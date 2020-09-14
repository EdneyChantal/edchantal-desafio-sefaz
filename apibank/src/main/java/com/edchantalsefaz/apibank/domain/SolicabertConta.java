package com.edchantalsefaz.apibank.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A SolicabertConta.
 */
@Entity
@Table(name = "solicabert_conta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SolicabertConta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @NotNull
    @Min(value = 1L)
    @Max(value = 99999999999L)
    @Column(name = "cpf", nullable = false)
    private Long cpf;

    @NotNull
    @DecimalMin(value = "0.1")
    @Column(name = "saldoinicial", nullable = false)
    private Double saldoinicial;

    @Size(min = 1, max = 500)
    @Column(name = "status", length = 500)
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public SolicabertConta nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public SolicabertConta cpf(Long cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Double getSaldoinicial() {
        return saldoinicial;
    }

    public SolicabertConta saldoinicial(Double saldoinicial) {
        this.saldoinicial = saldoinicial;
        return this;
    }

    public void setSaldoinicial(Double saldoinicial) {
        this.saldoinicial = saldoinicial;
    }

    public String getStatus() {
        return status;
    }

    public SolicabertConta status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SolicabertConta)) {
            return false;
        }
        return id != null && id.equals(((SolicabertConta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SolicabertConta{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf=" + getCpf() +
            ", saldoinicial=" + getSaldoinicial() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
