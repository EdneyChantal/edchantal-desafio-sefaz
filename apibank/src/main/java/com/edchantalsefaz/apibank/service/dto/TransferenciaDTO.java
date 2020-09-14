package com.edchantalsefaz.apibank.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.edchantalsefaz.apibank.domain.Transferencia} entity.
 */
public class TransferenciaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private LocalDate dataTransferencia;

    @NotNull
    private Double valor;


    private Long accountSaqueId;

    private String accountSaqueNumeroConta;

    private Long accountDepositoId;

    private String accountDepositoNumeroConta;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(LocalDate dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getAccountSaqueId() {
        return accountSaqueId;
    }

    public void setAccountSaqueId(Long accountBankId) {
        this.accountSaqueId = accountBankId;
    }

    public String getAccountSaqueNumeroConta() {
        return accountSaqueNumeroConta;
    }

    public void setAccountSaqueNumeroConta(String accountBankNumeroConta) {
        this.accountSaqueNumeroConta = accountBankNumeroConta;
    }

    public Long getAccountDepositoId() {
        return accountDepositoId;
    }

    public void setAccountDepositoId(Long accountBankId) {
        this.accountDepositoId = accountBankId;
    }

    public String getAccountDepositoNumeroConta() {
        return accountDepositoNumeroConta;
    }

    public void setAccountDepositoNumeroConta(String accountBankNumeroConta) {
        this.accountDepositoNumeroConta = accountBankNumeroConta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferenciaDTO)) {
            return false;
        }

        return id != null && id.equals(((TransferenciaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferenciaDTO{" +
            "id=" + getId() +
            ", dataTransferencia='" + getDataTransferencia() + "'" +
            ", valor=" + getValor() +
            ", accountSaqueId=" + getAccountSaqueId() +
            ", accountSaqueNumeroConta='" + getAccountSaqueNumeroConta() + "'" +
            ", accountDepositoId=" + getAccountDepositoId() +
            ", accountDepositoNumeroConta='" + getAccountDepositoNumeroConta() + "'" +
            "}";
    }
}
