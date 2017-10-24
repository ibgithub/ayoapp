package com.ib.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;


import io.github.jhipster.service.filter.ZonedDateTimeFilter;


/**
 * Criteria class for the LogSaldo entity. This class is used in LogSaldoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /log-saldos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LogSaldoCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter idMember;

    private BigDecimalFilter saldo;

    private BigDecimalFilter act;

    private ZonedDateTimeFilter tgl;

    private StringFilter ket;

    private IntegerFilter ref;

    private IntegerFilter tkode;

    private IntegerFilter kodetrx;

    private StringFilter msg;

    private StringFilter userInput;

    private IntegerFilter isstok;

    public LogSaldoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIdMember() {
        return idMember;
    }

    public void setIdMember(StringFilter idMember) {
        this.idMember = idMember;
    }

    public BigDecimalFilter getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimalFilter saldo) {
        this.saldo = saldo;
    }

    public BigDecimalFilter getAct() {
        return act;
    }

    public void setAct(BigDecimalFilter act) {
        this.act = act;
    }

    public ZonedDateTimeFilter getTgl() {
        return tgl;
    }

    public void setTgl(ZonedDateTimeFilter tgl) {
        this.tgl = tgl;
    }

    public StringFilter getKet() {
        return ket;
    }

    public void setKet(StringFilter ket) {
        this.ket = ket;
    }

    public IntegerFilter getRef() {
        return ref;
    }

    public void setRef(IntegerFilter ref) {
        this.ref = ref;
    }

    public IntegerFilter getTkode() {
        return tkode;
    }

    public void setTkode(IntegerFilter tkode) {
        this.tkode = tkode;
    }

    public IntegerFilter getKodetrx() {
        return kodetrx;
    }

    public void setKodetrx(IntegerFilter kodetrx) {
        this.kodetrx = kodetrx;
    }

    public StringFilter getMsg() {
        return msg;
    }

    public void setMsg(StringFilter msg) {
        this.msg = msg;
    }

    public StringFilter getUserInput() {
        return userInput;
    }

    public void setUserInput(StringFilter userInput) {
        this.userInput = userInput;
    }

    public IntegerFilter getIsstok() {
        return isstok;
    }

    public void setIsstok(IntegerFilter isstok) {
        this.isstok = isstok;
    }

    @Override
    public String toString() {
        return "LogSaldoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idMember != null ? "idMember=" + idMember + ", " : "") +
                (saldo != null ? "saldo=" + saldo + ", " : "") +
                (act != null ? "act=" + act + ", " : "") +
                (tgl != null ? "tgl=" + tgl + ", " : "") +
                (ket != null ? "ket=" + ket + ", " : "") +
                (ref != null ? "ref=" + ref + ", " : "") +
                (tkode != null ? "tkode=" + tkode + ", " : "") +
                (kodetrx != null ? "kodetrx=" + kodetrx + ", " : "") +
                (msg != null ? "msg=" + msg + ", " : "") +
                (userInput != null ? "userInput=" + userInput + ", " : "") +
                (isstok != null ? "isstok=" + isstok + ", " : "") +
            "}";
    }

}
