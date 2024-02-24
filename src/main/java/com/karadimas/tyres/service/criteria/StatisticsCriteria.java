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
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.karadimas.tyres.domain.Statistics} entity. This class is used
 * in {@link com.karadimas.tyres.web.rest.StatisticsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /statistics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class StatisticsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter todaysales;

    private IntegerFilter totalCustomersNumb;

    private BigDecimalFilter custmerTotal;

    private IntegerFilter schedTotalNexWeek;

    private IntegerFilter totalCarts;

    private IntegerFilter totalPending;

    private BigDecimalFilter totalPayments;

    private BigDecimalFilter pendingPayments;

    private IntegerFilter pendingNumberPayments;

    private IntegerFilter totalCars;

    private IntegerFilter totalTrucks;

    private IntegerFilter totalOther1;

    private IntegerFilter totalOther2;

    private StringFilter latestpayments;

    private StringFilter ltstsupplierpaym;

    private StringFilter recentrlycompltd;

    private Boolean distinct;

    public StatisticsCriteria() {}

    public StatisticsCriteria(StatisticsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.todaysales = other.todaysales == null ? null : other.todaysales.copy();
        this.totalCustomersNumb = other.totalCustomersNumb == null ? null : other.totalCustomersNumb.copy();
        this.custmerTotal = other.custmerTotal == null ? null : other.custmerTotal.copy();
        this.schedTotalNexWeek = other.schedTotalNexWeek == null ? null : other.schedTotalNexWeek.copy();
        this.totalCarts = other.totalCarts == null ? null : other.totalCarts.copy();
        this.totalPending = other.totalPending == null ? null : other.totalPending.copy();
        this.totalPayments = other.totalPayments == null ? null : other.totalPayments.copy();
        this.pendingPayments = other.pendingPayments == null ? null : other.pendingPayments.copy();
        this.pendingNumberPayments = other.pendingNumberPayments == null ? null : other.pendingNumberPayments.copy();
        this.totalCars = other.totalCars == null ? null : other.totalCars.copy();
        this.totalTrucks = other.totalTrucks == null ? null : other.totalTrucks.copy();
        this.totalOther1 = other.totalOther1 == null ? null : other.totalOther1.copy();
        this.totalOther2 = other.totalOther2 == null ? null : other.totalOther2.copy();
        this.latestpayments = other.latestpayments == null ? null : other.latestpayments.copy();
        this.ltstsupplierpaym = other.ltstsupplierpaym == null ? null : other.ltstsupplierpaym.copy();
        this.recentrlycompltd = other.recentrlycompltd == null ? null : other.recentrlycompltd.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StatisticsCriteria copy() {
        return new StatisticsCriteria(this);
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

    public BigDecimalFilter getTodaysales() {
        return todaysales;
    }

    public BigDecimalFilter todaysales() {
        if (todaysales == null) {
            todaysales = new BigDecimalFilter();
        }
        return todaysales;
    }

    public void setTodaysales(BigDecimalFilter todaysales) {
        this.todaysales = todaysales;
    }

    public IntegerFilter getTotalCustomersNumb() {
        return totalCustomersNumb;
    }

    public IntegerFilter totalCustomersNumb() {
        if (totalCustomersNumb == null) {
            totalCustomersNumb = new IntegerFilter();
        }
        return totalCustomersNumb;
    }

    public void setTotalCustomersNumb(IntegerFilter totalCustomersNumb) {
        this.totalCustomersNumb = totalCustomersNumb;
    }

    public BigDecimalFilter getCustmerTotal() {
        return custmerTotal;
    }

    public BigDecimalFilter custmerTotal() {
        if (custmerTotal == null) {
            custmerTotal = new BigDecimalFilter();
        }
        return custmerTotal;
    }

    public void setCustmerTotal(BigDecimalFilter custmerTotal) {
        this.custmerTotal = custmerTotal;
    }

    public IntegerFilter getSchedTotalNexWeek() {
        return schedTotalNexWeek;
    }

    public IntegerFilter schedTotalNexWeek() {
        if (schedTotalNexWeek == null) {
            schedTotalNexWeek = new IntegerFilter();
        }
        return schedTotalNexWeek;
    }

    public void setSchedTotalNexWeek(IntegerFilter schedTotalNexWeek) {
        this.schedTotalNexWeek = schedTotalNexWeek;
    }

    public IntegerFilter getTotalCarts() {
        return totalCarts;
    }

    public IntegerFilter totalCarts() {
        if (totalCarts == null) {
            totalCarts = new IntegerFilter();
        }
        return totalCarts;
    }

    public void setTotalCarts(IntegerFilter totalCarts) {
        this.totalCarts = totalCarts;
    }

    public IntegerFilter getTotalPending() {
        return totalPending;
    }

    public IntegerFilter totalPending() {
        if (totalPending == null) {
            totalPending = new IntegerFilter();
        }
        return totalPending;
    }

    public void setTotalPending(IntegerFilter totalPending) {
        this.totalPending = totalPending;
    }

    public BigDecimalFilter getTotalPayments() {
        return totalPayments;
    }

    public BigDecimalFilter totalPayments() {
        if (totalPayments == null) {
            totalPayments = new BigDecimalFilter();
        }
        return totalPayments;
    }

    public void setTotalPayments(BigDecimalFilter totalPayments) {
        this.totalPayments = totalPayments;
    }

    public BigDecimalFilter getPendingPayments() {
        return pendingPayments;
    }

    public BigDecimalFilter pendingPayments() {
        if (pendingPayments == null) {
            pendingPayments = new BigDecimalFilter();
        }
        return pendingPayments;
    }

    public void setPendingPayments(BigDecimalFilter pendingPayments) {
        this.pendingPayments = pendingPayments;
    }

    public IntegerFilter getPendingNumberPayments() {
        return pendingNumberPayments;
    }

    public IntegerFilter pendingNumberPayments() {
        if (pendingNumberPayments == null) {
            pendingNumberPayments = new IntegerFilter();
        }
        return pendingNumberPayments;
    }

    public void setPendingNumberPayments(IntegerFilter pendingNumberPayments) {
        this.pendingNumberPayments = pendingNumberPayments;
    }

    public IntegerFilter getTotalCars() {
        return totalCars;
    }

    public IntegerFilter totalCars() {
        if (totalCars == null) {
            totalCars = new IntegerFilter();
        }
        return totalCars;
    }

    public void setTotalCars(IntegerFilter totalCars) {
        this.totalCars = totalCars;
    }

    public IntegerFilter getTotalTrucks() {
        return totalTrucks;
    }

    public IntegerFilter totalTrucks() {
        if (totalTrucks == null) {
            totalTrucks = new IntegerFilter();
        }
        return totalTrucks;
    }

    public void setTotalTrucks(IntegerFilter totalTrucks) {
        this.totalTrucks = totalTrucks;
    }

    public IntegerFilter getTotalOther1() {
        return totalOther1;
    }

    public IntegerFilter totalOther1() {
        if (totalOther1 == null) {
            totalOther1 = new IntegerFilter();
        }
        return totalOther1;
    }

    public void setTotalOther1(IntegerFilter totalOther1) {
        this.totalOther1 = totalOther1;
    }

    public IntegerFilter getTotalOther2() {
        return totalOther2;
    }

    public IntegerFilter totalOther2() {
        if (totalOther2 == null) {
            totalOther2 = new IntegerFilter();
        }
        return totalOther2;
    }

    public void setTotalOther2(IntegerFilter totalOther2) {
        this.totalOther2 = totalOther2;
    }

    public StringFilter getLatestpayments() {
        return latestpayments;
    }

    public StringFilter latestpayments() {
        if (latestpayments == null) {
            latestpayments = new StringFilter();
        }
        return latestpayments;
    }

    public void setLatestpayments(StringFilter latestpayments) {
        this.latestpayments = latestpayments;
    }

    public StringFilter getLtstsupplierpaym() {
        return ltstsupplierpaym;
    }

    public StringFilter ltstsupplierpaym() {
        if (ltstsupplierpaym == null) {
            ltstsupplierpaym = new StringFilter();
        }
        return ltstsupplierpaym;
    }

    public void setLtstsupplierpaym(StringFilter ltstsupplierpaym) {
        this.ltstsupplierpaym = ltstsupplierpaym;
    }

    public StringFilter getRecentrlycompltd() {
        return recentrlycompltd;
    }

    public StringFilter recentrlycompltd() {
        if (recentrlycompltd == null) {
            recentrlycompltd = new StringFilter();
        }
        return recentrlycompltd;
    }

    public void setRecentrlycompltd(StringFilter recentrlycompltd) {
        this.recentrlycompltd = recentrlycompltd;
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
        final StatisticsCriteria that = (StatisticsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(todaysales, that.todaysales) &&
            Objects.equals(totalCustomersNumb, that.totalCustomersNumb) &&
            Objects.equals(custmerTotal, that.custmerTotal) &&
            Objects.equals(schedTotalNexWeek, that.schedTotalNexWeek) &&
            Objects.equals(totalCarts, that.totalCarts) &&
            Objects.equals(totalPending, that.totalPending) &&
            Objects.equals(totalPayments, that.totalPayments) &&
            Objects.equals(pendingPayments, that.pendingPayments) &&
            Objects.equals(pendingNumberPayments, that.pendingNumberPayments) &&
            Objects.equals(totalCars, that.totalCars) &&
            Objects.equals(totalTrucks, that.totalTrucks) &&
            Objects.equals(totalOther1, that.totalOther1) &&
            Objects.equals(totalOther2, that.totalOther2) &&
            Objects.equals(latestpayments, that.latestpayments) &&
            Objects.equals(ltstsupplierpaym, that.ltstsupplierpaym) &&
            Objects.equals(recentrlycompltd, that.recentrlycompltd) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            todaysales,
            totalCustomersNumb,
            custmerTotal,
            schedTotalNexWeek,
            totalCarts,
            totalPending,
            totalPayments,
            pendingPayments,
            pendingNumberPayments,
            totalCars,
            totalTrucks,
            totalOther1,
            totalOther2,
            latestpayments,
            ltstsupplierpaym,
            recentrlycompltd,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatisticsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (todaysales != null ? "todaysales=" + todaysales + ", " : "") +
            (totalCustomersNumb != null ? "totalCustomersNumb=" + totalCustomersNumb + ", " : "") +
            (custmerTotal != null ? "custmerTotal=" + custmerTotal + ", " : "") +
            (schedTotalNexWeek != null ? "schedTotalNexWeek=" + schedTotalNexWeek + ", " : "") +
            (totalCarts != null ? "totalCarts=" + totalCarts + ", " : "") +
            (totalPending != null ? "totalPending=" + totalPending + ", " : "") +
            (totalPayments != null ? "totalPayments=" + totalPayments + ", " : "") +
            (pendingPayments != null ? "pendingPayments=" + pendingPayments + ", " : "") +
            (pendingNumberPayments != null ? "pendingNumberPayments=" + pendingNumberPayments + ", " : "") +
            (totalCars != null ? "totalCars=" + totalCars + ", " : "") +
            (totalTrucks != null ? "totalTrucks=" + totalTrucks + ", " : "") +
            (totalOther1 != null ? "totalOther1=" + totalOther1 + ", " : "") +
            (totalOther2 != null ? "totalOther2=" + totalOther2 + ", " : "") +
            (latestpayments != null ? "latestpayments=" + latestpayments + ", " : "") +
            (ltstsupplierpaym != null ? "ltstsupplierpaym=" + ltstsupplierpaym + ", " : "") +
            (recentrlycompltd != null ? "recentrlycompltd=" + recentrlycompltd + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
