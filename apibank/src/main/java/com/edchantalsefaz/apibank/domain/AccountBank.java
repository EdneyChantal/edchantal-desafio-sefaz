package com.edchantalsefaz.apibank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A AccountBank.
 */
@Entity
@Table(name = "account_bank")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountBank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "saldo", nullable = false)
    private Double saldo;

    @NotNull
    @Column(name = "numero_conta", nullable = false, unique = true)
    private Long numeroConta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "accountBanks", allowSetters = true)
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public AccountBank saldo(Double saldo) {
        this.saldo = saldo;
        return this;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Long getNumeroConta() {
        return numeroConta;
    }

    public AccountBank numeroConta(Long numeroConta) {
        this.numeroConta = numeroConta;
        return this;
    }

    public void setNumeroConta(Long numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Person getPerson() {
        return person;
    }

    public AccountBank person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountBank)) {
            return false;
        }
        return id != null && id.equals(((AccountBank) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountBank{" +
            "id=" + getId() +
            ", saldo=" + getSaldo() +
            ", numeroConta=" + getNumeroConta() +
            "}";
    }
}
