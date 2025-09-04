package com.Lab365.api_sustentavel.Dto;

import com.Lab365.api_sustentavel.Enum.CategoriaAcao;
import java.time.LocalDate;

public class AcaoSustentavelResponse {

    private Long id;
    private String titulo;
    private String descricao;
    private CategoriaAcao categoria;
    private LocalDate dataRealizacao;
    private String responsavel;

    // Construtor a partir da entidade
    public AcaoSustentavelResponse(Long id, String titulo, String descricao, CategoriaAcao categoria,
                                   LocalDate dataRealizacao, String responsavel) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.dataRealizacao = dataRealizacao;
        this.responsavel = responsavel;
    }

    // Getters (Setters não são necessários em DTO de resposta)

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public CategoriaAcao getCategoria() {
        return categoria;
    }

    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }

    public String getResponsavel() {
        return responsavel;
    }
}
