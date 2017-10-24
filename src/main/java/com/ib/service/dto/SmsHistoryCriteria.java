package com.ib.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;



import io.github.jhipster.service.filter.ZonedDateTimeFilter;


/**
 * Criteria class for the SmsHistory entity. This class is used in SmsHistoryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /sms-histories?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SmsHistoryCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private ZonedDateTimeFilter tglInput;

    private StringFilter noHp;

    private StringFilter nama;

    private StringFilter pesan;

    private IntegerFilter tipe;

    private ZonedDateTimeFilter tglSms;

    private StringFilter com;

    private IntegerFilter report;

    private IntegerFilter trx;

    private IntegerFilter posting;

    private StringFilter ref;

    private StringFilter msisdn;

    private StringFilter enginename;

    private StringFilter ip;

    private IntegerFilter typemsg;

    private StringFilter idMember;

    public SmsHistoryCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getTglInput() {
        return tglInput;
    }

    public void setTglInput(ZonedDateTimeFilter tglInput) {
        this.tglInput = tglInput;
    }

    public StringFilter getNoHp() {
        return noHp;
    }

    public void setNoHp(StringFilter noHp) {
        this.noHp = noHp;
    }

    public StringFilter getNama() {
        return nama;
    }

    public void setNama(StringFilter nama) {
        this.nama = nama;
    }

    public StringFilter getPesan() {
        return pesan;
    }

    public void setPesan(StringFilter pesan) {
        this.pesan = pesan;
    }

    public IntegerFilter getTipe() {
        return tipe;
    }

    public void setTipe(IntegerFilter tipe) {
        this.tipe = tipe;
    }

    public ZonedDateTimeFilter getTglSms() {
        return tglSms;
    }

    public void setTglSms(ZonedDateTimeFilter tglSms) {
        this.tglSms = tglSms;
    }

    public StringFilter getCom() {
        return com;
    }

    public void setCom(StringFilter com) {
        this.com = com;
    }

    public IntegerFilter getReport() {
        return report;
    }

    public void setReport(IntegerFilter report) {
        this.report = report;
    }

    public IntegerFilter getTrx() {
        return trx;
    }

    public void setTrx(IntegerFilter trx) {
        this.trx = trx;
    }

    public IntegerFilter getPosting() {
        return posting;
    }

    public void setPosting(IntegerFilter posting) {
        this.posting = posting;
    }

    public StringFilter getRef() {
        return ref;
    }

    public void setRef(StringFilter ref) {
        this.ref = ref;
    }

    public StringFilter getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(StringFilter msisdn) {
        this.msisdn = msisdn;
    }

    public StringFilter getEnginename() {
        return enginename;
    }

    public void setEnginename(StringFilter enginename) {
        this.enginename = enginename;
    }

    public StringFilter getIp() {
        return ip;
    }

    public void setIp(StringFilter ip) {
        this.ip = ip;
    }

    public IntegerFilter getTypemsg() {
        return typemsg;
    }

    public void setTypemsg(IntegerFilter typemsg) {
        this.typemsg = typemsg;
    }

    public StringFilter getIdMember() {
        return idMember;
    }

    public void setIdMember(StringFilter idMember) {
        this.idMember = idMember;
    }

    @Override
    public String toString() {
        return "SmsHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tglInput != null ? "tglInput=" + tglInput + ", " : "") +
                (noHp != null ? "noHp=" + noHp + ", " : "") +
                (nama != null ? "nama=" + nama + ", " : "") +
                (pesan != null ? "pesan=" + pesan + ", " : "") +
                (tipe != null ? "tipe=" + tipe + ", " : "") +
                (tglSms != null ? "tglSms=" + tglSms + ", " : "") +
                (com != null ? "com=" + com + ", " : "") +
                (report != null ? "report=" + report + ", " : "") +
                (trx != null ? "trx=" + trx + ", " : "") +
                (posting != null ? "posting=" + posting + ", " : "") +
                (ref != null ? "ref=" + ref + ", " : "") +
                (msisdn != null ? "msisdn=" + msisdn + ", " : "") +
                (enginename != null ? "enginename=" + enginename + ", " : "") +
                (ip != null ? "ip=" + ip + ", " : "") +
                (typemsg != null ? "typemsg=" + typemsg + ", " : "") +
                (idMember != null ? "idMember=" + idMember + ", " : "") +
            "}";
    }

}
