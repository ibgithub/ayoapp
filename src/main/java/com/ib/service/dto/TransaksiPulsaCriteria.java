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
 * Criteria class for the TransaksiPulsa entity. This class is used in TransaksiPulsaResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /transaksi-pulsas?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransaksiPulsaCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter kodeProduk;

    private StringFilter hpTujuan;

    private StringFilter hpMember;

    private BigDecimalFilter hargaBeli;

    private BigDecimalFilter hpp;

    private BigDecimalFilter laba;

    private StringFilter com;

    private BigDecimalFilter admrpt;

    private IntegerFilter ulang;

    private StringFilter ulangTgl;

    private IntegerFilter fisik;

    private IntegerFilter manual;

    private IntegerFilter switch1;

    private IntegerFilter kodeGagal;

    private IntegerFilter waitSms;

    private IntegerFilter head2Head;

    private StringFilter hpPembeli;

    private BigDecimalFilter beaAdmin;

    private IntegerFilter isReport;

    private IntegerFilter suplierKe;

    private LongFilter idDistributor;

    private StringFilter sn;

    private StringFilter ip;

    private StringFilter pesankirim;

    private IntegerFilter metode;

    private StringFilter toDistributor;

    private IntegerFilter idPortip;

    private ZonedDateTimeFilter timeupdate;

    private LongFilter idDistributorOld;

    private LongFilter idDistributorProduk;

    private BigDecimalFilter saldoSup;

    private IntegerFilter isrebate;

    private StringFilter enginename;

    private IntegerFilter typemsg;

    private IntegerFilter isro;

    public TransaksiPulsaCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(StringFilter kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public StringFilter getHpTujuan() {
        return hpTujuan;
    }

    public void setHpTujuan(StringFilter hpTujuan) {
        this.hpTujuan = hpTujuan;
    }

    public StringFilter getHpMember() {
        return hpMember;
    }

    public void setHpMember(StringFilter hpMember) {
        this.hpMember = hpMember;
    }

    public BigDecimalFilter getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(BigDecimalFilter hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public BigDecimalFilter getHpp() {
        return hpp;
    }

    public void setHpp(BigDecimalFilter hpp) {
        this.hpp = hpp;
    }

    public BigDecimalFilter getLaba() {
        return laba;
    }

    public void setLaba(BigDecimalFilter laba) {
        this.laba = laba;
    }

    public StringFilter getCom() {
        return com;
    }

    public void setCom(StringFilter com) {
        this.com = com;
    }

    public BigDecimalFilter getAdmrpt() {
        return admrpt;
    }

    public void setAdmrpt(BigDecimalFilter admrpt) {
        this.admrpt = admrpt;
    }

    public IntegerFilter getUlang() {
        return ulang;
    }

    public void setUlang(IntegerFilter ulang) {
        this.ulang = ulang;
    }

    public StringFilter getUlangTgl() {
        return ulangTgl;
    }

    public void setUlangTgl(StringFilter ulangTgl) {
        this.ulangTgl = ulangTgl;
    }

    public IntegerFilter getFisik() {
        return fisik;
    }

    public void setFisik(IntegerFilter fisik) {
        this.fisik = fisik;
    }

    public IntegerFilter getManual() {
        return manual;
    }

    public void setManual(IntegerFilter manual) {
        this.manual = manual;
    }

    public IntegerFilter getSwitch1() {
        return switch1;
    }

    public void setSwitch1(IntegerFilter switch1) {
        this.switch1 = switch1;
    }

    public IntegerFilter getKodeGagal() {
        return kodeGagal;
    }

    public void setKodeGagal(IntegerFilter kodeGagal) {
        this.kodeGagal = kodeGagal;
    }

    public IntegerFilter getWaitSms() {
        return waitSms;
    }

    public void setWaitSms(IntegerFilter waitSms) {
        this.waitSms = waitSms;
    }

    public IntegerFilter getHead2Head() {
        return head2Head;
    }

    public void setHead2Head(IntegerFilter head2Head) {
        this.head2Head = head2Head;
    }

    public StringFilter getHpPembeli() {
        return hpPembeli;
    }

    public void setHpPembeli(StringFilter hpPembeli) {
        this.hpPembeli = hpPembeli;
    }

    public BigDecimalFilter getBeaAdmin() {
        return beaAdmin;
    }

    public void setBeaAdmin(BigDecimalFilter beaAdmin) {
        this.beaAdmin = beaAdmin;
    }

    public IntegerFilter getIsReport() {
        return isReport;
    }

    public void setIsReport(IntegerFilter isReport) {
        this.isReport = isReport;
    }

    public IntegerFilter getSuplierKe() {
        return suplierKe;
    }

    public void setSuplierKe(IntegerFilter suplierKe) {
        this.suplierKe = suplierKe;
    }

    public LongFilter getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(LongFilter idDistributor) {
        this.idDistributor = idDistributor;
    }

    public StringFilter getSn() {
        return sn;
    }

    public void setSn(StringFilter sn) {
        this.sn = sn;
    }

    public StringFilter getIp() {
        return ip;
    }

    public void setIp(StringFilter ip) {
        this.ip = ip;
    }

    public StringFilter getPesankirim() {
        return pesankirim;
    }

    public void setPesankirim(StringFilter pesankirim) {
        this.pesankirim = pesankirim;
    }

    public IntegerFilter getMetode() {
        return metode;
    }

    public void setMetode(IntegerFilter metode) {
        this.metode = metode;
    }

    public StringFilter getToDistributor() {
        return toDistributor;
    }

    public void setToDistributor(StringFilter toDistributor) {
        this.toDistributor = toDistributor;
    }

    public IntegerFilter getIdPortip() {
        return idPortip;
    }

    public void setIdPortip(IntegerFilter idPortip) {
        this.idPortip = idPortip;
    }

    public ZonedDateTimeFilter getTimeupdate() {
        return timeupdate;
    }

    public void setTimeupdate(ZonedDateTimeFilter timeupdate) {
        this.timeupdate = timeupdate;
    }

    public LongFilter getIdDistributorOld() {
        return idDistributorOld;
    }

    public void setIdDistributorOld(LongFilter idDistributorOld) {
        this.idDistributorOld = idDistributorOld;
    }

    public LongFilter getIdDistributorProduk() {
        return idDistributorProduk;
    }

    public void setIdDistributorProduk(LongFilter idDistributorProduk) {
        this.idDistributorProduk = idDistributorProduk;
    }

    public BigDecimalFilter getSaldoSup() {
        return saldoSup;
    }

    public void setSaldoSup(BigDecimalFilter saldoSup) {
        this.saldoSup = saldoSup;
    }

    public IntegerFilter getIsrebate() {
        return isrebate;
    }

    public void setIsrebate(IntegerFilter isrebate) {
        this.isrebate = isrebate;
    }

    public StringFilter getEnginename() {
        return enginename;
    }

    public void setEnginename(StringFilter enginename) {
        this.enginename = enginename;
    }

    public IntegerFilter getTypemsg() {
        return typemsg;
    }

    public void setTypemsg(IntegerFilter typemsg) {
        this.typemsg = typemsg;
    }

    public IntegerFilter getIsro() {
        return isro;
    }

    public void setIsro(IntegerFilter isro) {
        this.isro = isro;
    }

    @Override
    public String toString() {
        return "TransaksiPulsaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (kodeProduk != null ? "kodeProduk=" + kodeProduk + ", " : "") +
                (hpTujuan != null ? "hpTujuan=" + hpTujuan + ", " : "") +
                (hpMember != null ? "hpMember=" + hpMember + ", " : "") +
                (hargaBeli != null ? "hargaBeli=" + hargaBeli + ", " : "") +
                (hpp != null ? "hpp=" + hpp + ", " : "") +
                (laba != null ? "laba=" + laba + ", " : "") +
                (com != null ? "com=" + com + ", " : "") +
                (admrpt != null ? "admrpt=" + admrpt + ", " : "") +
                (ulang != null ? "ulang=" + ulang + ", " : "") +
                (ulangTgl != null ? "ulangTgl=" + ulangTgl + ", " : "") +
                (fisik != null ? "fisik=" + fisik + ", " : "") +
                (manual != null ? "manual=" + manual + ", " : "") +
                (switch1 != null ? "switch1=" + switch1 + ", " : "") +
                (kodeGagal != null ? "kodeGagal=" + kodeGagal + ", " : "") +
                (waitSms != null ? "waitSms=" + waitSms + ", " : "") +
                (head2Head != null ? "head2Head=" + head2Head + ", " : "") +
                (hpPembeli != null ? "hpPembeli=" + hpPembeli + ", " : "") +
                (beaAdmin != null ? "beaAdmin=" + beaAdmin + ", " : "") +
                (isReport != null ? "isReport=" + isReport + ", " : "") +
                (suplierKe != null ? "suplierKe=" + suplierKe + ", " : "") +
                (idDistributor != null ? "idDistributor=" + idDistributor + ", " : "") +
                (sn != null ? "sn=" + sn + ", " : "") +
                (ip != null ? "ip=" + ip + ", " : "") +
                (pesankirim != null ? "pesankirim=" + pesankirim + ", " : "") +
                (metode != null ? "metode=" + metode + ", " : "") +
                (toDistributor != null ? "toDistributor=" + toDistributor + ", " : "") +
                (idPortip != null ? "idPortip=" + idPortip + ", " : "") +
                (timeupdate != null ? "timeupdate=" + timeupdate + ", " : "") +
                (idDistributorOld != null ? "idDistributorOld=" + idDistributorOld + ", " : "") +
                (idDistributorProduk != null ? "idDistributorProduk=" + idDistributorProduk + ", " : "") +
                (saldoSup != null ? "saldoSup=" + saldoSup + ", " : "") +
                (isrebate != null ? "isrebate=" + isrebate + ", " : "") +
                (enginename != null ? "enginename=" + enginename + ", " : "") +
                (typemsg != null ? "typemsg=" + typemsg + ", " : "") +
                (isro != null ? "isro=" + isro + ", " : "") +
            "}";
    }

}
