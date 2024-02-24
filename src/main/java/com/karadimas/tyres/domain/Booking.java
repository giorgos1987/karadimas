package com.karadimas.tyres.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "eventdate")
    private Instant eventdate;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "estimatedhours", precision = 21, scale = 2)
    private BigDecimal estimatedhours;

    @Lob
    @Column(name = "note")
    private String note;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Booking id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEventdate() {
        return this.eventdate;
    }

    public Booking eventdate(Instant eventdate) {
        this.setEventdate(eventdate);
        return this;
    }

    public void setEventdate(Instant eventdate) {
        this.eventdate = eventdate;
    }

    public String getMobile() {
        return this.mobile;
    }

    public Booking mobile(String mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBrand() {
        return this.brand;
    }

    public Booking brand(String brand) {
        this.setBrand(brand);
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public Booking model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getEstimatedhours() {
        return this.estimatedhours;
    }

    public Booking estimatedhours(BigDecimal estimatedhours) {
        this.setEstimatedhours(estimatedhours);
        return this;
    }

    public void setEstimatedhours(BigDecimal estimatedhours) {
        this.estimatedhours = estimatedhours;
    }

    public String getNote() {
        return this.note;
    }

    public Booking note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        return id != null && id.equals(((Booking) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", eventdate='" + getEventdate() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", brand='" + getBrand() + "'" +
            ", model='" + getModel() + "'" +
            ", estimatedhours=" + getEstimatedhours() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
