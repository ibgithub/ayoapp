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





/**
 * Criteria class for the Rebate entity. This class is used in RebateResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /rebates?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RebateCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter idTransaksi;

    private BigDecimalFilter jml;

    private BigDecimalFilter hargaProduk;

    private StringFilter idMember;

    private IntegerFilter level;

    private IntegerFilter bulan;

    private IntegerFilter tahun;

    private IntegerFilter status;

    public RebateCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(LongFilter idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public BigDecimalFilter getJml() {
        return jml;
    }

    public void setJml(BigDecimalFilter jml) {
        this.jml = jml;
    }

    public BigDecimalFilter getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(BigDecimalFilter hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public StringFilter getIdMember() {
        return idMember;
    }

    public void setIdMember(StringFilter idMember) {
        this.idMember = idMember;
    }

    public IntegerFilter getLevel() {
        return level;
    }

    public void setLevel(IntegerFilter level) {
        this.level = level;
    }

    public IntegerFilter getBulan() {
        return bulan;
    }

    public void setBulan(IntegerFilter bulan) {
        this.bulan = bulan;
    }

    public IntegerFilter getTahun() {
        return tahun;
    }

    public void setTahun(IntegerFilter tahun) {
        this.tahun = tahun;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RebateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idTransaksi != null ? "idTransaksi=" + idTransaksi + ", " : "") +
                (jml != null ? "jml=" + jml + ", " : "") +
                (hargaProduk != null ? "hargaProduk=" + hargaProduk + ", " : "") +
                (idMember != null ? "idMember=" + idMember + ", " : "") +
                (level != null ? "level=" + level + ", " : "") +
                (bulan != null ? "bulan=" + bulan + ", " : "") +
                (tahun != null ? "tahun=" + tahun + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
