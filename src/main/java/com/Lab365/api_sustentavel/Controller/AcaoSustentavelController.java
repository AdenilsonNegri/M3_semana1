package com.Lab365.api_sustentavel.Controller;

import com.Lab365.api_sustentavel.Dto.AcaoSustentavelRequest;
import com.Lab365.api_sustentavel.Dto.AcaoSustentavelResponse;
import com.Lab365.api_sustentavel.Entity.AcaoSustentavel;
import com.Lab365.api_sustentavel.Exception.RecursoNaoEncontradoException;
import com.Lab365.api_sustentavel.Repository.AcaoSustentavelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Lab365.api_sustentavel.Enum.CategoriaAcao;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/acoes")
public class AcaoSustentavelController {

    @Autowired
    private AcaoSustentavelRepository acaoRepository;

    //POST - Criar nova ação sustentável
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AcaoSustentavelResponse> criar(@Valid @RequestBody AcaoSustentavelRequest request) {
        AcaoSustentavel acao = new AcaoSustentavel(
                request.getTitulo(),
                request.getDescricao(),
                request.getCategoria(),
                request.getDataRealizacao(),
                request.getResponsavel()
        );

        AcaoSustentavel salva = acaoRepository.save(acao);

        AcaoSustentavelResponse response = new AcaoSustentavelResponse(
                salva.getId(),
                salva.getTitulo(),
                salva.getDescricao(),
                salva.getCategoria(),
                salva.getDataRealizacao(),
                salva.getResponsavel()
        );

        return ResponseEntity.created(URI.create("/acoes/" + salva.getId())).body(response);
    }

    // GET - Listar todas as ações
    @GetMapping
    public ResponseEntity<List<AcaoSustentavelResponse>> listar() {
        List<AcaoSustentavelResponse> resposta = acaoRepository.findAll().stream()
                .map(acao -> new AcaoSustentavelResponse(
                        acao.getId(),
                        acao.getTitulo(),
                        acao.getDescricao(),
                        acao.getCategoria(),
                        acao.getDataRealizacao(),
                        acao.getResponsavel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resposta);
    }

    // GET - /acoes/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<AcaoSustentavelResponse> buscarPorId(@PathVariable Long id) {
        AcaoSustentavel acao = acaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Ação sustentavel com ID" + id + "não foi encontrada."
                ));

        AcaoSustentavelResponse response = new AcaoSustentavelResponse(
                acao.getId(),
                acao.getTitulo(),
                acao.getDescricao(),
                acao.getCategoria(),
                acao.getDataRealizacao(),
                acao.getResponsavel()
        );

        return ResponseEntity.ok(response);
    }

    //  PUT /acoes/{id} → Atualizar ação existente
    @PutMapping("/{id}")
    public ResponseEntity<AcaoSustentavelResponse> atualizar(@PathVariable Long id,
                                                             @Valid @RequestBody AcaoSustentavelRequest request) {
        AcaoSustentavel acao = acaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Impossível atualizar. Ação com ID" + id + "não encontrada."
                ));

                    acao.setTitulo(request.getTitulo());
                    acao.setDescricao(request.getDescricao());
                    acao.setCategoria(request.getCategoria());
                    acao.setDataRealizacao(request.getDataRealizacao());
                    acao.setResponsavel(request.getResponsavel());

                    AcaoSustentavel atualizada = acaoRepository.save(acao);

                    AcaoSustentavelResponse response = new AcaoSustentavelResponse(
                            atualizada.getId(),
                            atualizada.getTitulo(),
                            atualizada.getDescricao(),
                            atualizada.getCategoria(),
                            atualizada.getDataRealizacao(),
                            atualizada.getResponsavel()
                    );

                    return ResponseEntity.ok(response);

    }

    //  DELETE /acoes/{id} → Excluir ação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!acaoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Ação com ID" + id + "não pode ser excluída porque não existe.");
        }

        acaoRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // ✅ NOVO: GET /acoes/categoria?tipo=DOACAO
    @GetMapping("/categoria")
    public ResponseEntity<List<AcaoSustentavelResponse>> listarPorCategoria(
            @RequestParam("tipo") String tipo) {

        // 1. Validar se o valor do parâmetro é um enum válido
        CategoriaAcao categoria;
        try {
            categoria = CategoriaAcao.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RecursoNaoEncontradoException(
                    "Categoria inválida: '" + tipo + "'. " +
                            "Valores permitidos: DOACAO, RECICLAGEM, PLANTIO, EDUCACAO_AMBIENTAL, OUTROS"
            );
        }

        // 2. Buscar no banco
        List<AcaoSustentavel> acoes = acaoRepository.findByCategoria(categoria);

        // 3. Converter para DTO
        List<AcaoSustentavelResponse> resposta = acoes.stream()
                .map(acao -> new AcaoSustentavelResponse(
                        acao.getId(),
                        acao.getTitulo(),
                        acao.getDescricao(),
                        acao.getCategoria(),
                        acao.getDataRealizacao(),
                        acao.getResponsavel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resposta);
    }
}


