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
 * Criteria class for the Transaksi entity. This class is used in TransaksiResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /transaksis?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransaksiCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private ZonedDateTimeFilter tglTransaksi;

    private StringFilter idMember;

    private StringFilter nama;

    private BigDecimalFilter jml;

    private IntegerFilter kodeTrx;

    private IntegerFilter status;

    private BigDecimalFilter saldoAwal;

    private BigDecimalFilter saldoAkhir;

    private StringFilter ket;

    private ZonedDateTimeFilter tglInput;

    private StringFilter userInput;

    private IntegerFilter isstok;

    public TransaksiCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getTglTransaksi() {
        return tglTransaksi;
    }

    public void setTglTransaksi(ZonedDateTimeFilter tglTransaksi) {
        this.tglTransaksi = tglTransaksi;
    }

    public StringFilter getIdMember() {
        return idMember;
    }

    public void setIdMember(StringFilter idMember) {
        this.idMember = idMember;
    }

    public StringFilter getNama() {
        return nama;
    }

    public void setNama(StringFilter nama) {
        this.nama = nama;
    }

    public BigDecimalFilter getJml() {
        return jml;
    }

    public void setJml(BigDecimalFilter jml) {
        this.jml = jml;
    }

    public IntegerFilter getKodeTrx() {
        return kodeTrx;
    }

    public void setKodeTrx(IntegerFilter kodeTrx) {
        this.kodeTrx = kodeTrx;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public BigDecimalFilter getSaldoAwal() {
        return saldoAwal;
    }

    public void setSaldoAwal(BigDecimalFilter saldoAwal) {
        this.saldoAwal = saldoAwal;
    }

    public BigDecimalFilter getSaldoAkhir() {
        return saldoAkhir;
    }

    public void setSaldoAkhir(BigDecimalFilter saldoAkhir) {
        this.saldoAkhir = saldoAkhir;
    }

    public StringFilter getKet() {
        return ket;
    }

    public void setKet(StringFilter ket) {
        this.ket = ket;
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

    public IntegerFilter getIsstok() {
        return isstok;
    }

    public void setIsstok(IntegerFilter isstok) {
        this.isstok = isstok;
    }

    @Override
    public String toString() {
        return "TransaksiCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tglTransaksi != null ? "tglTransaksi=" + tglTransaksi + ", " : "") +
                (idMember != null ? "idMember=" + idMember + ", " : "") +
                (nama != null ? "nama=" + nama + ", " : "") +
                (jml != null ? "jml=" + jml + ", " : "") +
                (kodeTrx != null ? "kodeTrx=" + kodeTrx + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (saldoAwal != null ? "saldoAwal=" + saldoAwal + ", " : "") +
                (saldoAkhir != null ? "saldoAkhir=" + saldoAkhir + ", " : "") +
                (ket != null ? "ket=" + ket + ", " : "") +
                (tglInput != null ? "tglInput=" + tglInput + ", " : "") +
                (userInput != null ? "userInput=" + userInput + ", " : "") +
                (isstok != null ? "isstok=" + isstok + ", " : "") +
            "}";
    }

}
