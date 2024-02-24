package com.karadimas.tyres.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Statistics.
 */
@Entity
@Table(name = "statistics")
public class Statistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "todaysales", precision = 21, scale = 2)
    private BigDecimal todaysales;

    @Min(value = 0)
    @Column(name = "total_customers_numb")
    private Integer totalCustomersNumb;

    @Column(name = "custmer_total", precision = 21, scale = 2)
    private BigDecimal custmerTotal;

    @Column(name = "sched_total_nex_week")
    private Integer schedTotalNexWeek;

    @Column(name = "total_carts")
    private Integer totalCarts;

    @Column(name = "total_pending")
    private Integer totalPending;

    @Column(name = "total_payments", precision = 21, scale = 2)
    private BigDecimal totalPayments;

    @Column(name = "pending_payments", precision = 21, scale = 2)
    private BigDecimal pendingPayments;

    @Column(name = "pending_number_payments")
    private Integer pendingNumberPayments;

    @Column(name = "total_cars")
    private Integer totalCars;

    @Column(name = "total_trucks")
    private Integer totalTrucks;

    @Column(name = "total_other_1")
    private Integer totalOther1;

    @Column(name = "total_other_2")
    private Integer totalOther2;

    @Column(name = "latestpayments")
    private String latestpayments;

    @Column(name = "ltstsupplierpaym")
    private String ltstsupplierpaym;

    @Column(name = "recentrlycompltd")
    private String recentrlycompltd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Statistics id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTodaysales() {
        return this.todaysales;
    }

    public Statistics todaysales(BigDecimal todaysales) {
        this.setTodaysales(todaysales);
        return this;
    }

    public void setTodaysales(BigDecimal todaysales) {
        this.todaysales = todaysales;
    }

    public Integer getTotalCustomersNumb() {
        return this.totalCustomersNumb;
    }

    public Statistics totalCustomersNumb(Integer totalCustomersNumb) {
        this.setTotalCustomersNumb(totalCustomersNumb);
        return this;
    }

    public void setTotalCustomersNumb(Integer totalCustomersNumb) {
        this.totalCustomersNumb = totalCustomersNumb;
    }

    public BigDecimal getCustmerTotal() {
        return this.custmerTotal;
    }

    public Statistics custmerTotal(BigDecimal custmerTotal) {
        this.setCustmerTotal(custmerTotal);
        return this;
    }

    public void setCustmerTotal(BigDecimal custmerTotal) {
        this.custmerTotal = custmerTotal;
    }

    public Integer getSchedTotalNexWeek() {
        return this.schedTotalNexWeek;
    }

    public Statistics schedTotalNexWeek(Integer schedTotalNexWeek) {
        this.setSchedTotalNexWeek(schedTotalNexWeek);
        return this;
    }

    public void setSchedTotalNexWeek(Integer schedTotalNexWeek) {
        this.schedTotalNexWeek = schedTotalNexWeek;
    }

    public Integer getTotalCarts() {
        return this.totalCarts;
    }

    public Statistics totalCarts(Integer totalCarts) {
        this.setTotalCarts(totalCarts);
        return this;
    }

    public void setTotalCarts(Integer totalCarts) {
        this.totalCarts = totalCarts;
    }

    public Integer getTotalPending() {
        return this.totalPending;
    }

    public Statistics totalPending(Integer totalPending) {
        this.setTotalPending(totalPending);
        return this;
    }

    public void setTotalPending(Integer totalPending) {
        this.totalPending = totalPending;
    }

    public BigDecimal getTotalPayments() {
        return this.totalPayments;
    }

    public Statistics totalPayments(BigDecimal totalPayments) {
        this.setTotalPayments(totalPayments);
        return this;
    }

    public void setTotalPayments(BigDecimal totalPayments) {
        this.totalPayments = totalPayments;
    }

    public BigDecimal getPendingPayments() {
        return this.pendingPayments;
    }

    public Statistics pendingPayments(BigDecimal pendingPayments) {
        this.setPendingPayments(pendingPayments);
        return this;
    }

    public void setPendingPayments(BigDecimal pendingPayments) {
        this.pendingPayments = pendingPayments;
    }

    public Integer getPendingNumberPayments() {
        return this.pendingNumberPayments;
    }

    public Statistics pendingNumberPayments(Integer pendingNumberPayments) {
        this.setPendingNumberPayments(pendingNumberPayments);
        return this;
    }

    public void setPendingNumberPayments(Integer pendingNumberPayments) {
        this.pendingNumberPayments = pendingNumberPayments;
    }

    public Integer getTotalCars() {
        return this.totalCars;
    }

    public Statistics totalCars(Integer totalCars) {
        this.setTotalCars(totalCars);
        return this;
    }

    public void setTotalCars(Integer totalCars) {
        this.totalCars = totalCars;
    }

    public Integer getTotalTrucks() {
        return this.totalTrucks;
    }

    public Statistics totalTrucks(Integer totalTrucks) {
        this.setTotalTrucks(totalTrucks);
        return this;
    }

    public void setTotalTrucks(Integer totalTrucks) {
        this.totalTrucks = totalTrucks;
    }

    public Integer getTotalOther1() {
        return this.totalOther1;
    }

    public Statistics totalOther1(Integer totalOther1) {
        this.setTotalOther1(totalOther1);
        return this;
    }

    public void setTotalOther1(Integer totalOther1) {
        this.totalOther1 = totalOther1;
    }

    public Integer getTotalOther2() {
        return this.totalOther2;
    }

    public Statistics totalOther2(Integer totalOther2) {
        this.setTotalOther2(totalOther2);
        return this;
    }

    public void setTotalOther2(Integer totalOther2) {
        this.totalOther2 = totalOther2;
    }

    public String getLatestpayments() {
        return this.latestpayments;
    }

    public Statistics latestpayments(String latestpayments) {
        this.setLatestpayments(latestpayments);
        return this;
    }

    public void setLatestpayments(String latestpayments) {
        this.latestpayments = latestpayments;
    }

    public String getLtstsupplierpaym() {
        return this.ltstsupplierpaym;
    }

    public Statistics ltstsupplierpaym(String ltstsupplierpaym) {
        this.setLtstsupplierpaym(ltstsupplierpaym);
        return this;
    }

    public void setLtstsupplierpaym(String ltstsupplierpaym) {
        this.ltstsupplierpaym = ltstsupplierpaym;
    }

    public String getRecentrlycompltd() {
        return this.recentrlycompltd;
    }

    public Statistics recentrlycompltd(String recentrlycompltd) {
        this.setRecentrlycompltd(recentrlycompltd);
        return this;
    }

    public void setRecentrlycompltd(String recentrlycompltd) {
        this.recentrlycompltd = recentrlycompltd;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Statistics)) {
            return false;
        }
        return id != null && id.equals(((Statistics) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Statistics{" +
            "id=" + getId() +
            ", todaysales=" + getTodaysales() +
            ", totalCustomersNumb=" + getTotalCustomersNumb() +
            ", custmerTotal=" + getCustmerTotal() +
            ", schedTotalNexWeek=" + getSchedTotalNexWeek() +
            ", totalCarts=" + getTotalCarts() +
            ", totalPending=" + getTotalPending() +
            ", totalPayments=" + getTotalPayments() +
            ", pendingPayments=" + getPendingPayments() +
            ", pendingNumberPayments=" + getPendingNumberPayments() +
            ", totalCars=" + getTotalCars() +
            ", totalTrucks=" + getTotalTrucks() +
            ", totalOther1=" + getTotalOther1() +
            ", totalOther2=" + getTotalOther2() +
            ", latestpayments='" + getLatestpayments() + "'" +
            ", ltstsupplierpaym='" + getLtstsupplierpaym() + "'" +
            ", recentrlycompltd='" + getRecentrlycompltd() + "'" +
            "}";
    }
}
