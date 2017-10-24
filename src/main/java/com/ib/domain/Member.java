package com.ib.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_member", nullable = false)
    private String idMember;

    @NotNull
    @Column(name = "tgl_register", nullable = false)
    private LocalDate tglRegister;

    @Column(name = "nama")
    private String nama;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "pin")
    private String pin;

    @Column(name = "status")
    private Integer status;

    @NotNull
    @Column(name = "saldo", precision=10, scale=2, nullable = false)
    private BigDecimal saldo;

    @Column(name = "id_upline")
    private String idUpline;

    @Column(name = "kode_level")
    private String kodeLevel;

    @Column(name = "tgl_input")
    private ZonedDateTime tglInput;

    @Column(name = "user_input")
    private String userInput;

    @Column(name = "tgl_update")
    private ZonedDateTime tglUpdate;

    @Column(name = "user_update")
    private String userUpdate;

    @Column(name = "id_master")
    private String idMaster;

    @NotNull
    @Column(name = "rpt_trx", nullable = false)
    private Integer rptTrx;

    @NotNull
    @Column(name = "selisih", precision=10, scale=2, nullable = false)
    private BigDecimal selisih;

    @Column(name = "counter")
    private Integer counter;

    @Column(name = "dongle_no")
    private String dongleNo;

    @Column(name = "head_2_head")
    private Integer head2head;

    @Column(name = "ymid")
    private String ymid;

    @Column(name = "iprpt")
    private String iprpt;

    @Column(name = "last_trx")
    private ZonedDateTime lastTrx;

    @Column(name = "jhi_ref")
    private String ref;

    @Column(name = "crypt")
    private String crypt;

    @Column(name = "gtalkid")
    private String gtalkid;

    @Column(name = "vremsgid")
    private String vremsgid;

    @Column(name = "kodepos")
    private String kodepos;

    @Column(name = "iswarn")
    private Integer iswarn;

    @Column(name = "msnid")
    private String msnid;

    @Column(name = "idlogsal")
    private Long idlogsal;

    @Column(name = "last_kodetrx")
    private Long lastKodetrx;

    @Column(name = "last_idtrx")
    private Long lastIdtrx;

    @Column(name = "telebotid")
    private String telebotid;

    @Column(name = "telegramid")
    private String telegramid;

    @Column(name = "isowner")
    private Long isowner;

    @Column(name = "cryptowner")
    private String cryptowner;

    @Column(name = "pinowner")
    private String pinowner;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdMember() {
        return idMember;
    }

    public Member idMember(String idMember) {
        this.idMember = idMember;
        return this;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

    public LocalDate getTglRegister() {
        return tglRegister;
    }

    public Member tglRegister(LocalDate tglRegister) {
        this.tglRegister = tglRegister;
        return this;
    }

    public void setTglRegister(LocalDate tglRegister) {
        this.tglRegister = tglRegister;
    }

    public String getNama() {
        return nama;
    }

    public Member nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public Member alamat(String alamat) {
        this.alamat = alamat;
        return this;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPin() {
        return pin;
    }

    public Member pin(String pin) {
        this.pin = pin;
        return this;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getStatus() {
        return status;
    }

    public Member status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Member saldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getIdUpline() {
        return idUpline;
    }

    public Member idUpline(String idUpline) {
        this.idUpline = idUpline;
        return this;
    }

    public void setIdUpline(String idUpline) {
        this.idUpline = idUpline;
    }

    public String getKodeLevel() {
        return kodeLevel;
    }

    public Member kodeLevel(String kodeLevel) {
        this.kodeLevel = kodeLevel;
        return this;
    }

    public void setKodeLevel(String kodeLevel) {
        this.kodeLevel = kodeLevel;
    }

    public ZonedDateTime getTglInput() {
        return tglInput;
    }

    public Member tglInput(ZonedDateTime tglInput) {
        this.tglInput = tglInput;
        return this;
    }

    public void setTglInput(ZonedDateTime tglInput) {
        this.tglInput = tglInput;
    }

    public String getUserInput() {
        return userInput;
    }

    public Member userInput(String userInput) {
        this.userInput = userInput;
        return this;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public ZonedDateTime getTglUpdate() {
        return tglUpdate;
    }

    public Member tglUpdate(ZonedDateTime tglUpdate) {
        this.tglUpdate = tglUpdate;
        return this;
    }

    public void setTglUpdate(ZonedDateTime tglUpdate) {
        this.tglUpdate = tglUpdate;
    }

    public String getUserUpdate() {
        return userUpdate;
    }

    public Member userUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
        return this;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }

    public String getIdMaster() {
        return idMaster;
    }

    public Member idMaster(String idMaster) {
        this.idMaster = idMaster;
        return this;
    }

    public void setIdMaster(String idMaster) {
        this.idMaster = idMaster;
    }

    public Integer getRptTrx() {
        return rptTrx;
    }

    public Member rptTrx(Integer rptTrx) {
        this.rptTrx = rptTrx;
        return this;
    }

    public void setRptTrx(Integer rptTrx) {
        this.rptTrx = rptTrx;
    }

    public BigDecimal getSelisih() {
        return selisih;
    }

    public Member selisih(BigDecimal selisih) {
        this.selisih = selisih;
        return this;
    }

    public void setSelisih(BigDecimal selisih) {
        this.selisih = selisih;
    }

    public Integer getCounter() {
        return counter;
    }

    public Member counter(Integer counter) {
        this.counter = counter;
        return this;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String getDongleNo() {
        return dongleNo;
    }

    public Member dongleNo(String dongleNo) {
        this.dongleNo = dongleNo;
        return this;
    }

    public void setDongleNo(String dongleNo) {
        this.dongleNo = dongleNo;
    }

    public Integer getHead2head() {
        return head2head;
    }

    public Member head2head(Integer head2head) {
        this.head2head = head2head;
        return this;
    }

    public void setHead2head(Integer head2head) {
        this.head2head = head2head;
    }

    public String getYmid() {
        return ymid;
    }

    public Member ymid(String ymid) {
        this.ymid = ymid;
        return this;
    }

    public void setYmid(String ymid) {
        this.ymid = ymid;
    }

    public String getIprpt() {
        return iprpt;
    }

    public Member iprpt(String iprpt) {
        this.iprpt = iprpt;
        return this;
    }

    public void setIprpt(String iprpt) {
        this.iprpt = iprpt;
    }

    public ZonedDateTime getLastTrx() {
        return lastTrx;
    }

    public Member lastTrx(ZonedDateTime lastTrx) {
        this.lastTrx = lastTrx;
        return this;
    }

    public void setLastTrx(ZonedDateTime lastTrx) {
        this.lastTrx = lastTrx;
    }

    public String getRef() {
        return ref;
    }

    public Member ref(String ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getCrypt() {
        return crypt;
    }

    public Member crypt(String crypt) {
        this.crypt = crypt;
        return this;
    }

    public void setCrypt(String crypt) {
        this.crypt = crypt;
    }

    public String getGtalkid() {
        return gtalkid;
    }

    public Member gtalkid(String gtalkid) {
        this.gtalkid = gtalkid;
        return this;
    }

    public void setGtalkid(String gtalkid) {
        this.gtalkid = gtalkid;
    }

    public String getVremsgid() {
        return vremsgid;
    }

    public Member vremsgid(String vremsgid) {
        this.vremsgid = vremsgid;
        return this;
    }

    public void setVremsgid(String vremsgid) {
        this.vremsgid = vremsgid;
    }

    public String getKodepos() {
        return kodepos;
    }

    public Member kodepos(String kodepos) {
        this.kodepos = kodepos;
        return this;
    }

    public void setKodepos(String kodepos) {
        this.kodepos = kodepos;
    }

    public Integer getIswarn() {
        return iswarn;
    }

    public Member iswarn(Integer iswarn) {
        this.iswarn = iswarn;
        return this;
    }

    public void setIswarn(Integer iswarn) {
        this.iswarn = iswarn;
    }

    public String getMsnid() {
        return msnid;
    }

    public Member msnid(String msnid) {
        this.msnid = msnid;
        return this;
    }

    public void setMsnid(String msnid) {
        this.msnid = msnid;
    }

    public Long getIdlogsal() {
        return idlogsal;
    }

    public Member idlogsal(Long idlogsal) {
        this.idlogsal = idlogsal;
        return this;
    }

    public void setIdlogsal(Long idlogsal) {
        this.idlogsal = idlogsal;
    }

    public Long getLastKodetrx() {
        return lastKodetrx;
    }

    public Member lastKodetrx(Long lastKodetrx) {
        this.lastKodetrx = lastKodetrx;
        return this;
    }

    public void setLastKodetrx(Long lastKodetrx) {
        this.lastKodetrx = lastKodetrx;
    }

    public Long getLastIdtrx() {
        return lastIdtrx;
    }

    public Member lastIdtrx(Long lastIdtrx) {
        this.lastIdtrx = lastIdtrx;
        return this;
    }

    public void setLastIdtrx(Long lastIdtrx) {
        this.lastIdtrx = lastIdtrx;
    }

    public String getTelebotid() {
        return telebotid;
    }

    public Member telebotid(String telebotid) {
        this.telebotid = telebotid;
        return this;
    }

    public void setTelebotid(String telebotid) {
        this.telebotid = telebotid;
    }

    public String getTelegramid() {
        return telegramid;
    }

    public Member telegramid(String telegramid) {
        this.telegramid = telegramid;
        return this;
    }

    public void setTelegramid(String telegramid) {
        this.telegramid = telegramid;
    }

    public Long getIsowner() {
        return isowner;
    }

    public Member isowner(Long isowner) {
        this.isowner = isowner;
        return this;
    }

    public void setIsowner(Long isowner) {
        this.isowner = isowner;
    }

    public String getCryptowner() {
        return cryptowner;
    }

    public Member cryptowner(String cryptowner) {
        this.cryptowner = cryptowner;
        return this;
    }

    public void setCryptowner(String cryptowner) {
        this.cryptowner = cryptowner;
    }

    public String getPinowner() {
        return pinowner;
    }

    public Member pinowner(String pinowner) {
        this.pinowner = pinowner;
        return this;
    }

    public void setPinowner(String pinowner) {
        this.pinowner = pinowner;
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
        Member member = (Member) o;
        if (member.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", idMember='" + getIdMember() + "'" +
            ", tglRegister='" + getTglRegister() + "'" +
            ", nama='" + getNama() + "'" +
            ", alamat='" + getAlamat() + "'" +
            ", pin='" + getPin() + "'" +
            ", status='" + getStatus() + "'" +
            ", saldo='" + getSaldo() + "'" +
            ", idUpline='" + getIdUpline() + "'" +
            ", kodeLevel='" + getKodeLevel() + "'" +
            ", tglInput='" + getTglInput() + "'" +
            ", userInput='" + getUserInput() + "'" +
            ", tglUpdate='" + getTglUpdate() + "'" +
            ", userUpdate='" + getUserUpdate() + "'" +
            ", idMaster='" + getIdMaster() + "'" +
            ", rptTrx='" + getRptTrx() + "'" +
            ", selisih='" + getSelisih() + "'" +
            ", counter='" + getCounter() + "'" +
            ", dongleNo='" + getDongleNo() + "'" +
            ", head2head='" + getHead2head() + "'" +
            ", ymid='" + getYmid() + "'" +
            ", iprpt='" + getIprpt() + "'" +
            ", lastTrx='" + getLastTrx() + "'" +
            ", ref='" + getRef() + "'" +
            ", crypt='" + getCrypt() + "'" +
            ", gtalkid='" + getGtalkid() + "'" +
            ", vremsgid='" + getVremsgid() + "'" +
            ", kodepos='" + getKodepos() + "'" +
            ", iswarn='" + getIswarn() + "'" +
            ", msnid='" + getMsnid() + "'" +
            ", idlogsal='" + getIdlogsal() + "'" +
            ", lastKodetrx='" + getLastKodetrx() + "'" +
            ", lastIdtrx='" + getLastIdtrx() + "'" +
            ", telebotid='" + getTelebotid() + "'" +
            ", telegramid='" + getTelegramid() + "'" +
            ", isowner='" + getIsowner() + "'" +
            ", cryptowner='" + getCryptowner() + "'" +
            ", pinowner='" + getPinowner() + "'" +
            "}";
    }
}
