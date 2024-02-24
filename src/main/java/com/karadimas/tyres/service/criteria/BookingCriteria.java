package com.karadimas.tyres.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.karadimas.tyres.domain.Booking} entity. This class is used
 * in {@link com.karadimas.tyres.web.rest.BookingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bookings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class BookingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter eventdate;

    private StringFilter mobile;

    private StringFilter brand;

    private StringFilter model;

    private BigDecimalFilter estimatedhours;

    private Boolean distinct;

    public BookingCriteria() {}

    public BookingCriteria(BookingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.eventdate = other.eventdate == null ? null : other.eventdate.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.brand = other.brand == null ? null : other.brand.copy();
        this.model = other.model == null ? null : other.model.copy();
        this.estimatedhours = other.estimatedhours == null ? null : other.estimatedhours.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BookingCriteria copy() {
        return new BookingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getEventdate() {
        return eventdate;
    }

    public InstantFilter eventdate() {
        if (eventdate == null) {
            eventdate = new InstantFilter();
        }
        return eventdate;
    }

    public void setEventdate(InstantFilter eventdate) {
        this.eventdate = eventdate;
    }

    public StringFilter getMobile() {
        return mobile;
    }

    public StringFilter mobile() {
        if (mobile == null) {
            mobile = new StringFilter();
        }
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public StringFilter getBrand() {
        return brand;
    }

    public StringFilter brand() {
        if (brand == null) {
            brand = new StringFilter();
        }
        return brand;
    }

    public void setBrand(StringFilter brand) {
        this.brand = brand;
    }

    public StringFilter getModel() {
        return model;
    }

    public StringFilter model() {
        if (model == null) {
            model = new StringFilter();
        }
        return model;
    }

    public void setModel(StringFilter model) {
        this.model = model;
    }

    public BigDecimalFilter getEstimatedhours() {
        return estimatedhours;
    }

    public BigDecimalFilter estimatedhours() {
        if (estimatedhours == null) {
            estimatedhours = new BigDecimalFilter();
        }
        return estimatedhours;
    }

    public void setEstimatedhours(BigDecimalFilter estimatedhours) {
        this.estimatedhours = estimatedhours;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookingCriteria that = (BookingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(eventdate, that.eventdate) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(brand, that.brand) &&
            Objects.equals(model, that.model) &&
            Objects.equals(estimatedhours, that.estimatedhours) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventdate, mobile, brand, model, estimatedhours, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (eventdate != null ? "eventdate=" + eventdate + ", " : "") +
            (mobile != null ? "mobile=" + mobile + ", " : "") +
            (brand != null ? "brand=" + brand + ", " : "") +
            (model != null ? "model=" + model + ", " : "") +
            (estimatedhours != null ? "estimatedhours=" + estimatedhours + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
