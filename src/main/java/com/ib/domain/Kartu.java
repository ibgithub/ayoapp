package com.ib.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Kartu.
 */
@Entity
@Table(name = "kartu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "kartu")
public class Kartu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_kartu")
    private Integer idKartu;

    @Column(name = "nama")
    private String nama;

    @Column(name = "id_operator")
    private Integer idOperator;

    @Column(name = "cek_hlr")
    private Integer cekHlr;

    @Column(name = "ketkartu")
    private String ketkartu;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdKartu() {
        return idKartu;
    }

    public Kartu idKartu(Integer idKartu) {
        this.idKartu = idKartu;
        return this;
    }

    public void setIdKartu(Integer idKartu) {
        this.idKartu = idKartu;
    }

    public String getNama() {
        return nama;
    }

    public Kartu nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getIdOperator() {
        return idOperator;
    }

    public Kartu idOperator(Integer idOperator) {
        this.idOperator = idOperator;
        return this;
    }

    public void setIdOperator(Integer idOperator) {
        this.idOperator = idOperator;
    }

    public Integer getCekHlr() {
        return cekHlr;
    }

    public Kartu cekHlr(Integer cekHlr) {
        this.cekHlr = cekHlr;
        return this;
    }

    public void setCekHlr(Integer cekHlr) {
        this.cekHlr = cekHlr;
    }

    public String getKetkartu() {
        return ketkartu;
    }

    public Kartu ketkartu(String ketkartu) {
        this.ketkartu = ketkartu;
        return this;
    }

    public void setKetkartu(String ketkartu) {
        this.ketkartu = ketkartu;
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
        Kartu kartu = (Kartu) o;
        if (kartu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kartu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Kartu{" +
            "id=" + getId() +
            ", idKartu='" + getIdKartu() + "'" +
            ", nama='" + getNama() + "'" +
            ", idOperator='" + getIdOperator() + "'" +
            ", cekHlr='" + getCekHlr() + "'" +
            ", ketkartu='" + getKetkartu() + "'" +
            "}";
    }
}
