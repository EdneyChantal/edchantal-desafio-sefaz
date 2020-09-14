package com.edchantalsefaz.apibank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Saque.
 */
@Entity
@Table(name = "saque")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Saque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_saque", nullable = false)
    private LocalDate dataSaque;

    @NotNull
    @Column(name = "valor", nullable = false)
    private Double valor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "saques", allowSetters = true)
    private AccountBank account;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataSaque() {
        return dataSaque;
    }

    public Saque dataSaque(LocalDate dataSaque) {
        this.dataSaque = dataSaque;
        return this;
    }

    public void setDataSaque(LocalDate dataSaque) {
        this.dataSaque = dataSaque;
    }

    public Double getValor() {
        return valor;
    }

    public Saque valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public AccountBank getAccount() {
        return account;
    }

    public Saque account(AccountBank accountBank) {
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
        if (!(o instanceof Saque)) {
            return false;
        }
        return id != null && id.equals(((Saque) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Saque{" +
            "id=" + getId() +
            ", dataSaque='" + getDataSaque() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
