package com.Lab365.api_sustentavel.Controller;

import com.Lab365.api_sustentavel.Dto.AcaoSustentavelRequest;
import com.Lab365.api_sustentavel.Dto.AcaoSustentavelResponse;
import com.Lab365.api_sustentavel.Entity.AcaoSustentavel;
import com.Lab365.api_sustentavel.Repository.AcaoSustentavelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return acaoRepository.findById(id)
                .map(acao -> ResponseEntity.ok(new AcaoSustentavelResponse(
                        acao.getId(),
                        acao.getTitulo(),
                        acao.getDescricao(),
                        acao.getCategoria(),
                        acao.getDataRealizacao(),
                        acao.getResponsavel())))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ PUT /acoes/{id} → Atualizar ação existente
    @PutMapping("/{id}")
    public ResponseEntity<AcaoSustentavelResponse> atualizar(@PathVariable Long id,
                                                             @Valid @RequestBody AcaoSustentavelRequest request) {
        return acaoRepository.findById(id)
                .map(acaoExistente -> {
                    // Atualiza os campos com os novos valores
                    acaoExistente.setTitulo(request.getTitulo());
                    acaoExistente.setDescricao(request.getDescricao());
                    acaoExistente.setCategoria(request.getCategoria());
                    acaoExistente.setDataRealizacao(request.getDataRealizacao());
                    acaoExistente.setResponsavel(request.getResponsavel());

                    AcaoSustentavel atualizada = acaoRepository.save(acaoExistente);

                    AcaoSustentavelResponse response = new AcaoSustentavelResponse(
                            atualizada.getId(),
                            atualizada.getTitulo(),
                            atualizada.getDescricao(),
                            atualizada.getCategoria(),
                            atualizada.getDataRealizacao(),
                            atualizada.getResponsavel()
                    );

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE /acoes/{id} → Excluir ação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!acaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        acaoRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
