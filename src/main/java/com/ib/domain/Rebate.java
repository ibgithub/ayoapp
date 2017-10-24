package com.ib.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Rebate.
 */
@Entity
@Table(name = "rebate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rebate")
public class Rebate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_transaksi")
    private Long idTransaksi;

    @Column(name = "jml", precision=10, scale=2)
    private BigDecimal jml;

    @Column(name = "harga_produk", precision=10, scale=2)
    private BigDecimal hargaProduk;

    @Column(name = "id_member")
    private String idMember;

    @Column(name = "jhi_level")
    private Integer level;

    @Column(name = "bulan")
    private Integer bulan;

    @Column(name = "tahun")
    private Integer tahun;

    @Column(name = "status")
    private Integer status;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTransaksi() {
        return idTransaksi;
    }

    public Rebate idTransaksi(Long idTransaksi) {
        this.idTransaksi = idTransaksi;
        return this;
    }

    public void setIdTransaksi(Long idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public BigDecimal getJml() {
        return jml;
    }

    public Rebate jml(BigDecimal jml) {
        this.jml = jml;
        return this;
    }

    public void setJml(BigDecimal jml) {
        this.jml = jml;
    }

    public BigDecimal getHargaProduk() {
        return hargaProduk;
    }

    public Rebate hargaProduk(BigDecimal hargaProduk) {
        this.hargaProduk = hargaProduk;
        return this;
    }

    public void setHargaProduk(BigDecimal hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public String getIdMember() {
        return idMember;
    }

    public Rebate idMember(String idMember) {
        this.idMember = idMember;
        return this;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

    public Integer getLevel() {
        return level;
    }

    public Rebate level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getBulan() {
        return bulan;
    }

    public Rebate bulan(Integer bulan) {
        this.bulan = bulan;
        return this;
    }

    public void setBulan(Integer bulan) {
        this.bulan = bulan;
    }

    public Integer getTahun() {
        return tahun;
    }

    public Rebate tahun(Integer tahun) {
        this.tahun = tahun;
        return this;
    }

    public void setTahun(Integer tahun) {
        this.tahun = tahun;
    }

    public Integer getStatus() {
        return status;
    }

    public Rebate status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        Rebate rebate = (Rebate) o;
        if (rebate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rebate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rebate{" +
            "id=" + getId() +
            ", idTransaksi='" + getIdTransaksi() + "'" +
            ", jml='" + getJml() + "'" +
            ", hargaProduk='" + getHargaProduk() + "'" +
            ", idMember='" + getIdMember() + "'" +
            ", level='" + getLevel() + "'" +
            ", bulan='" + getBulan() + "'" +
            ", tahun='" + getTahun() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
