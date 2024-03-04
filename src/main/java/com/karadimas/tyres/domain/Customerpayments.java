package com.karadimas.tyres.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.karadimas.tyres.domain.enumeration.Paystatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Customerpayments.
 */
@Entity
@Table(name = "customerpayments")
public class Customerpayments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_amount")
    private String totalAmount;

    @Column(name = "remainder")
    private String remainder;

    @Column(name = "down_payment")
    private String downPayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "ispaid")
    private Paystatus ispaid;

    @Column(name = "invoice_date")
    private Instant invoiceDate;

    @Column(name = "due_date")
    private Instant dueDate;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Lob
    @Column(name = "attachments")
    private byte[] attachments;

    @Column(name = "attachments_content_type")
    private String attachmentsContentType;

    @Column(name = "lastupdated")
    private Instant lastupdated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "carts", "customerpayments" }, allowSetters = true)
    private Customers customers;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customerpayments id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public Customerpayments totalAmount(String totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRemainder() {
        return this.remainder;
    }

    public Customerpayments remainder(String remainder) {
        this.setRemainder(remainder);
        return this;
    }

    public void setRemainder(String remainder) {
        this.remainder = remainder;
    }

    public String getDownPayment() {
        return this.downPayment;
    }

    public Customerpayments downPayment(String downPayment) {
        this.setDownPayment(downPayment);
        return this;
    }

    public void setDownPayment(String downPayment) {
        this.downPayment = downPayment;
    }

    public Paystatus getIspaid() {
        return this.ispaid;
    }

    public Customerpayments ispaid(Paystatus ispaid) {
        this.setIspaid(ispaid);
        return this;
    }

    public void setIspaid(Paystatus ispaid) {
        this.ispaid = ispaid;
    }

    public Instant getInvoiceDate() {
        return this.invoiceDate;
    }

    public Customerpayments invoiceDate(Instant invoiceDate) {
        this.setInvoiceDate(invoiceDate);
        return this;
    }

    public void setInvoiceDate(Instant invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Instant getDueDate() {
        return this.dueDate;
    }

    public Customerpayments dueDate(Instant dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public Customerpayments notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte[] getAttachments() {
        return this.attachments;
    }

    public Customerpayments attachments(byte[] attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentsContentType() {
        return this.attachmentsContentType;
    }

    public Customerpayments attachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
        return this;
    }

    public void setAttachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public Instant getLastupdated() {
        return this.lastupdated;
    }

    public Customerpayments lastupdated(Instant lastupdated) {
        this.setLastupdated(lastupdated);
        return this;
    }

    public void setLastupdated(Instant lastupdated) {
        this.lastupdated = lastupdated;
    }

    public Customers getCustomers() {
        return this.customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public Customerpayments customers(Customers customers) {
        this.setCustomers(customers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customerpayments)) {
            return false;
        }
        return id != null && id.equals(((Customerpayments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customerpayments{" +
            "id=" + getId() +
            ", totalAmount='" + getTotalAmount() + "'" +
            ", remainder='" + getRemainder() + "'" +
            ", downPayment='" + getDownPayment() + "'" +
            ", ispaid='" + getIspaid() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", notes='" + getNotes() + "'" +
            ", attachments='" + getAttachments() + "'" +
            ", attachmentsContentType='" + getAttachmentsContentType() + "'" +
            ", lastupdated='" + getLastupdated() + "'" +
            "}";
    }
}
