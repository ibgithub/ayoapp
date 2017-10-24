package com.ib.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Operator.
 */
@Entity
@Table(name = "operator")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "operator")
public class Operator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_operator")
    private Integer idOperator;

    @Column(name = "nama")
    private String nama;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdOperator() {
        return idOperator;
    }

    public Operator idOperator(Integer idOperator) {
        this.idOperator = idOperator;
        return this;
    }

    public void setIdOperator(Integer idOperator) {
        this.idOperator = idOperator;
    }

    public String getNama() {
        return nama;
    }

    public Operator nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
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
        Operator operator = (Operator) o;
        if (operator.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operator.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operator{" +
            "id=" + getId() +
            ", idOperator='" + getIdOperator() + "'" +
            ", nama='" + getNama() + "'" +
            "}";
    }
}
