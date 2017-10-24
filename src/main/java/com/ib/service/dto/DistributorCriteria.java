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
 * Criteria class for the Distributor entity. This class is used in DistributorResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /distributors?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DistributorCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter idDistributor;

    private StringFilter nama;

    private IntegerFilter status;

    private StringFilter kodeId;

    private StringFilter pin;

    private StringFilter com;

    private StringFilter noKontak;

    private StringFilter metode;

    private StringFilter kodeParsing;

    private StringFilter kodeParsing2;

    private StringFilter replyno;

    private ZonedDateTimeFilter tglInput;

    private StringFilter userInput;

    private ZonedDateTimeFilter tglUpdate;

    private StringFilter userUpdate;

    private StringFilter ip;

    private IntegerFilter isvre;

    private IntegerFilter isgtw;

    private StringFilter ugtw;

    private IntegerFilter isfilter;

    private StringFilter parseSaldo;

    private StringFilter parseHarga;

    private StringFilter tiketWrap;

    private IntegerFilter istiketsend;

    private StringFilter pesanTiket;

    private IntegerFilter saldoSupwarn;

    private IntegerFilter issortby;

    private StringFilter parseUnit;

    private IntegerFilter isulangim;

    private IntegerFilter ishlr;

    private StringFilter kodeParsing3;

    private LongFilter idHistory;

    private StringFilter kodeParsing4;

    private IntegerFilter selisihSupwarn;

    private StringFilter timeon;

    private StringFilter timeoff;

    public DistributorCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(LongFilter idDistributor) {
        this.idDistributor = idDistributor;
    }

    public StringFilter getNama() {
        return nama;
    }

    public void setNama(StringFilter nama) {
        this.nama = nama;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public StringFilter getKodeId() {
        return kodeId;
    }

    public void setKodeId(StringFilter kodeId) {
        this.kodeId = kodeId;
    }

    public StringFilter getPin() {
        return pin;
    }

    public void setPin(StringFilter pin) {
        this.pin = pin;
    }

    public StringFilter getCom() {
        return com;
    }

    public void setCom(StringFilter com) {
        this.com = com;
    }

    public StringFilter getNoKontak() {
        return noKontak;
    }

    public void setNoKontak(StringFilter noKontak) {
        this.noKontak = noKontak;
    }

    public StringFilter getMetode() {
        return metode;
    }

    public void setMetode(StringFilter metode) {
        this.metode = metode;
    }

    public StringFilter getKodeParsing() {
        return kodeParsing;
    }

    public void setKodeParsing(StringFilter kodeParsing) {
        this.kodeParsing = kodeParsing;
    }

    public StringFilter getKodeParsing2() {
        return kodeParsing2;
    }

    public void setKodeParsing2(StringFilter kodeParsing2) {
        this.kodeParsing2 = kodeParsing2;
    }

    public StringFilter getReplyno() {
        return replyno;
    }

    public void setReplyno(StringFilter replyno) {
        this.replyno = replyno;
    }

    public ZonedDateTimeFilter getTglInput() {
        return tglInput;
    }

    public void setTglInput(ZonedDateTimeFilter tglInput) {
        this.tglInput = tglInput;
    }

    public StringFilter getUserInput() {
        return userInput;
    }

    public void setUserInput(StringFilter userInput) {
        this.userInput = userInput;
    }

    public ZonedDateTimeFilter getTglUpdate() {
        return tglUpdate;
    }

    public void setTglUpdate(ZonedDateTimeFilter tglUpdate) {
        this.tglUpdate = tglUpdate;
    }

    public StringFilter getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(StringFilter userUpdate) {
        this.userUpdate = userUpdate;
    }

    public StringFilter getIp() {
        return ip;
    }

    public void setIp(StringFilter ip) {
        this.ip = ip;
    }

    public IntegerFilter getIsvre() {
        return isvre;
    }

    public void setIsvre(IntegerFilter isvre) {
        this.isvre = isvre;
    }

    public IntegerFilter getIsgtw() {
        return isgtw;
    }

    public void setIsgtw(IntegerFilter isgtw) {
        this.isgtw = isgtw;
    }

    public StringFilter getUgtw() {
        return ugtw;
    }

    public void setUgtw(StringFilter ugtw) {
        this.ugtw = ugtw;
    }

    public IntegerFilter getIsfilter() {
        return isfilter;
    }

    public void setIsfilter(IntegerFilter isfilter) {
        this.isfilter = isfilter;
    }

    public StringFilter getParseSaldo() {
        return parseSaldo;
    }

    public void setParseSaldo(StringFilter parseSaldo) {
        this.parseSaldo = parseSaldo;
    }

    public StringFilter getParseHarga() {
        return parseHarga;
    }

    public void setParseHarga(StringFilter parseHarga) {
        this.parseHarga = parseHarga;
    }

    public StringFilter getTiketWrap() {
        return tiketWrap;
    }

    public void setTiketWrap(StringFilter tiketWrap) {
        this.tiketWrap = tiketWrap;
    }

    public IntegerFilter getIstiketsend() {
        return istiketsend;
    }

    public void setIstiketsend(IntegerFilter istiketsend) {
        this.istiketsend = istiketsend;
    }

    public StringFilter getPesanTiket() {
        return pesanTiket;
    }

    public void setPesanTiket(StringFilter pesanTiket) {
        this.pesanTiket = pesanTiket;
    }

    public IntegerFilter getSaldoSupwarn() {
        return saldoSupwarn;
    }

    public void setSaldoSupwarn(IntegerFilter saldoSupwarn) {
        this.saldoSupwarn = saldoSupwarn;
    }

    public IntegerFilter getIssortby() {
        return issortby;
    }

    public void setIssortby(IntegerFilter issortby) {
        this.issortby = issortby;
    }

    public StringFilter getParseUnit() {
        return parseUnit;
    }

    public void setParseUnit(StringFilter parseUnit) {
        this.parseUnit = parseUnit;
    }

    public IntegerFilter getIsulangim() {
        return isulangim;
    }

    public void setIsulangim(IntegerFilter isulangim) {
        this.isulangim = isulangim;
    }

    public IntegerFilter getIshlr() {
        return ishlr;
    }

    public void setIshlr(IntegerFilter ishlr) {
        this.ishlr = ishlr;
    }

    public StringFilter getKodeParsing3() {
        return kodeParsing3;
    }

    public void setKodeParsing3(StringFilter kodeParsing3) {
        this.kodeParsing3 = kodeParsing3;
    }

    public LongFilter getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(LongFilter idHistory) {
        this.idHistory = idHistory;
    }

    public StringFilter getKodeParsing4() {
        return kodeParsing4;
    }

    public void setKodeParsing4(StringFilter kodeParsing4) {
        this.kodeParsing4 = kodeParsing4;
    }

    public IntegerFilter getSelisihSupwarn() {
        return selisihSupwarn;
    }

    public void setSelisihSupwarn(IntegerFilter selisihSupwarn) {
        this.selisihSupwarn = selisihSupwarn;
    }

    public StringFilter getTimeon() {
        return timeon;
    }

    public void setTimeon(StringFilter timeon) {
        this.timeon = timeon;
    }

    public StringFilter getTimeoff() {
        return timeoff;
    }

    public void setTimeoff(StringFilter timeoff) {
        this.timeoff = timeoff;
    }

    @Override
    public String toString() {
        return "DistributorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idDistributor != null ? "idDistributor=" + idDistributor + ", " : "") +
                (nama != null ? "nama=" + nama + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (kodeId != null ? "kodeId=" + kodeId + ", " : "") +
                (pin != null ? "pin=" + pin + ", " : "") +
                (com != null ? "com=" + com + ", " : "") +
                (noKontak != null ? "noKontak=" + noKontak + ", " : "") +
                (metode != null ? "metode=" + metode + ", " : "") +
                (kodeParsing != null ? "kodeParsing=" + kodeParsing + ", " : "") +
                (kodeParsing2 != null ? "kodeParsing2=" + kodeParsing2 + ", " : "") +
                (replyno != null ? "replyno=" + replyno + ", " : "") +
                (tglInput != null ? "tglInput=" + tglInput + ", " : "") +
                (userInput != null ? "userInput=" + userInput + ", " : "") +
                (tglUpdate != null ? "tglUpdate=" + tglUpdate + ", " : "") +
                (userUpdate != null ? "userUpdate=" + userUpdate + ", " : "") +
                (ip != null ? "ip=" + ip + ", " : "") +
                (isvre != null ? "isvre=" + isvre + ", " : "") +
                (isgtw != null ? "isgtw=" + isgtw + ", " : "") +
                (ugtw != null ? "ugtw=" + ugtw + ", " : "") +
                (isfilter != null ? "isfilter=" + isfilter + ", " : "") +
                (parseSaldo != null ? "parseSaldo=" + parseSaldo + ", " : "") +
                (parseHarga != null ? "parseHarga=" + parseHarga + ", " : "") +
                (tiketWrap != null ? "tiketWrap=" + tiketWrap + ", " : "") +
                (istiketsend != null ? "istiketsend=" + istiketsend + ", " : "") +
                (pesanTiket != null ? "pesanTiket=" + pesanTiket + ", " : "") +
                (saldoSupwarn != null ? "saldoSupwarn=" + saldoSupwarn + ", " : "") +
                (issortby != null ? "issortby=" + issortby + ", " : "") +
                (parseUnit != null ? "parseUnit=" + parseUnit + ", " : "") +
                (isulangim != null ? "isulangim=" + isulangim + ", " : "") +
                (ishlr != null ? "ishlr=" + ishlr + ", " : "") +
                (kodeParsing3 != null ? "kodeParsing3=" + kodeParsing3 + ", " : "") +
                (idHistory != null ? "idHistory=" + idHistory + ", " : "") +
                (kodeParsing4 != null ? "kodeParsing4=" + kodeParsing4 + ", " : "") +
                (selisihSupwarn != null ? "selisihSupwarn=" + selisihSupwarn + ", " : "") +
                (timeon != null ? "timeon=" + timeon + ", " : "") +
                (timeoff != null ? "timeoff=" + timeoff + ", " : "") +
            "}";
    }

}
