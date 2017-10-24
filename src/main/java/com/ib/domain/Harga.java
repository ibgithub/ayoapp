package com.ib.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Harga.
 */
@Entity
@Table(name = "harga")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "harga")
public class Harga implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_harga")
    private Integer idHarga;

    @Column(name = "id_produk")
    private Integer idProduk;

    @Column(name = "id_member")
    private String idMember;

    @Column(name = "harga_jual", precision=10, scale=2)
    private BigDecimal hargaJual;

    @Column(name = "tgl_input")
    private ZonedDateTime tglInput;

    @Column(name = "user_input")
    private String userInput;

    @Column(name = "harga_before", precision=10, scale=2)
    private BigDecimal hargaBefore;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdHarga() {
        return idHarga;
    }

    public Harga idHarga(Integer idHarga) {
        this.idHarga = idHarga;
        return this;
    }

    public void setIdHarga(Integer idHarga) {
        this.idHarga = idHarga;
    }

    public Integer getIdProduk() {
        return idProduk;
    }

    public Harga idProduk(Integer idProduk) {
        this.idProduk = idProduk;
        return this;
    }

    public void setIdProduk(Integer idProduk) {
        this.idProduk = idProduk;
    }

    public String getIdMember() {
        return idMember;
    }

    public Harga idMember(String idMember) {
        this.idMember = idMember;
        return this;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

    public BigDecimal getHargaJual() {
        return hargaJual;
    }

    public Harga hargaJual(BigDecimal hargaJual) {
        this.hargaJual = hargaJual;
        return this;
    }

    public void setHargaJual(BigDecimal hargaJual) {
        this.hargaJual = hargaJual;
    }

    public ZonedDateTime getTglInput() {
        return tglInput;
    }

    public Harga tglInput(ZonedDateTime tglInput) {
        this.tglInput = tglInput;
        return this;
    }

    public void setTglInput(ZonedDateTime tglInput) {
        this.tglInput = tglInput;
    }

    public String getUserInput() {
        return userInput;
    }

    public Harga userInput(String userInput) {
        this.userInput = userInput;
        return this;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public BigDecimal getHargaBefore() {
        return hargaBefore;
    }

    public Harga hargaBefore(BigDecimal hargaBefore) {
        this.hargaBefore = hargaBefore;
        return this;
    }

    public void setHargaBefore(BigDecimal hargaBefore) {
        this.hargaBefore = hargaBefore;
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
        Harga harga = (Harga) o;
        if (harga.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), harga.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Harga{" +
            "id=" + getId() +
            ", idHarga='" + getIdHarga() + "'" +
            ", idProduk='" + getIdProduk() + "'" +
            ", idMember='" + getIdMember() + "'" +
            ", hargaJual='" + getHargaJual() + "'" +
            ", tglInput='" + getTglInput() + "'" +
            ", userInput='" + getUserInput() + "'" +
            ", hargaBefore='" + getHargaBefore() + "'" +
            "}";
    }
}
