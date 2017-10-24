package com.ib.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Kartu entity. This class is used in KartuResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /kartus?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class KartuCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter idKartu;

    private StringFilter nama;

    private IntegerFilter idOperator;

    private IntegerFilter cekHlr;

    private StringFilter ketkartu;

    public KartuCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdKartu() {
        return idKartu;
    }

    public void setIdKartu(IntegerFilter idKartu) {
        this.idKartu = idKartu;
    }

    public StringFilter getNama() {
        return nama;
    }

    public void setNama(StringFilter nama) {
        this.nama = nama;
    }

    public IntegerFilter getIdOperator() {
        return idOperator;
    }

    public void setIdOperator(IntegerFilter idOperator) {
        this.idOperator = idOperator;
    }

    public IntegerFilter getCekHlr() {
        return cekHlr;
    }

    public void setCekHlr(IntegerFilter cekHlr) {
        this.cekHlr = cekHlr;
    }

    public StringFilter getKetkartu() {
        return ketkartu;
    }

    public void setKetkartu(StringFilter ketkartu) {
        this.ketkartu = ketkartu;
    }

    @Override
    public String toString() {
        return "KartuCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idKartu != null ? "idKartu=" + idKartu + ", " : "") +
                (nama != null ? "nama=" + nama + ", " : "") +
                (idOperator != null ? "idOperator=" + idOperator + ", " : "") +
                (cekHlr != null ? "cekHlr=" + cekHlr + ", " : "") +
                (ketkartu != null ? "ketkartu=" + ketkartu + ", " : "") +
            "}";
    }

}
