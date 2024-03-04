package com.karadimas.tyres.service.criteria;

import com.karadimas.tyres.domain.enumeration.Paystatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.karadimas.tyres.domain.Customerpayments} entity. This class is used
 * in {@link com.karadimas.tyres.web.rest.CustomerpaymentsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customerpayments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CustomerpaymentsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Paystatus
     */
    public static class PaystatusFilter extends Filter<Paystatus> {

        public PaystatusFilter() {}

        public PaystatusFilter(PaystatusFilter filter) {
            super(filter);
        }

        @Override
        public PaystatusFilter copy() {
            return new PaystatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter totalAmount;

    private StringFilter remainder;

    private StringFilter downPayment;

    private PaystatusFilter ispaid;

    private InstantFilter invoiceDate;

    private InstantFilter dueDate;

    private InstantFilter lastupdated;

    private LongFilter customersId;

    private Boolean distinct;

    public CustomerpaymentsCriteria() {}

    public CustomerpaymentsCriteria(CustomerpaymentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.totalAmount = other.totalAmount == null ? null : other.totalAmount.copy();
        this.remainder = other.remainder == null ? null : other.remainder.copy();
        this.downPayment = other.downPayment == null ? null : other.downPayment.copy();
        this.ispaid = other.ispaid == null ? null : other.ispaid.copy();
        this.invoiceDate = other.invoiceDate == null ? null : other.invoiceDate.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.lastupdated = other.lastupdated == null ? null : other.lastupdated.copy();
        this.customersId = other.customersId == null ? null : other.customersId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CustomerpaymentsCriteria copy() {
        return new CustomerpaymentsCriteria(this);
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

    public StringFilter getTotalAmount() {
        return totalAmount;
    }

    public StringFilter totalAmount() {
        if (totalAmount == null) {
            totalAmount = new StringFilter();
        }
        return totalAmount;
    }

    public void setTotalAmount(StringFilter totalAmount) {
        this.totalAmount = totalAmount;
    }

    public StringFilter getRemainder() {
        return remainder;
    }

    public StringFilter remainder() {
        if (remainder == null) {
            remainder = new StringFilter();
        }
        return remainder;
    }

    public void setRemainder(StringFilter remainder) {
        this.remainder = remainder;
    }

    public StringFilter getDownPayment() {
        return downPayment;
    }

    public StringFilter downPayment() {
        if (downPayment == null) {
            downPayment = new StringFilter();
        }
        return downPayment;
    }

    public void setDownPayment(StringFilter downPayment) {
        this.downPayment = downPayment;
    }

    public PaystatusFilter getIspaid() {
        return ispaid;
    }

    public PaystatusFilter ispaid() {
        if (ispaid == null) {
            ispaid = new PaystatusFilter();
        }
        return ispaid;
    }

    public void setIspaid(PaystatusFilter ispaid) {
        this.ispaid = ispaid;
    }

    public InstantFilter getInvoiceDate() {
        return invoiceDate;
    }

    public InstantFilter invoiceDate() {
        if (invoiceDate == null) {
            invoiceDate = new InstantFilter();
        }
        return invoiceDate;
    }

    public void setInvoiceDate(InstantFilter invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public InstantFilter getDueDate() {
        return dueDate;
    }

    public InstantFilter dueDate() {
        if (dueDate == null) {
            dueDate = new InstantFilter();
        }
        return dueDate;
    }

    public void setDueDate(InstantFilter dueDate) {
        this.dueDate = dueDate;
    }

    public InstantFilter getLastupdated() {
        return lastupdated;
    }

    public InstantFilter lastupdated() {
        if (lastupdated == null) {
            lastupdated = new InstantFilter();
        }
        return lastupdated;
    }

    public void setLastupdated(InstantFilter lastupdated) {
        this.lastupdated = lastupdated;
    }

    public LongFilter getCustomersId() {
        return customersId;
    }

    public LongFilter customersId() {
        if (customersId == null) {
            customersId = new LongFilter();
        }
        return customersId;
    }

    public void setCustomersId(LongFilter customersId) {
        this.customersId = customersId;
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
        final CustomerpaymentsCriteria that = (CustomerpaymentsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(remainder, that.remainder) &&
            Objects.equals(downPayment, that.downPayment) &&
            Objects.equals(ispaid, that.ispaid) &&
            Objects.equals(invoiceDate, that.invoiceDate) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(lastupdated, that.lastupdated) &&
            Objects.equals(customersId, that.customersId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalAmount, remainder, downPayment, ispaid, invoiceDate, dueDate, lastupdated, customersId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerpaymentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
            (remainder != null ? "remainder=" + remainder + ", " : "") +
            (downPayment != null ? "downPayment=" + downPayment + ", " : "") +
            (ispaid != null ? "ispaid=" + ispaid + ", " : "") +
            (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", " : "") +
            (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
            (lastupdated != null ? "lastupdated=" + lastupdated + ", " : "") +
            (customersId != null ? "customersId=" + customersId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
