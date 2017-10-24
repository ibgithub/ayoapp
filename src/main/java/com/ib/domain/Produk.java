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
 * A Produk.
 */
@Entity
@Table(name = "produk")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "produk")
public class Produk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_produk")
    private Integer idProduk;

    @Column(name = "kode_produk")
    private String kodeProduk;

    @Column(name = "id_kartu")
    private Integer idKartu;

    @Column(name = "denom", precision=10, scale=2)
    private BigDecimal denom;

    @Column(name = "hpp", precision=10, scale=2)
    private BigDecimal hpp;

    @Column(name = "harga_man", precision=10, scale=2)
    private BigDecimal hargaMan;

    @Column(name = "status")
    private Integer status;

    @Column(name = "gangguan")
    private Integer gangguan;

    @Column(name = "id_distributor")
    private Long idDistributor;

    @Column(name = "fisik")
    private Integer fisik;

    @Column(name = "tgl_update")
    private ZonedDateTime tglUpdate;

    @Column(name = "user_update")
    private String userUpdate;

    @Column(name = "id_distributor_2")
    private Long idDistributor2;

    @Column(name = "konversi_saldo", precision=10, scale=2)
    private BigDecimal konversiSaldo;

    @Column(name = "isreport")
    private Integer isreport;

    @Column(name = "issplit")
    private Integer issplit;

    @Column(name = "ototimeopen")
    private String ototimeopen;

    @Column(name = "ototimeclose")
    private String ototimeclose;

    @Column(name = "id_distributor_3")
    private Long idDistributor3;

    @Column(name = "isstok")
    private Integer isstok;

    @Column(name = "otoclosestatus")
    private Integer otoclosestatus;

    @Column(name = "saldo_min", precision=10, scale=2)
    private BigDecimal saldoMin;

    @Column(name = "akses")
    private Integer akses;

    @Column(name = "hlr")
    private Integer hlr;

    @Column(name = "isulang")
    private Integer isulang;

    @Column(name = "isurut")
    private Integer isurut;

    @Column(name = "formatppob")
    private Integer formatppob;

    @Column(name = "jenisppob")
    private Integer jenisppob;

    @Column(name = "ketproduk")
    private String ketproduk;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProduk() {
        return idProduk;
    }

    public Produk idProduk(Integer idProduk) {
        this.idProduk = idProduk;
        return this;
    }

    public void setIdProduk(Integer idProduk) {
        this.idProduk = idProduk;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public Produk kodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
        return this;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public Integer getIdKartu() {
        return idKartu;
    }

    public Produk idKartu(Integer idKartu) {
        this.idKartu = idKartu;
        return this;
    }

    public void setIdKartu(Integer idKartu) {
        this.idKartu = idKartu;
    }

    public BigDecimal getDenom() {
        return denom;
    }

    public Produk denom(BigDecimal denom) {
        this.denom = denom;
        return this;
    }

    public void setDenom(BigDecimal denom) {
        this.denom = denom;
    }

    public BigDecimal getHpp() {
        return hpp;
    }

    public Produk hpp(BigDecimal hpp) {
        this.hpp = hpp;
        return this;
    }

    public void setHpp(BigDecimal hpp) {
        this.hpp = hpp;
    }

    public BigDecimal getHargaMan() {
        return hargaMan;
    }

    public Produk hargaMan(BigDecimal hargaMan) {
        this.hargaMan = hargaMan;
        return this;
    }

    public void setHargaMan(BigDecimal hargaMan) {
        this.hargaMan = hargaMan;
    }

    public Integer getStatus() {
        return status;
    }

    public Produk status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGangguan() {
        return gangguan;
    }

    public Produk gangguan(Integer gangguan) {
        this.gangguan = gangguan;
        return this;
    }

    public void setGangguan(Integer gangguan) {
        this.gangguan = gangguan;
    }

    public Long getIdDistributor() {
        return idDistributor;
    }

    public Produk idDistributor(Long idDistributor) {
        this.idDistributor = idDistributor;
        return this;
    }

    public void setIdDistributor(Long idDistributor) {
        this.idDistributor = idDistributor;
    }

    public Integer getFisik() {
        return fisik;
    }

    public Produk fisik(Integer fisik) {
        this.fisik = fisik;
        return this;
    }

    public void setFisik(Integer fisik) {
        this.fisik = fisik;
    }

    public ZonedDateTime getTglUpdate() {
        return tglUpdate;
    }

    public Produk tglUpdate(ZonedDateTime tglUpdate) {
        this.tglUpdate = tglUpdate;
        return this;
    }

    public void setTglUpdate(ZonedDateTime tglUpdate) {
        this.tglUpdate = tglUpdate;
    }

    public String getUserUpdate() {
        return userUpdate;
    }

    public Produk userUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
        return this;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }

    public Long getIdDistributor2() {
        return idDistributor2;
    }

    public Produk idDistributor2(Long idDistributor2) {
        this.idDistributor2 = idDistributor2;
        return this;
    }

    public void setIdDistributor2(Long idDistributor2) {
        this.idDistributor2 = idDistributor2;
    }

    public BigDecimal getKonversiSaldo() {
        return konversiSaldo;
    }

    public Produk konversiSaldo(BigDecimal konversiSaldo) {
        this.konversiSaldo = konversiSaldo;
        return this;
    }

    public void setKonversiSaldo(BigDecimal konversiSaldo) {
        this.konversiSaldo = konversiSaldo;
    }

    public Integer getIsreport() {
        return isreport;
    }

    public Produk isreport(Integer isreport) {
        this.isreport = isreport;
        return this;
    }

    public void setIsreport(Integer isreport) {
        this.isreport = isreport;
    }

    public Integer getIssplit() {
        return issplit;
    }

    public Produk issplit(Integer issplit) {
        this.issplit = issplit;
        return this;
    }

    public void setIssplit(Integer issplit) {
        this.issplit = issplit;
    }

    public String getOtotimeopen() {
        return ototimeopen;
    }

    public Produk ototimeopen(String ototimeopen) {
        this.ototimeopen = ototimeopen;
        return this;
    }

    public void setOtotimeopen(String ototimeopen) {
        this.ototimeopen = ototimeopen;
    }

    public String getOtotimeclose() {
        return ototimeclose;
    }

    public Produk ototimeclose(String ototimeclose) {
        this.ototimeclose = ototimeclose;
        return this;
    }

    public void setOtotimeclose(String ototimeclose) {
        this.ototimeclose = ototimeclose;
    }

    public Long getIdDistributor3() {
        return idDistributor3;
    }

    public Produk idDistributor3(Long idDistributor3) {
        this.idDistributor3 = idDistributor3;
        return this;
    }

    public void setIdDistributor3(Long idDistributor3) {
        this.idDistributor3 = idDistributor3;
    }

    public Integer getIsstok() {
        return isstok;
    }

    public Produk isstok(Integer isstok) {
        this.isstok = isstok;
        return this;
    }

    public void setIsstok(Integer isstok) {
        this.isstok = isstok;
    }

    public Integer getOtoclosestatus() {
        return otoclosestatus;
    }

    public Produk otoclosestatus(Integer otoclosestatus) {
        this.otoclosestatus = otoclosestatus;
        return this;
    }

    public void setOtoclosestatus(Integer otoclosestatus) {
        this.otoclosestatus = otoclosestatus;
    }

    public BigDecimal getSaldoMin() {
        return saldoMin;
    }

    public Produk saldoMin(BigDecimal saldoMin) {
        this.saldoMin = saldoMin;
        return this;
    }

    public void setSaldoMin(BigDecimal saldoMin) {
        this.saldoMin = saldoMin;
    }

    public Integer getAkses() {
        return akses;
    }

    public Produk akses(Integer akses) {
        this.akses = akses;
        return this;
    }

    public void setAkses(Integer akses) {
        this.akses = akses;
    }

    public Integer getHlr() {
        return hlr;
    }

    public Produk hlr(Integer hlr) {
        this.hlr = hlr;
        return this;
    }

    public void setHlr(Integer hlr) {
        this.hlr = hlr;
    }

    public Integer getIsulang() {
        return isulang;
    }

    public Produk isulang(Integer isulang) {
        this.isulang = isulang;
        return this;
    }

    public void setIsulang(Integer isulang) {
        this.isulang = isulang;
    }

    public Integer getIsurut() {
        return isurut;
    }

    public Produk isurut(Integer isurut) {
        this.isurut = isurut;
        return this;
    }

    public void setIsurut(Integer isurut) {
        this.isurut = isurut;
    }

    public Integer getFormatppob() {
        return formatppob;
    }

    public Produk formatppob(Integer formatppob) {
        this.formatppob = formatppob;
        return this;
    }

    public void setFormatppob(Integer formatppob) {
        this.formatppob = formatppob;
    }

    public Integer getJenisppob() {
        return jenisppob;
    }

    public Produk jenisppob(Integer jenisppob) {
        this.jenisppob = jenisppob;
        return this;
    }

    public void setJenisppob(Integer jenisppob) {
        this.jenisppob = jenisppob;
    }

    public String getKetproduk() {
        return ketproduk;
    }

    public Produk ketproduk(String ketproduk) {
        this.ketproduk = ketproduk;
        return this;
    }

    public void setKetproduk(String ketproduk) {
        this.ketproduk = ketproduk;
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
        Produk produk = (Produk) o;
        if (produk.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Produk{" +
            "id=" + getId() +
            ", idProduk='" + getIdProduk() + "'" +
            ", kodeProduk='" + getKodeProduk() + "'" +
            ", idKartu='" + getIdKartu() + "'" +
            ", denom='" + getDenom() + "'" +
            ", hpp='" + getHpp() + "'" +
            ", hargaMan='" + getHargaMan() + "'" +
            ", status='" + getStatus() + "'" +
            ", gangguan='" + getGangguan() + "'" +
            ", idDistributor='" + getIdDistributor() + "'" +
            ", fisik='" + getFisik() + "'" +
            ", tglUpdate='" + getTglUpdate() + "'" +
            ", userUpdate='" + getUserUpdate() + "'" +
            ", idDistributor2='" + getIdDistributor2() + "'" +
            ", konversiSaldo='" + getKonversiSaldo() + "'" +
            ", isreport='" + getIsreport() + "'" +
            ", issplit='" + getIssplit() + "'" +
            ", ototimeopen='" + getOtotimeopen() + "'" +
            ", ototimeclose='" + getOtotimeclose() + "'" +
            ", idDistributor3='" + getIdDistributor3() + "'" +
            ", isstok='" + getIsstok() + "'" +
            ", otoclosestatus='" + getOtoclosestatus() + "'" +
            ", saldoMin='" + getSaldoMin() + "'" +
            ", akses='" + getAkses() + "'" +
            ", hlr='" + getHlr() + "'" +
            ", isulang='" + getIsulang() + "'" +
            ", isurut='" + getIsurut() + "'" +
            ", formatppob='" + getFormatppob() + "'" +
            ", jenisppob='" + getJenisppob() + "'" +
            ", ketproduk='" + getKetproduk() + "'" +
            "}";
    }
}
