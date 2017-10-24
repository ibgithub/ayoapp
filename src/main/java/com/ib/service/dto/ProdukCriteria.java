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
 * Criteria class for the Produk entity. This class is used in ProdukResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /produks?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProdukCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter idProduk;

    private StringFilter kodeProduk;

    private IntegerFilter idKartu;

    private BigDecimalFilter denom;

    private BigDecimalFilter hpp;

    private BigDecimalFilter hargaMan;

    private IntegerFilter status;

    private IntegerFilter gangguan;

    private LongFilter idDistributor;

    private IntegerFilter fisik;

    private ZonedDateTimeFilter tglUpdate;

    private StringFilter userUpdate;

    private LongFilter idDistributor2;

    private BigDecimalFilter konversiSaldo;

    private IntegerFilter isreport;

    private IntegerFilter issplit;

    private StringFilter ototimeopen;

    private StringFilter ototimeclose;

    private LongFilter idDistributor3;

    private IntegerFilter isstok;

    private IntegerFilter otoclosestatus;

    private BigDecimalFilter saldoMin;

    private IntegerFilter akses;

    private IntegerFilter hlr;

    private IntegerFilter isulang;

    private IntegerFilter isurut;

    private IntegerFilter formatppob;

    private IntegerFilter jenisppob;

    private StringFilter ketproduk;

    public ProdukCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(IntegerFilter idProduk) {
        this.idProduk = idProduk;
    }

    public StringFilter getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(StringFilter kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public IntegerFilter getIdKartu() {
        return idKartu;
    }

    public void setIdKartu(IntegerFilter idKartu) {
        this.idKartu = idKartu;
    }

    public BigDecimalFilter getDenom() {
        return denom;
    }

    public void setDenom(BigDecimalFilter denom) {
        this.denom = denom;
    }

    public BigDecimalFilter getHpp() {
        return hpp;
    }

    public void setHpp(BigDecimalFilter hpp) {
        this.hpp = hpp;
    }

    public BigDecimalFilter getHargaMan() {
        return hargaMan;
    }

    public void setHargaMan(BigDecimalFilter hargaMan) {
        this.hargaMan = hargaMan;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public IntegerFilter getGangguan() {
        return gangguan;
    }

    public void setGangguan(IntegerFilter gangguan) {
        this.gangguan = gangguan;
    }

    public LongFilter getIdDistributor() {
        return idDistributor;
    }

    public void setIdDistributor(LongFilter idDistributor) {
        this.idDistributor = idDistributor;
    }

    public IntegerFilter getFisik() {
        return fisik;
    }

    public void setFisik(IntegerFilter fisik) {
        this.fisik = fisik;
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

    public LongFilter getIdDistributor2() {
        return idDistributor2;
    }

    public void setIdDistributor2(LongFilter idDistributor2) {
        this.idDistributor2 = idDistributor2;
    }

    public BigDecimalFilter getKonversiSaldo() {
        return konversiSaldo;
    }

    public void setKonversiSaldo(BigDecimalFilter konversiSaldo) {
        this.konversiSaldo = konversiSaldo;
    }

    public IntegerFilter getIsreport() {
        return isreport;
    }

    public void setIsreport(IntegerFilter isreport) {
        this.isreport = isreport;
    }

    public IntegerFilter getIssplit() {
        return issplit;
    }

    public void setIssplit(IntegerFilter issplit) {
        this.issplit = issplit;
    }

    public StringFilter getOtotimeopen() {
        return ototimeopen;
    }

    public void setOtotimeopen(StringFilter ototimeopen) {
        this.ototimeopen = ototimeopen;
    }

    public StringFilter getOtotimeclose() {
        return ototimeclose;
    }

    public void setOtotimeclose(StringFilter ototimeclose) {
        this.ototimeclose = ototimeclose;
    }

    public LongFilter getIdDistributor3() {
        return idDistributor3;
    }

    public void setIdDistributor3(LongFilter idDistributor3) {
        this.idDistributor3 = idDistributor3;
    }

    public IntegerFilter getIsstok() {
        return isstok;
    }

    public void setIsstok(IntegerFilter isstok) {
        this.isstok = isstok;
    }

    public IntegerFilter getOtoclosestatus() {
        return otoclosestatus;
    }

    public void setOtoclosestatus(IntegerFilter otoclosestatus) {
        this.otoclosestatus = otoclosestatus;
    }

    public BigDecimalFilter getSaldoMin() {
        return saldoMin;
    }

    public void setSaldoMin(BigDecimalFilter saldoMin) {
        this.saldoMin = saldoMin;
    }

    public IntegerFilter getAkses() {
        return akses;
    }

    public void setAkses(IntegerFilter akses) {
        this.akses = akses;
    }

    public IntegerFilter getHlr() {
        return hlr;
    }

    public void setHlr(IntegerFilter hlr) {
        this.hlr = hlr;
    }

    public IntegerFilter getIsulang() {
        return isulang;
    }

    public void setIsulang(IntegerFilter isulang) {
        this.isulang = isulang;
    }

    public IntegerFilter getIsurut() {
        return isurut;
    }

    public void setIsurut(IntegerFilter isurut) {
        this.isurut = isurut;
    }

    public IntegerFilter getFormatppob() {
        return formatppob;
    }

    public void setFormatppob(IntegerFilter formatppob) {
        this.formatppob = formatppob;
    }

    public IntegerFilter getJenisppob() {
        return jenisppob;
    }

    public void setJenisppob(IntegerFilter jenisppob) {
        this.jenisppob = jenisppob;
    }

    public StringFilter getKetproduk() {
        return ketproduk;
    }

    public void setKetproduk(StringFilter ketproduk) {
        this.ketproduk = ketproduk;
    }

    @Override
    public String toString() {
        return "ProdukCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idProduk != null ? "idProduk=" + idProduk + ", " : "") +
                (kodeProduk != null ? "kodeProduk=" + kodeProduk + ", " : "") +
                (idKartu != null ? "idKartu=" + idKartu + ", " : "") +
                (denom != null ? "denom=" + denom + ", " : "") +
                (hpp != null ? "hpp=" + hpp + ", " : "") +
                (hargaMan != null ? "hargaMan=" + hargaMan + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (gangguan != null ? "gangguan=" + gangguan + ", " : "") +
                (idDistributor != null ? "idDistributor=" + idDistributor + ", " : "") +
                (fisik != null ? "fisik=" + fisik + ", " : "") +
                (tglUpdate != null ? "tglUpdate=" + tglUpdate + ", " : "") +
                (userUpdate != null ? "userUpdate=" + userUpdate + ", " : "") +
                (idDistributor2 != null ? "idDistributor2=" + idDistributor2 + ", " : "") +
                (konversiSaldo != null ? "konversiSaldo=" + konversiSaldo + ", " : "") +
                (isreport != null ? "isreport=" + isreport + ", " : "") +
                (issplit != null ? "issplit=" + issplit + ", " : "") +
                (ototimeopen != null ? "ototimeopen=" + ototimeopen + ", " : "") +
                (ototimeclose != null ? "ototimeclose=" + ototimeclose + ", " : "") +
                (idDistributor3 != null ? "idDistributor3=" + idDistributor3 + ", " : "") +
                (isstok != null ? "isstok=" + isstok + ", " : "") +
                (otoclosestatus != null ? "otoclosestatus=" + otoclosestatus + ", " : "") +
                (saldoMin != null ? "saldoMin=" + saldoMin + ", " : "") +
                (akses != null ? "akses=" + akses + ", " : "") +
                (hlr != null ? "hlr=" + hlr + ", " : "") +
                (isulang != null ? "isulang=" + isulang + ", " : "") +
                (isurut != null ? "isurut=" + isurut + ", " : "") +
                (formatppob != null ? "formatppob=" + formatppob + ", " : "") +
                (jenisppob != null ? "jenisppob=" + jenisppob + ", " : "") +
                (ketproduk != null ? "ketproduk=" + ketproduk + ", " : "") +
            "}";
    }

}
