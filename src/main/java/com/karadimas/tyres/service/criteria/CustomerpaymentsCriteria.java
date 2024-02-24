package com.karadimas.tyres.service.criteria;

import com.karadimas.tyres.domain.enumeration.Paystatus;
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

    private PaystatusFilter ispaid;

    private InstantFilter invoiceDate;

    private InstantFilter dueDate;

    private StringFilter tax;

    private StringFilter shipping;

    private BigDecimalFilter amountDue;

    private InstantFilter lastupdated;

    private LongFilter customerDetailsId;

    private Boolean distinct;

    public CustomerpaymentsCriteria() {}

    public CustomerpaymentsCriteria(CustomerpaymentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ispaid = other.ispaid == null ? null : other.ispaid.copy();
        this.invoiceDate = other.invoiceDate == null ? null : other.invoiceDate.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.tax = other.tax == null ? null : other.tax.copy();
        this.shipping = other.shipping == null ? null : other.shipping.copy();
        this.amountDue = other.amountDue == null ? null : other.amountDue.copy();
        this.lastupdated = other.lastupdated == null ? null : other.lastupdated.copy();
        this.customerDetailsId = other.customerDetailsId == null ? null : other.customerDetailsId.copy();
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

    public StringFilter getTax() {
        return tax;
    }

    public StringFilter tax() {
        if (tax == null) {
            tax = new StringFilter();
        }
        return tax;
    }

    public void setTax(StringFilter tax) {
        this.tax = tax;
    }

    public StringFilter getShipping() {
        return shipping;
    }

    public StringFilter shipping() {
        if (shipping == null) {
            shipping = new StringFilter();
        }
        return shipping;
    }

    public void setShipping(StringFilter shipping) {
        this.shipping = shipping;
    }

    public BigDecimalFilter getAmountDue() {
        return amountDue;
    }

    public BigDecimalFilter amountDue() {
        if (amountDue == null) {
            amountDue = new BigDecimalFilter();
        }
        return amountDue;
    }

    public void setAmountDue(BigDecimalFilter amountDue) {
        this.amountDue = amountDue;
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

    public LongFilter getCustomerDetailsId() {
        return customerDetailsId;
    }

    public LongFilter customerDetailsId() {
        if (customerDetailsId == null) {
            customerDetailsId = new LongFilter();
        }
        return customerDetailsId;
    }

    public void setCustomerDetailsId(LongFilter customerDetailsId) {
        this.customerDetailsId = customerDetailsId;
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
            Objects.equals(ispaid, that.ispaid) &&
            Objects.equals(invoiceDate, that.invoiceDate) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(shipping, that.shipping) &&
            Objects.equals(amountDue, that.amountDue) &&
            Objects.equals(lastupdated, that.lastupdated) &&
            Objects.equals(customerDetailsId, that.customerDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ispaid, invoiceDate, dueDate, tax, shipping, amountDue, lastupdated, customerDetailsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerpaymentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (ispaid != null ? "ispaid=" + ispaid + ", " : "") +
            (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", " : "") +
            (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
            (tax != null ? "tax=" + tax + ", " : "") +
            (shipping != null ? "shipping=" + shipping + ", " : "") +
            (amountDue != null ? "amountDue=" + amountDue + ", " : "") +
            (lastupdated != null ? "lastupdated=" + lastupdated + ", " : "") +
            (customerDetailsId != null ? "customerDetailsId=" + customerDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
