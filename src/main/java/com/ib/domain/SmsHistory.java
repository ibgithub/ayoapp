package com.ib.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SmsHistory.
 */
@Entity
@Table(name = "sms_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "smshistory")
public class SmsHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tgl_input")
    private ZonedDateTime tglInput;

    @Column(name = "no_hp")
    private String noHp;

    @Column(name = "nama")
    private String nama;

    @Column(name = "pesan")
    private String pesan;

    @Column(name = "tipe")
    private Integer tipe;

    @Column(name = "tgl_sms")
    private ZonedDateTime tglSms;

    @Column(name = "com")
    private String com;

    @Column(name = "report")
    private Integer report;

    @Column(name = "trx")
    private Integer trx;

    @Column(name = "posting")
    private Integer posting;

    @Column(name = "jhi_ref")
    private String ref;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "enginename")
    private String enginename;

    @Column(name = "ip")
    private String ip;

    @Column(name = "typemsg")
    private Integer typemsg;

    @Column(name = "id_member")
    private String idMember;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTglInput() {
        return tglInput;
    }

    public SmsHistory tglInput(ZonedDateTime tglInput) {
        this.tglInput = tglInput;
        return this;
    }

    public void setTglInput(ZonedDateTime tglInput) {
        this.tglInput = tglInput;
    }

    public String getNoHp() {
        return noHp;
    }

    public SmsHistory noHp(String noHp) {
        this.noHp = noHp;
        return this;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getNama() {
        return nama;
    }

    public SmsHistory nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPesan() {
        return pesan;
    }

    public SmsHistory pesan(String pesan) {
        this.pesan = pesan;
        return this;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public Integer getTipe() {
        return tipe;
    }

    public SmsHistory tipe(Integer tipe) {
        this.tipe = tipe;
        return this;
    }

    public void setTipe(Integer tipe) {
        this.tipe = tipe;
    }

    public ZonedDateTime getTglSms() {
        return tglSms;
    }

    public SmsHistory tglSms(ZonedDateTime tglSms) {
        this.tglSms = tglSms;
        return this;
    }

    public void setTglSms(ZonedDateTime tglSms) {
        this.tglSms = tglSms;
    }

    public String getCom() {
        return com;
    }

    public SmsHistory com(String com) {
        this.com = com;
        return this;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public Integer getReport() {
        return report;
    }

    public SmsHistory report(Integer report) {
        this.report = report;
        return this;
    }

    public void setReport(Integer report) {
        this.report = report;
    }

    public Integer getTrx() {
        return trx;
    }

    public SmsHistory trx(Integer trx) {
        this.trx = trx;
        return this;
    }

    public void setTrx(Integer trx) {
        this.trx = trx;
    }

    public Integer getPosting() {
        return posting;
    }

    public SmsHistory posting(Integer posting) {
        this.posting = posting;
        return this;
    }

    public void setPosting(Integer posting) {
        this.posting = posting;
    }

    public String getRef() {
        return ref;
    }

    public SmsHistory ref(String ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public SmsHistory msisdn(String msisdn) {
        this.msisdn = msisdn;
        return this;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getEnginename() {
        return enginename;
    }

    public SmsHistory enginename(String enginename) {
        this.enginename = enginename;
        return this;
    }

    public void setEnginename(String enginename) {
        this.enginename = enginename;
    }

    public String getIp() {
        return ip;
    }

    public SmsHistory ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getTypemsg() {
        return typemsg;
    }

    public SmsHistory typemsg(Integer typemsg) {
        this.typemsg = typemsg;
        return this;
    }

    public void setTypemsg(Integer typemsg) {
        this.typemsg = typemsg;
    }

    public String getIdMember() {
        return idMember;
    }

    public SmsHistory idMember(String idMember) {
        this.idMember = idMember;
        return this;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
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
        SmsHistory smsHistory = (SmsHistory) o;
        if (smsHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), smsHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SmsHistory{" +
            "id=" + getId() +
            ", tglInput='" + getTglInput() + "'" +
            ", noHp='" + getNoHp() + "'" +
            ", nama='" + getNama() + "'" +
            ", pesan='" + getPesan() + "'" +
            ", tipe='" + getTipe() + "'" +
            ", tglSms='" + getTglSms() + "'" +
            ", com='" + getCom() + "'" +
            ", report='" + getReport() + "'" +
            ", trx='" + getTrx() + "'" +
            ", posting='" + getPosting() + "'" +
            ", ref='" + getRef() + "'" +
            ", msisdn='" + getMsisdn() + "'" +
            ", enginename='" + getEnginename() + "'" +
            ", ip='" + getIp() + "'" +
            ", typemsg='" + getTypemsg() + "'" +
            ", idMember='" + getIdMember() + "'" +
            "}";
    }
}
