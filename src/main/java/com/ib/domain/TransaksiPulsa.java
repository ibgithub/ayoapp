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
 * A TransaksiPulsa.
 */
@Entity
@Table(name = "transaksi_pulsa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transaksipulsa")
public class TransaksiPulsa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kode_produk")
    private String kodeProduk;

    @Column(name = "hp_tujuan")
    private String hpTujuan;

    @Column(name = "hp_member")
    private String hpMember;

    @Column(name = "harga_beli", precision=10, scale=2)
    private BigDecimal hargaBeli;

    @Column(name = "hpp", precision=10, scale=2)
    private BigDecimal hpp;

    @Column(name = "laba", precision=10, scale=2)
    private BigDecimal laba;

    @Column(name = "com")
    private String com;

    @Column(name = "admrpt", precision=10, scale=2)
    private BigDecimal admrpt;

    @Column(name = "ulang")
    private Integer ulang;

    @Column(name = "ulang_tgl")
    private String ulangTgl;

    @Column(name = "fisik")
    private Integer fisik;

    @Column(name = "manual")
    private Integer manual;

    @Column(name = "switch_1")
    private Integer switch1;

    @Column(name = "kode_gagal")
    private Integer kodeGagal;

    @Column(name = "wait_sms")
    private Integer waitSms;

    @Column(name = "head_2_head")
    private Integer head2Head;

    @Column(name = "hp_pembeli")
    private String hpPembeli;

    @Column(name = "bea_admin", precision=10, scale=2)
    private BigDecimal beaAdmin;

    @Column(name = "is_report")
    private Integer isReport;

    @Column(name = "suplier_ke")
    private Integer suplierKe;

    @Column(name = "id_distributor")
    private Long idDistributor;

    @Column(name = "sn")
    private String sn;

    @Column(name = "ip")
    private String ip;

    @Column(name = "pesankirim")
    private String pesankirim;

    @Column(name = "metode")
    private Integer metode;

    @Column(name = "to_distributor")
    private String toDistributor;

    @Column(name = "id_portip")
    private Integer idPortip;

    @Column(name = "timeupdate")
    private ZonedDateTime timeupdate;

    @Column(name = "id_distributor_old")
    private Long idDistributorOld;

    @Column(name = "id_distributor_produk")
    private Long idDistributorProduk;

    @Column(name = "saldo_sup", precision=10, scale=2)
    private BigDecimal saldoSup;

    @Column(name = "isrebate")
    private Integer isrebate;

    @Column(name = "enginename")
    private String enginename;

    @Column(name = "typemsg")
    private Integer typemsg;

    @Column(name = "isro")
    private Integer isro;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public TransaksiPulsa kodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
        return this;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public String getHpTujuan() {
        return hpTujuan;
    }

    public TransaksiPulsa hpTujuan(String hpTujuan) {
        this.hpTujuan = hpTujuan;
        return this;
    }

    public void setHpTujuan(String hpTujuan) {
        this.hpTujuan = hpTujuan;
    }

    public String getHpMember() {
        return hpMember;
    }

    public TransaksiPulsa hpMember(String hpMember) {
        this.hpMember = hpMember;
        return this;
    }

    public void setHpMember(String hpMember) {
        this.hpMember = hpMember;
    }

    public BigDecimal getHargaBeli() {
        return hargaBeli;
    }

    public TransaksiPulsa hargaBeli(BigDecimal hargaBeli) {
        this.hargaBeli = hargaBeli;
        return this;
    }

    public void setHargaBeli(BigDecimal hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public BigDecimal getHpp() {
        return hpp;
    }

    public TransaksiPulsa hpp(BigDecimal hpp) {
        this.hpp = hpp;
        return this;
    }

    public void setHpp(BigDecimal hpp) {
        this.hpp = hpp;
    }

    public BigDecimal getLaba() {
        return laba;
    }

    public TransaksiPulsa laba(BigDecimal laba) {
        this.laba = laba;
        return this;
    }

    public void setLaba(BigDecimal laba) {
        this.laba = laba;
    }

    public String getCom() {
        return com;
    }

    public TransaksiPulsa com(String com) {
        this.com = com;
        return this;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public BigDecimal getAdmrpt() {
        return admrpt;
    }

    public TransaksiPulsa admrpt(BigDecimal admrpt) {
        this.admrpt = admrpt;
        return this;
    }

    public void setAdmrpt(BigDecimal admrpt) {
        this.admrpt = admrpt;
    }

    public Integer getUlang() {
        return ulang;
    }

    public TransaksiPulsa ulang(Integer ulang) {
        this.ulang = ulang;
        return this;
    }

    public void setUlang(Integer ulang) {
        this.ulang = ulang;
    }

    public String getUlangTgl() {
        return ulangTgl;
    }

    public TransaksiPulsa ulangTgl(String ulangTgl) {
        this.ulangTgl = ulangTgl;
        return this;
    }

    public void setUlangTgl(String ulangTgl) {
        this.ulangTgl = ulangTgl;
    }

    public Integer getFisik() {
        return fisik;
    }

    public TransaksiPulsa fisik(Integer fisik) {
        this.fisik = fisik;
        return this;
    }

    public void setFisik(Integer fisik) {
        this.fisik = fisik;
    }

    public Integer getManual() {
        return manual;
    }

    public TransaksiPulsa manual(Integer manual) {
        this.manual = manual;
        return this;
    }

    public void setManual(Integer manual) {
        this.manual = manual;
    }

    public Integer getSwitch1() {
        return switch1;
    }

    public TransaksiPulsa switch1(Integer switch1) {
        this.switch1 = switch1;
        return this;
    }

    public void setSwitch1(Integer switch1) {
        this.switch1 = switch1;
    }

    public Integer getKodeGagal() {
        return kodeGagal;
    }

    public TransaksiPulsa kodeGagal(Integer kodeGagal) {
        this.kodeGagal = kodeGagal;
        return this;
    }

    public void setKodeGagal(Integer kodeGagal) {
        this.kodeGagal = kodeGagal;
    }

    public Integer getWaitSms() {
        return waitSms;
    }

    public TransaksiPulsa waitSms(Integer waitSms) {
        this.waitSms = waitSms;
        return this;
    }

    public void setWaitSms(Integer waitSms) {
        this.waitSms = waitSms;
    }

    public Integer getHead2Head() {
        return head2Head;
    }

    public TransaksiPulsa head2Head(Integer head2Head) {
        this.head2Head = head2Head;
        return this;
    }

    public void setHead2Head(Integer head2Head) {
        this.head2Head = head2Head;
    }

    public String getHpPembeli() {
        return hpPembeli;
    }

    public TransaksiPulsa hpPembeli(String hpPembeli) {
        this.hpPembeli = hpPembeli;
        return this;
    }

    public void setHpPembeli(String hpPembeli) {
        this.hpPembeli = hpPembeli;
    }

    public BigDecimal getBeaAdmin() {
        return beaAdmin;
    }

    public TransaksiPulsa beaAdmin(BigDecimal beaAdmin) {
        this.beaAdmin = beaAdmin;
        return this;
    }

    public void setBeaAdmin(BigDecimal beaAdmin) {
        this.beaAdmin = beaAdmin;
    }

    public Integer getIsReport() {
        return isReport;
    }

    public TransaksiPulsa isReport(Integer isReport) {
        this.isReport = isReport;
        return this;
    }

    public void setIsReport(Integer isReport) {
        this.isReport = isReport;
    }

    public Integer getSuplierKe() {
        return suplierKe;
    }

    public TransaksiPulsa suplierKe(Integer suplierKe) {
        this.suplierKe = suplierKe;
        return this;
    }

    public void setSuplierKe(Integer suplierKe) {
        this.suplierKe = suplierKe;
    }

    public Long getIdDistributor() {
        return idDistributor;
    }

    public TransaksiPulsa idDistributor(Long idDistributor) {
        this.idDistributor = idDistributor;
        return this;
    }

    public void setIdDistributor(Long idDistributor) {
        this.idDistributor = idDistributor;
    }

    public String getSn() {
        return sn;
    }

    public TransaksiPulsa sn(String sn) {
        this.sn = sn;
        return this;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getIp() {
        return ip;
    }

    public TransaksiPulsa ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPesankirim() {
        return pesankirim;
    }

    public TransaksiPulsa pesankirim(String pesankirim) {
        this.pesankirim = pesankirim;
        return this;
    }

    public void setPesankirim(String pesankirim) {
        this.pesankirim = pesankirim;
    }

    public Integer getMetode() {
        return metode;
    }

    public TransaksiPulsa metode(Integer metode) {
        this.metode = metode;
        return this;
    }

    public void setMetode(Integer metode) {
        this.metode = metode;
    }

    public String getToDistributor() {
        return toDistributor;
    }

    public TransaksiPulsa toDistributor(String toDistributor) {
        this.toDistributor = toDistributor;
        return this;
    }

    public void setToDistributor(String toDistributor) {
        this.toDistributor = toDistributor;
    }

    public Integer getIdPortip() {
        return idPortip;
    }

    public TransaksiPulsa idPortip(Integer idPortip) {
        this.idPortip = idPortip;
        return this;
    }

    public void setIdPortip(Integer idPortip) {
        this.idPortip = idPortip;
    }

    public ZonedDateTime getTimeupdate() {
        return timeupdate;
    }

    public TransaksiPulsa timeupdate(ZonedDateTime timeupdate) {
        this.timeupdate = timeupdate;
        return this;
    }

    public void setTimeupdate(ZonedDateTime timeupdate) {
        this.timeupdate = timeupdate;
    }

    public Long getIdDistributorOld() {
        return idDistributorOld;
    }

    public TransaksiPulsa idDistributorOld(Long idDistributorOld) {
        this.idDistributorOld = idDistributorOld;
        return this;
    }

    public void setIdDistributorOld(Long idDistributorOld) {
        this.idDistributorOld = idDistributorOld;
    }

    public Long getIdDistributorProduk() {
        return idDistributorProduk;
    }

    public TransaksiPulsa idDistributorProduk(Long idDistributorProduk) {
        this.idDistributorProduk = idDistributorProduk;
        return this;
    }

    public void setIdDistributorProduk(Long idDistributorProduk) {
        this.idDistributorProduk = idDistributorProduk;
    }

    public BigDecimal getSaldoSup() {
        return saldoSup;
    }

    public TransaksiPulsa saldoSup(BigDecimal saldoSup) {
        this.saldoSup = saldoSup;
        return this;
    }

    public void setSaldoSup(BigDecimal saldoSup) {
        this.saldoSup = saldoSup;
    }

    public Integer getIsrebate() {
        return isrebate;
    }

    public TransaksiPulsa isrebate(Integer isrebate) {
        this.isrebate = isrebate;
        return this;
    }

    public void setIsrebate(Integer isrebate) {
        this.isrebate = isrebate;
    }

    public String getEnginename() {
        return enginename;
    }

    public TransaksiPulsa enginename(String enginename) {
        this.enginename = enginename;
        return this;
    }

    public void setEnginename(String enginename) {
        this.enginename = enginename;
    }

    public Integer getTypemsg() {
        return typemsg;
    }

    public TransaksiPulsa typemsg(Integer typemsg) {
        this.typemsg = typemsg;
        return this;
    }

    public void setTypemsg(Integer typemsg) {
        this.typemsg = typemsg;
    }

    public Integer getIsro() {
        return isro;
    }

    public TransaksiPulsa isro(Integer isro) {
        this.isro = isro;
        return this;
    }

    public void setIsro(Integer isro) {
        this.isro = isro;
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
        TransaksiPulsa transaksiPulsa = (TransaksiPulsa) o;
        if (transaksiPulsa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaksiPulsa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransaksiPulsa{" +
            "id=" + getId() +
            ", kodeProduk='" + getKodeProduk() + "'" +
            ", hpTujuan='" + getHpTujuan() + "'" +
            ", hpMember='" + getHpMember() + "'" +
            ", hargaBeli='" + getHargaBeli() + "'" +
            ", hpp='" + getHpp() + "'" +
            ", laba='" + getLaba() + "'" +
            ", com='" + getCom() + "'" +
            ", admrpt='" + getAdmrpt() + "'" +
            ", ulang='" + getUlang() + "'" +
            ", ulangTgl='" + getUlangTgl() + "'" +
            ", fisik='" + getFisik() + "'" +
            ", manual='" + getManual() + "'" +
            ", switch1='" + getSwitch1() + "'" +
            ", kodeGagal='" + getKodeGagal() + "'" +
            ", waitSms='" + getWaitSms() + "'" +
            ", head2Head='" + getHead2Head() + "'" +
            ", hpPembeli='" + getHpPembeli() + "'" +
            ", beaAdmin='" + getBeaAdmin() + "'" +
            ", isReport='" + getIsReport() + "'" +
            ", suplierKe='" + getSuplierKe() + "'" +
            ", idDistributor='" + getIdDistributor() + "'" +
            ", sn='" + getSn() + "'" +
            ", ip='" + getIp() + "'" +
            ", pesankirim='" + getPesankirim() + "'" +
            ", metode='" + getMetode() + "'" +
            ", toDistributor='" + getToDistributor() + "'" +
            ", idPortip='" + getIdPortip() + "'" +
            ", timeupdate='" + getTimeupdate() + "'" +
            ", idDistributorOld='" + getIdDistributorOld() + "'" +
            ", idDistributorProduk='" + getIdDistributorProduk() + "'" +
            ", saldoSup='" + getSaldoSup() + "'" +
            ", isrebate='" + getIsrebate() + "'" +
            ", enginename='" + getEnginename() + "'" +
            ", typemsg='" + getTypemsg() + "'" +
            ", isro='" + getIsro() + "'" +
            "}";
    }
}
