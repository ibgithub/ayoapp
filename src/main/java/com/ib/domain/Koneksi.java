package com.ib.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Koneksi.
 */
@Entity
@Table(name = "koneksi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "koneksi")
public class Koneksi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metode")
    private Integer metode;

    @Column(name = "ket")
    private String ket;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMetode() {
        return metode;
    }

    public Koneksi metode(Integer metode) {
        this.metode = metode;
        return this;
    }

    public void setMetode(Integer metode) {
        this.metode = metode;
    }

    public String getKet() {
        return ket;
    }

    public Koneksi ket(String ket) {
        this.ket = ket;
        return this;
    }

    public void setKet(String ket) {
        this.ket = ket;
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
        Koneksi koneksi = (Koneksi) o;
        if (koneksi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), koneksi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Koneksi{" +
            "id=" + getId() +
            ", metode='" + getMetode() + "'" +
            ", ket='" + getKet() + "'" +
            "}";
    }
}
