package com.edchantalsefaz.apibank.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.edchantalsefaz.apibank.domain.SolicabertConta} entity.
 */
public class SolicabertContaDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    private String nome;

    @NotNull
    @Min(value = 1L)
    @Max(value = 99999999999L)
    private Long cpf;

    @NotNull
    @DecimalMin(value = "0.1")
    private Double saldoinicial;

    @Size(min = 1, max = 500)
    private String status;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Double getSaldoinicial() {
        return saldoinicial;
    }

    public void setSaldoinicial(Double saldoinicial) {
        this.saldoinicial = saldoinicial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SolicabertContaDTO)) {
            return false;
        }

        return id != null && id.equals(((SolicabertContaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SolicabertContaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf=" + getCpf() +
            ", saldoinicial=" + getSaldoinicial() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
