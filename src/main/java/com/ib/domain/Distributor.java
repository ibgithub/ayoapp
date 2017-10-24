package com.ib.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Distributor.
 */
@Entity
@Table(name = "distributor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "distributor")
public class Distributor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_distributor")
    private Long idDistributor;

    @Column(name = "nama")
    private String nama;

    @Column(name = "status")
    private Integer status;

    @Column(name = "kode_id")
    private String kodeId;

    @Column(name = "pin")
    private String pin;

    @Column(name = "com")
    private String com;

    @Column(name = "no_kontak")
    private String noKontak;

    @Column(name = "metode")
    private String metode;

    @Column(name = "kode_parsing")
    private String kodeParsing;

    @Column(name = "kode_parsing_2")
    private String kodeParsing2;

    @Column(name = "replyno")
    private String replyno;

    @Column(name = "tgl_input")
    private ZonedDateTime tglInput;

    @Column(name = "user_input")
    private String userInput;

    @Column(name = "tgl_update")
    private ZonedDateTime tglUpdate;

    @Column(name = "user_update")
    private String userUpdate;

    @Column(name = "ip")
    private String ip;

    @Column(name = "isvre")
    private Integer isvre;

    @Column(name = "isgtw")
    private Integer isgtw;

    @Column(name = "ugtw")
    private String ugtw;

    @Column(name = "isfilter")
    private Integer isfilter;

    @Column(name = "parse_saldo")
    private String parseSaldo;

    @Column(name = "parse_harga")
    private String parseHarga;

    @Column(name = "tiket_wrap")
    private String tiketWrap;

    @Column(name = "istiketsend")
    private Integer istiketsend;

    @Column(name = "pesan_tiket")
    private String pesanTiket;

    @Column(name = "saldo_supwarn")
    private Integer saldoSupwarn;

    @Column(name = "issortby")
    private Integer issortby;

    @Column(name = "parse_unit")
    private String parseUnit;

    @Column(name = "isulangim")
    private Integer isulangim;

    @Column(name = "ishlr")
    private Integer ishlr;

    @Column(name = "kode_parsing_3")
    private String kodeParsing3;

    @Column(name = "id_history")
    private Long idHistory;

    @Column(name = "kode_parsing_4")
    private String kodeParsing4;

    @Column(name = "selisih_supwarn")
    private Integer selisihSupwarn;

    @Column(name = "timeon")
    private String timeon;

    @Column(name = "timeoff")
    private String timeoff;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdDistributor() {
        return idDistributor;
    }

    public Distributor idDistributor(Long idDistributor) {
        this.idDistributor = idDistributor;
        return this;
    }

    public void setIdDistributor(Long idDistributor) {
        this.idDistributor = idDistributor;
    }

    public String getNama() {
        return nama;
    }

    public Distributor nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getStatus() {
        return status;
    }

    public Distributor status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKodeId() {
        return kodeId;
    }

    public Distributor kodeId(String kodeId) {
        this.kodeId = kodeId;
        return this;
    }

    public void setKodeId(String kodeId) {
        this.kodeId = kodeId;
    }

    public String getPin() {
        return pin;
    }

    public Distributor pin(String pin) {
        this.pin = pin;
        return this;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCom() {
        return com;
    }

    public Distributor com(String com) {
        this.com = com;
        return this;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getNoKontak() {
        return noKontak;
    }

    public Distributor noKontak(String noKontak) {
        this.noKontak = noKontak;
        return this;
    }

    public void setNoKontak(String noKontak) {
        this.noKontak = noKontak;
    }

    public String getMetode() {
        return metode;
    }

    public Distributor metode(String metode) {
        this.metode = metode;
        return this;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }

    public String getKodeParsing() {
        return kodeParsing;
    }

    public Distributor kodeParsing(String kodeParsing) {
        this.kodeParsing = kodeParsing;
        return this;
    }

    public void setKodeParsing(String kodeParsing) {
        this.kodeParsing = kodeParsing;
    }

    public String getKodeParsing2() {
        return kodeParsing2;
    }

    public Distributor kodeParsing2(String kodeParsing2) {
        this.kodeParsing2 = kodeParsing2;
        return this;
    }

    public void setKodeParsing2(String kodeParsing2) {
        this.kodeParsing2 = kodeParsing2;
    }

    public String getReplyno() {
        return replyno;
    }

    public Distributor replyno(String replyno) {
        this.replyno = replyno;
        return this;
    }

    public void setReplyno(String replyno) {
        this.replyno = replyno;
    }

    public ZonedDateTime getTglInput() {
        return tglInput;
    }

    public Distributor tglInput(ZonedDateTime tglInput) {
        this.tglInput = tglInput;
        return this;
    }

    public void setTglInput(ZonedDateTime tglInput) {
        this.tglInput = tglInput;
    }

    public String getUserInput() {
        return userInput;
    }

    public Distributor userInput(String userInput) {
        this.userInput = userInput;
        return this;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public ZonedDateTime getTglUpdate() {
        return tglUpdate;
    }

    public Distributor tglUpdate(ZonedDateTime tglUpdate) {
        this.tglUpdate = tglUpdate;
        return this;
    }

    public void setTglUpdate(ZonedDateTime tglUpdate) {
        this.tglUpdate = tglUpdate;
    }

    public String getUserUpdate() {
        return userUpdate;
    }

    public Distributor userUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
        return this;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }

    public String getIp() {
        return ip;
    }

    public Distributor ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getIsvre() {
        return isvre;
    }

    public Distributor isvre(Integer isvre) {
        this.isvre = isvre;
        return this;
    }

    public void setIsvre(Integer isvre) {
        this.isvre = isvre;
    }

    public Integer getIsgtw() {
        return isgtw;
    }

    public Distributor isgtw(Integer isgtw) {
        this.isgtw = isgtw;
        return this;
    }

    public void setIsgtw(Integer isgtw) {
        this.isgtw = isgtw;
    }

    public String getUgtw() {
        return ugtw;
    }

    public Distributor ugtw(String ugtw) {
        this.ugtw = ugtw;
        return this;
    }

    public void setUgtw(String ugtw) {
        this.ugtw = ugtw;
    }

    public Integer getIsfilter() {
        return isfilter;
    }

    public Distributor isfilter(Integer isfilter) {
        this.isfilter = isfilter;
        return this;
    }

    public void setIsfilter(Integer isfilter) {
        this.isfilter = isfilter;
    }

    public String getParseSaldo() {
        return parseSaldo;
    }

    public Distributor parseSaldo(String parseSaldo) {
        this.parseSaldo = parseSaldo;
        return this;
    }

    public void setParseSaldo(String parseSaldo) {
        this.parseSaldo = parseSaldo;
    }

    public String getParseHarga() {
        return parseHarga;
    }

    public Distributor parseHarga(String parseHarga) {
        this.parseHarga = parseHarga;
        return this;
    }

    public void setParseHarga(String parseHarga) {
        this.parseHarga = parseHarga;
    }

    public String getTiketWrap() {
        return tiketWrap;
    }

    public Distributor tiketWrap(String tiketWrap) {
        this.tiketWrap = tiketWrap;
        return this;
    }

    public void setTiketWrap(String tiketWrap) {
        this.tiketWrap = tiketWrap;
    }

    public Integer getIstiketsend() {
        return istiketsend;
    }

    public Distributor istiketsend(Integer istiketsend) {
        this.istiketsend = istiketsend;
        return this;
    }

    public void setIstiketsend(Integer istiketsend) {
        this.istiketsend = istiketsend;
    }

    public String getPesanTiket() {
        return pesanTiket;
    }

    public Distributor pesanTiket(String pesanTiket) {
        this.pesanTiket = pesanTiket;
        return this;
    }

    public void setPesanTiket(String pesanTiket) {
        this.pesanTiket = pesanTiket;
    }

    public Integer getSaldoSupwarn() {
        return saldoSupwarn;
    }

    public Distributor saldoSupwarn(Integer saldoSupwarn) {
        this.saldoSupwarn = saldoSupwarn;
        return this;
    }

    public void setSaldoSupwarn(Integer saldoSupwarn) {
        this.saldoSupwarn = saldoSupwarn;
    }

    public Integer getIssortby() {
        return issortby;
    }

    public Distributor issortby(Integer issortby) {
        this.issortby = issortby;
        return this;
    }

    public void setIssortby(Integer issortby) {
        this.issortby = issortby;
    }

    public String getParseUnit() {
        return parseUnit;
    }

    public Distributor parseUnit(String parseUnit) {
        this.parseUnit = parseUnit;
        return this;
    }

    public void setParseUnit(String parseUnit) {
        this.parseUnit = parseUnit;
    }

    public Integer getIsulangim() {
        return isulangim;
    }

    public Distributor isulangim(Integer isulangim) {
        this.isulangim = isulangim;
        return this;
    }

    public void setIsulangim(Integer isulangim) {
        this.isulangim = isulangim;
    }

    public Integer getIshlr() {
        return ishlr;
    }

    public Distributor ishlr(Integer ishlr) {
        this.ishlr = ishlr;
        return this;
    }

    public void setIshlr(Integer ishlr) {
        this.ishlr = ishlr;
    }

    public String getKodeParsing3() {
        return kodeParsing3;
    }

    public Distributor kodeParsing3(String kodeParsing3) {
        this.kodeParsing3 = kodeParsing3;
        return this;
    }

    public void setKodeParsing3(String kodeParsing3) {
        this.kodeParsing3 = kodeParsing3;
    }

    public Long getIdHistory() {
        return idHistory;
    }

    public Distributor idHistory(Long idHistory) {
        this.idHistory = idHistory;
        return this;
    }

    public void setIdHistory(Long idHistory) {
        this.idHistory = idHistory;
    }

    public String getKodeParsing4() {
        return kodeParsing4;
    }

    public Distributor kodeParsing4(String kodeParsing4) {
        this.kodeParsing4 = kodeParsing4;
        return this;
    }

    public void setKodeParsing4(String kodeParsing4) {
        this.kodeParsing4 = kodeParsing4;
    }

    public Integer getSelisihSupwarn() {
        return selisihSupwarn;
    }

    public Distributor selisihSupwarn(Integer selisihSupwarn) {
        this.selisihSupwarn = selisihSupwarn;
        return this;
    }

    public void setSelisihSupwarn(Integer selisihSupwarn) {
        this.selisihSupwarn = selisihSupwarn;
    }

    public String getTimeon() {
        return timeon;
    }

    public Distributor timeon(String timeon) {
        this.timeon = timeon;
        return this;
    }

    public void setTimeon(String timeon) {
        this.timeon = timeon;
    }

    public String getTimeoff() {
        return timeoff;
    }

    public Distributor timeoff(String timeoff) {
        this.timeoff = timeoff;
        return this;
    }

    public void setTimeoff(String timeoff) {
        this.timeoff = timeoff;
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
        Distributor distributor = (Distributor) o;
        if (distributor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), distributor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Distributor{" +
            "id=" + getId() +
            ", idDistributor='" + getIdDistributor() + "'" +
            ", nama='" + getNama() + "'" +
            ", status='" + getStatus() + "'" +
            ", kodeId='" + getKodeId() + "'" +
            ", pin='" + getPin() + "'" +
            ", com='" + getCom() + "'" +
            ", noKontak='" + getNoKontak() + "'" +
            ", metode='" + getMetode() + "'" +
            ", kodeParsing='" + getKodeParsing() + "'" +
            ", kodeParsing2='" + getKodeParsing2() + "'" +
            ", replyno='" + getReplyno() + "'" +
            ", tglInput='" + getTglInput() + "'" +
            ", userInput='" + getUserInput() + "'" +
            ", tglUpdate='" + getTglUpdate() + "'" +
            ", userUpdate='" + getUserUpdate() + "'" +
            ", ip='" + getIp() + "'" +
            ", isvre='" + getIsvre() + "'" +
            ", isgtw='" + getIsgtw() + "'" +
            ", ugtw='" + getUgtw() + "'" +
            ", isfilter='" + getIsfilter() + "'" +
            ", parseSaldo='" + getParseSaldo() + "'" +
            ", parseHarga='" + getParseHarga() + "'" +
            ", tiketWrap='" + getTiketWrap() + "'" +
            ", istiketsend='" + getIstiketsend() + "'" +
            ", pesanTiket='" + getPesanTiket() + "'" +
            ", saldoSupwarn='" + getSaldoSupwarn() + "'" +
            ", issortby='" + getIssortby() + "'" +
            ", parseUnit='" + getParseUnit() + "'" +
            ", isulangim='" + getIsulangim() + "'" +
            ", ishlr='" + getIshlr() + "'" +
            ", kodeParsing3='" + getKodeParsing3() + "'" +
            ", idHistory='" + getIdHistory() + "'" +
            ", kodeParsing4='" + getKodeParsing4() + "'" +
            ", selisihSupwarn='" + getSelisihSupwarn() + "'" +
            ", timeon='" + getTimeon() + "'" +
            ", timeoff='" + getTimeoff() + "'" +
            "}";
    }
}
