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
 * A Transaksi.
 */
@Entity
@Table(name = "transaksi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transaksi")
public class Transaksi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tgl_transaksi")
    private ZonedDateTime tglTransaksi;

    @Column(name = "id_member")
    private String idMember;

    @Column(name = "nama")
    private String nama;

    @Column(name = "jml", precision=10, scale=2)
    private BigDecimal jml;

    @Column(name = "kode_trx")
    private Integer kodeTrx;

    @Column(name = "status")
    private Integer status;

    @Column(name = "saldo_awal", precision=10, scale=2)
    private BigDecimal saldoAwal;

    @Column(name = "saldo_akhir", precision=10, scale=2)
    private BigDecimal saldoAkhir;

    @Column(name = "ket")
    private String ket;

    @Column(name = "tgl_input")
    private ZonedDateTime tglInput;

    @Column(name = "user_input")
    private String userInput;

    @Column(name = "isstok")
    private Integer isstok;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTglTransaksi() {
        return tglTransaksi;
    }

    public Transaksi tglTransaksi(ZonedDateTime tglTransaksi) {
        this.tglTransaksi = tglTransaksi;
        return this;
    }

    public void setTglTransaksi(ZonedDateTime tglTransaksi) {
        this.tglTransaksi = tglTransaksi;
    }

    public String getIdMember() {
        return idMember;
    }

    public Transaksi idMember(String idMember) {
        this.idMember = idMember;
        return this;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

    public String getNama() {
        return nama;
    }

    public Transaksi nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public BigDecimal getJml() {
        return jml;
    }

    public Transaksi jml(BigDecimal jml) {
        this.jml = jml;
        return this;
    }

    public void setJml(BigDecimal jml) {
        this.jml = jml;
    }

    public Integer getKodeTrx() {
        return kodeTrx;
    }

    public Transaksi kodeTrx(Integer kodeTrx) {
        this.kodeTrx = kodeTrx;
        return this;
    }

    public void setKodeTrx(Integer kodeTrx) {
        this.kodeTrx = kodeTrx;
    }

    public Integer getStatus() {
        return status;
    }

    public Transaksi status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getSaldoAwal() {
        return saldoAwal;
    }

    public Transaksi saldoAwal(BigDecimal saldoAwal) {
        this.saldoAwal = saldoAwal;
        return this;
    }

    public void setSaldoAwal(BigDecimal saldoAwal) {
        this.saldoAwal = saldoAwal;
    }

    public BigDecimal getSaldoAkhir() {
        return saldoAkhir;
    }

    public Transaksi saldoAkhir(BigDecimal saldoAkhir) {
        this.saldoAkhir = saldoAkhir;
        return this;
    }

    public void setSaldoAkhir(BigDecimal saldoAkhir) {
        this.saldoAkhir = saldoAkhir;
    }

    public String getKet() {
        return ket;
    }

    public Transaksi ket(String ket) {
        this.ket = ket;
        return this;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public ZonedDateTime getTglInput() {
        return tglInput;
    }

    public Transaksi tglInput(ZonedDateTime tglInput) {
        this.tglInput = tglInput;
        return this;
    }

    public void setTglInput(ZonedDateTime tglInput) {
        this.tglInput = tglInput;
    }

    public String getUserInput() {
        return userInput;
    }

    public Transaksi userInput(String userInput) {
        this.userInput = userInput;
        return this;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public Integer getIsstok() {
        return isstok;
    }

    public Transaksi isstok(Integer isstok) {
        this.isstok = isstok;
        return this;
    }

    public void setIsstok(Integer isstok) {
        this.isstok = isstok;
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
        Transaksi transaksi = (Transaksi) o;
        if (transaksi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaksi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaksi{" +
            "id=" + getId() +
            ", tglTransaksi='" + getTglTransaksi() + "'" +
            ", idMember='" + getIdMember() + "'" +
            ", nama='" + getNama() + "'" +
            ", jml='" + getJml() + "'" +
            ", kodeTrx='" + getKodeTrx() + "'" +
            ", status='" + getStatus() + "'" +
            ", saldoAwal='" + getSaldoAwal() + "'" +
            ", saldoAkhir='" + getSaldoAkhir() + "'" +
            ", ket='" + getKet() + "'" +
            ", tglInput='" + getTglInput() + "'" +
            ", userInput='" + getUserInput() + "'" +
            ", isstok='" + getIsstok() + "'" +
            "}";
    }
}
