package com.edchantalsefaz.apibank.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.edchantalsefaz.apibank.domain.Saque} entity.
 */
public class SaqueDTO implements Serializable {
    
    private Long id;

    @NotNull
    private LocalDate dataSaque;

    @NotNull
    private Double valor;


    private Long accountId;

    private String accountNumeroConta;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataSaque() {
        return dataSaque;
    }

    public void setDataSaque(LocalDate dataSaque) {
        this.dataSaque = dataSaque;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountBankId) {
        this.accountId = accountBankId;
    }

    public String getAccountNumeroConta() {
        return accountNumeroConta;
    }

    public void setAccountNumeroConta(String accountBankNumeroConta) {
        this.accountNumeroConta = accountBankNumeroConta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaqueDTO)) {
            return false;
        }

        return id != null && id.equals(((SaqueDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaqueDTO{" +
            "id=" + getId() +
            ", dataSaque='" + getDataSaque() + "'" +
            ", valor=" + getValor() +
            ", accountId=" + getAccountId() +
            ", accountNumeroConta='" + getAccountNumeroConta() + "'" +
            "}";
    }
}
