package com.ib.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LevelMember.
 */
@Entity
@Table(name = "level_member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "levelmember")
public class LevelMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kode_level")
    private String kodeLevel;

    @Column(name = "nama")
    private String nama;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKodeLevel() {
        return kodeLevel;
    }

    public LevelMember kodeLevel(String kodeLevel) {
        this.kodeLevel = kodeLevel;
        return this;
    }

    public void setKodeLevel(String kodeLevel) {
        this.kodeLevel = kodeLevel;
    }

    public String getNama() {
        return nama;
    }

    public LevelMember nama(String nama) {
        this.nama = nama;
        return this;
    }

    public void setNama(String nama) {
        this.nama = nama;
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
        LevelMember levelMember = (LevelMember) o;
        if (levelMember.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), levelMember.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LevelMember{" +
            "id=" + getId() +
            ", kodeLevel='" + getKodeLevel() + "'" +
            ", nama='" + getNama() + "'" +
            "}";
    }
}
