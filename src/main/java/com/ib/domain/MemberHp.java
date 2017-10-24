package com.ib.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MemberHp.
 */
@Entity
@Table(name = "member_hp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "memberhp")
public class MemberHp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_member", nullable = false)
    private String idMember;

    @NotNull
    @Column(name = "hp", nullable = false)
    private String hp;

    @Column(name = "typeim")
    private Integer typeim;

    @Column(name = "istrx")
    private Integer istrx;

    @Column(name = "crypt")
    private String crypt;

    @Column(name = "typemsg")
    private Integer typemsg;

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

    public MemberHp idMember(String idMember) {
        this.idMember = idMember;
        return this;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

    public String getHp() {
        return hp;
    }

    public MemberHp hp(String hp) {
        this.hp = hp;
        return this;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public Integer getTypeim() {
        return typeim;
    }

    public MemberHp typeim(Integer typeim) {
        this.typeim = typeim;
        return this;
    }

    public void setTypeim(Integer typeim) {
        this.typeim = typeim;
    }

    public Integer getIstrx() {
        return istrx;
    }

    public MemberHp istrx(Integer istrx) {
        this.istrx = istrx;
        return this;
    }

    public void setIstrx(Integer istrx) {
        this.istrx = istrx;
    }

    public String getCrypt() {
        return crypt;
    }

    public MemberHp crypt(String crypt) {
        this.crypt = crypt;
        return this;
    }

    public void setCrypt(String crypt) {
        this.crypt = crypt;
    }

    public Integer getTypemsg() {
        return typemsg;
    }

    public MemberHp typemsg(Integer typemsg) {
        this.typemsg = typemsg;
        return this;
    }

    public void setTypemsg(Integer typemsg) {
        this.typemsg = typemsg;
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
        MemberHp memberHp = (MemberHp) o;
        if (memberHp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), memberHp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MemberHp{" +
            "id=" + getId() +
            ", idMember='" + getIdMember() + "'" +
            ", hp='" + getHp() + "'" +
            ", typeim='" + getTypeim() + "'" +
            ", istrx='" + getIstrx() + "'" +
            ", crypt='" + getCrypt() + "'" +
            ", typemsg='" + getTypemsg() + "'" +
            "}";
    }
}
