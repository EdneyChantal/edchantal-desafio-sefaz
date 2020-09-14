package com.edchantalsefaz.apibank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Transferencia.
 */
@Entity
@Table(name = "transferencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transferencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_transferencia", nullable = false)
    private LocalDate dataTransferencia;

    @NotNull
    @Column(name = "valor", nullable = false)
    private Double valor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "transferencias", allowSetters = true)
    private AccountBank accountSaque;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "transferencias", allowSetters = true)
    private AccountBank accountDeposito;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataTransferencia() {
        return dataTransferencia;
    }

    public Transferencia dataTransferencia(LocalDate dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
        return this;
    }

    public void setDataTransferencia(LocalDate dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    public Double getValor() {
        return valor;
    }

    public Transferencia valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public AccountBank getAccountSaque() {
        return accountSaque;
    }

    public Transferencia accountSaque(AccountBank accountBank) {
        this.accountSaque = accountBank;
        return this;
    }

    public void setAccountSaque(AccountBank accountBank) {
        this.accountSaque = accountBank;
    }

    public AccountBank getAccountDeposito() {
        return accountDeposito;
    }

    public Transferencia accountDeposito(AccountBank accountBank) {
        this.accountDeposito = accountBank;
        return this;
    }

    public void setAccountDeposito(AccountBank accountBank) {
        this.accountDeposito = accountBank;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transferencia)) {
            return false;
        }
        return id != null && id.equals(((Transferencia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transferencia{" +
            "id=" + getId() +
            ", dataTransferencia='" + getDataTransferencia() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
