package com.edchantalsefaz.apibank.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.edchantalsefaz.apibank.domain.AccountBank} entity.
 */
public class AccountBankDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double saldo;

    @NotNull
    private Long numeroConta;


    private Long personId;

    private String personNome;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Long getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Long numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonNome() {
        return personNome;
    }

    public void setPersonNome(String personNome) {
        this.personNome = personNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountBankDTO)) {
            return false;
        }

        return id != null && id.equals(((AccountBankDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountBankDTO{" +
            "id=" + getId() +
            ", saldo=" + getSaldo() +
            ", numeroConta=" + getNumeroConta() +
            ", personId=" + getPersonId() +
            ", personNome='" + getPersonNome() + "'" +
            "}";
    }
}
