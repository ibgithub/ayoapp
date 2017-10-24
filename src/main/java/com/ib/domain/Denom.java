package com.ib.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Denom.
 */
@Entity
@Table(name = "denom")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "denom")
public class Denom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "jml_denom", precision=10, scale=2, nullable = false)
    private BigDecimal jmlDenom;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getJmlDenom() {
        return jmlDenom;
    }

    public Denom jmlDenom(BigDecimal jmlDenom) {
        this.jmlDenom = jmlDenom;
        return this;
    }

    public void setJmlDenom(BigDecimal jmlDenom) {
        this.jmlDenom = jmlDenom;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Denom denom = (Denom) o;
        if (denom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), denom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Denom{" +
            "id=" + getId() +
            ", jmlDenom='" + getJmlDenom() + "'" +
            "}";
    }
}
