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
 * A LogSaldo.
 */
@Entity
@Table(name = "log_saldo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "logsaldo")
public class LogSaldo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_member")
    private String idMember;

    @Column(name = "saldo", precision=10, scale=2)
    private BigDecimal saldo;

    @Column(name = "act", precision=10, scale=2)
    private BigDecimal act;

    @Column(name = "tgl")
    private ZonedDateTime tgl;

    @Column(name = "ket")
    private String ket;

    @Column(name = "jhi_ref")
    private Integer ref;

    @Column(name = "tkode")
    private Integer tkode;

    @Column(name = "kodetrx")
    private Integer kodetrx;

    @Column(name = "msg")
    private String msg;

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

    public String getIdMember() {
        return idMember;
    }

    public LogSaldo idMember(String idMember) {
        this.idMember = idMember;
        return this;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public LogSaldo saldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getAct() {
        return act;
    }

    public LogSaldo act(BigDecimal act) {
        this.act = act;
        return this;
    }

    public void setAct(BigDecimal act) {
        this.act = act;
    }

    public ZonedDateTime getTgl() {
        return tgl;
    }

    public LogSaldo tgl(ZonedDateTime tgl) {
        this.tgl = tgl;
        return this;
    }

    public void setTgl(ZonedDateTime tgl) {
        this.tgl = tgl;
    }

    public String getKet() {
        return ket;
    }

    public LogSaldo ket(String ket) {
        this.ket = ket;
        return this;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public Integer getRef() {
        return ref;
    }

    public LogSaldo ref(Integer ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Integer getTkode() {
        return tkode;
    }

    public LogSaldo tkode(Integer tkode) {
        this.tkode = tkode;
        return this;
    }

    public void setTkode(Integer tkode) {
        this.tkode = tkode;
    }

    public Integer getKodetrx() {
        return kodetrx;
    }

    public LogSaldo kodetrx(Integer kodetrx) {
        this.kodetrx = kodetrx;
        return this;
    }

    public void setKodetrx(Integer kodetrx) {
        this.kodetrx = kodetrx;
    }

    public String getMsg() {
        return msg;
    }

    public LogSaldo msg(String msg) {
        this.msg = msg;
        return this;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserInput() {
        return userInput;
    }

    public LogSaldo userInput(String userInput) {
        this.userInput = userInput;
        return this;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public Integer getIsstok() {
        return isstok;
    }

    public LogSaldo isstok(Integer isstok) {
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
        LogSaldo logSaldo = (LogSaldo) o;
        if (logSaldo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logSaldo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogSaldo{" +
            "id=" + getId() +
            ", idMember='" + getIdMember() + "'" +
            ", saldo='" + getSaldo() + "'" +
            ", act='" + getAct() + "'" +
            ", tgl='" + getTgl() + "'" +
            ", ket='" + getKet() + "'" +
            ", ref='" + getRef() + "'" +
            ", tkode='" + getTkode() + "'" +
            ", kodetrx='" + getKodetrx() + "'" +
            ", msg='" + getMsg() + "'" +
            ", userInput='" + getUserInput() + "'" +
            ", isstok='" + getIsstok() + "'" +
            "}";
    }
}
