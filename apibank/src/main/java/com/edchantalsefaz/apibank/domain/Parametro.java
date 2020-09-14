package com.edchantalsefaz.apibank.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Parametro.
 */
@Entity
@Table(name = "parametro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Parametro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "vlr_min_abre_conta", nullable = false)
    private Double vlrMinAbreConta;

    @NotNull
    @Column(name = "vlr_max_transfer", nullable = false)
    private Double vlrMaxTransfer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getVlrMinAbreConta() {
        return vlrMinAbreConta;
    }

    public Parametro vlrMinAbreConta(Double vlrMinAbreConta) {
        this.vlrMinAbreConta = vlrMinAbreConta;
        return this;
    }

    public void setVlrMinAbreConta(Double vlrMinAbreConta) {
        this.vlrMinAbreConta = vlrMinAbreConta;
    }

    public Double getVlrMaxTransfer() {
        return vlrMaxTransfer;
    }

    public Parametro vlrMaxTransfer(Double vlrMaxTransfer) {
        this.vlrMaxTransfer = vlrMaxTransfer;
        return this;
    }

    public void setVlrMaxTransfer(Double vlrMaxTransfer) {
        this.vlrMaxTransfer = vlrMaxTransfer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parametro)) {
            return false;
        }
        return id != null && id.equals(((Parametro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parametro{" +
            "id=" + getId() +
            ", vlrMinAbreConta=" + getVlrMinAbreConta() +
            ", vlrMaxTransfer=" + getVlrMaxTransfer() +
            "}";
    }
}
