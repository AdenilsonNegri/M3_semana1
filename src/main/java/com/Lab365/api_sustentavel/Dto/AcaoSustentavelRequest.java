package com.Lab365.api_sustentavel.Dto;

import com.Lab365.api_sustentavel.Enum.CategoriaAcao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AcaoSustentavelRequest {

    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 100, message = "O título não pode ter mais de 100 caracteres.")
    private String titulo;

    @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres.")
    private String descricao;

    @NotNull(message = "A categoria é obrigatória.")
    private CategoriaAcao categoria;

    @NotNull(message = "A data de realização é obrigatória.")
    @PastOrPresent(message = "A data de realização não pode ser futura.")
    private LocalDate dataRealizacao;

    @NotBlank(message = "O nome do responsável é obrigatório.")
    @Size(max = 100, message = "O nome do responsável não pode ter mais de 100 caracteres.")
    private String responsavel;

    // Getters e Setters

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CategoriaAcao getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaAcao categoria) {
        this.categoria = categoria;
    }

    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(LocalDate dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}
