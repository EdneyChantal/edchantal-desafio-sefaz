package com.edchantalsefaz.apibank.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.edchantalsefaz.apibank.domain.Parametro} entity.
 */
public class ParametroDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double vlrMinAbreConta;

    @NotNull
    private Double vlrMaxTransfer;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getVlrMinAbreConta() {
        return vlrMinAbreConta;
    }

    public void setVlrMinAbreConta(Double vlrMinAbreConta) {
        this.vlrMinAbreConta = vlrMinAbreConta;
    }

    public Double getVlrMaxTransfer() {
        return vlrMaxTransfer;
    }

    public void setVlrMaxTransfer(Double vlrMaxTransfer) {
        this.vlrMaxTransfer = vlrMaxTransfer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParametroDTO)) {
            return false;
        }

        return id != null && id.equals(((ParametroDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParametroDTO{" +
            "id=" + getId() +
            ", vlrMinAbreConta=" + getVlrMinAbreConta() +
            ", vlrMaxTransfer=" + getVlrMaxTransfer() +
            "}";
    }
}
