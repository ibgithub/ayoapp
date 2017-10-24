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
 * Criteria class for the Harga entity. This class is used in HargaResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /hargas?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HargaCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter idHarga;

    private IntegerFilter idProduk;

    private StringFilter idMember;

    private BigDecimalFilter hargaJual;

    private ZonedDateTimeFilter tglInput;

    private StringFilter userInput;

    private BigDecimalFilter hargaBefore;

    public HargaCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdHarga() {
        return idHarga;
    }

    public void setIdHarga(IntegerFilter idHarga) {
        this.idHarga = idHarga;
    }

    public IntegerFilter getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(IntegerFilter idProduk) {
        this.idProduk = idProduk;
    }

    public StringFilter getIdMember() {
        return idMember;
    }

    public void setIdMember(StringFilter idMember) {
        this.idMember = idMember;
    }

    public BigDecimalFilter getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(BigDecimalFilter hargaJual) {
        this.hargaJual = hargaJual;
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

    public BigDecimalFilter getHargaBefore() {
        return hargaBefore;
    }

    public void setHargaBefore(BigDecimalFilter hargaBefore) {
        this.hargaBefore = hargaBefore;
    }

    @Override
    public String toString() {
        return "HargaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idHarga != null ? "idHarga=" + idHarga + ", " : "") +
                (idProduk != null ? "idProduk=" + idProduk + ", " : "") +
                (idMember != null ? "idMember=" + idMember + ", " : "") +
                (hargaJual != null ? "hargaJual=" + hargaJual + ", " : "") +
                (tglInput != null ? "tglInput=" + tglInput + ", " : "") +
                (userInput != null ? "userInput=" + userInput + ", " : "") +
                (hargaBefore != null ? "hargaBefore=" + hargaBefore + ", " : "") +
            "}";
    }

}
