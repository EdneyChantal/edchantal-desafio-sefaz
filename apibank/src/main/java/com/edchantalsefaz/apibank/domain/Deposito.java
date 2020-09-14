package com.edchantalsefaz.apibank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Deposito.
 */
@Entity
@Table(name = "deposito")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Deposito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_deposito", nullable = false)
    private LocalDate dataDeposito;

    @NotNull
    @Column(name = "valor", nullable = false)
    private Double valor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "depositos", allowSetters = true)
    private AccountBank account;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataDeposito() {
        return dataDeposito;
    }

    public Deposito dataDeposito(LocalDate dataDeposito) {
        this.dataDeposito = dataDeposito;
        return this;
    }

    public void setDataDeposito(LocalDate dataDeposito) {
        this.dataDeposito = dataDeposito;
    }

    public Double getValor() {
        return valor;
    }

    public Deposito valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public AccountBank getAccount() {
        return account;
    }

    public Deposito account(AccountBank accountBank) {
        this.account = accountBank;
        return this;
    }

    public void setAccount(AccountBank accountBank) {
        this.account = accountBank;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deposito)) {
            return false;
        }
        return id != null && id.equals(((Deposito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deposito{" +
            "id=" + getId() +
            ", dataDeposito='" + getDataDeposito() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
