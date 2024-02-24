package com.karadimas.tyres.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Supplier.
 */
@Entity
@Table(name = "supplier")
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "company", length = 50)
    private String company;

    @Lob
    @Column(name = "web_page")
    private String webPage;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "email_address", length = 50)
    private String emailAddress;

    @Size(max = 50)
    @Column(name = "job_title", length = 50)
    private String jobTitle;

    @Size(max = 25)
    @Column(name = "business_phone", length = 25)
    private String businessPhone;

    @Size(max = 25)
    @Column(name = "home_phone", length = 25)
    private String homePhone;

    @Size(max = 25)
    @Column(name = "mobile_phone", length = 25)
    private String mobilePhone;

    @Size(max = 25)
    @Column(name = "fax_number", length = 25)
    private String faxNumber;

    @Lob
    @Column(name = "address")
    private String address;

    @Size(max = 50)
    @Column(name = "city", length = 50)
    private String city;

    @Size(max = 50)
    @Column(name = "state_province", length = 50)
    private String stateProvince;

    @Size(max = 15)
    @Column(name = "zip_postal_code", length = 15)
    private String zipPostalCode;

    @Size(max = 50)
    @Column(name = "country_region", length = 50)
    private String countryRegion;

    @Lob
    @Column(name = "attachments")
    private byte[] attachments;

    @Column(name = "attachments_content_type")
    private String attachmentsContentType;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnoreProperties(value = { "supplier" }, allowSetters = true)
    private Set<Supplierpayments> supplierpayments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Supplier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return this.company;
    }

    public Supplier company(String company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWebPage() {
        return this.webPage;
    }

    public Supplier webPage(String webPage) {
        this.setWebPage(webPage);
        return this;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getNotes() {
        return this.notes;
    }

    public Supplier notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Supplier lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Supplier firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public Supplier emailAddress(String emailAddress) {
        this.setEmailAddress(emailAddress);
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public Supplier jobTitle(String jobTitle) {
        this.setJobTitle(jobTitle);
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getBusinessPhone() {
        return this.businessPhone;
    }

    public Supplier businessPhone(String businessPhone) {
        this.setBusinessPhone(businessPhone);
        return this;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getHomePhone() {
        return this.homePhone;
    }

    public Supplier homePhone(String homePhone) {
        this.setHomePhone(homePhone);
        return this;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public Supplier mobilePhone(String mobilePhone) {
        this.setMobilePhone(mobilePhone);
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFaxNumber() {
        return this.faxNumber;
    }

    public Supplier faxNumber(String faxNumber) {
        this.setFaxNumber(faxNumber);
        return this;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public Supplier address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public Supplier city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return this.stateProvince;
    }

    public Supplier stateProvince(String stateProvince) {
        this.setStateProvince(stateProvince);
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getZipPostalCode() {
        return this.zipPostalCode;
    }

    public Supplier zipPostalCode(String zipPostalCode) {
        this.setZipPostalCode(zipPostalCode);
        return this;
    }

    public void setZipPostalCode(String zipPostalCode) {
        this.zipPostalCode = zipPostalCode;
    }

    public String getCountryRegion() {
        return this.countryRegion;
    }

    public Supplier countryRegion(String countryRegion) {
        this.setCountryRegion(countryRegion);
        return this;
    }

    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }

    public byte[] getAttachments() {
        return this.attachments;
    }

    public Supplier attachments(byte[] attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentsContentType() {
        return this.attachmentsContentType;
    }

    public Supplier attachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
        return this;
    }

    public void setAttachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public Set<Supplierpayments> getSupplierpayments() {
        return this.supplierpayments;
    }

    public void setSupplierpayments(Set<Supplierpayments> supplierpayments) {
        if (this.supplierpayments != null) {
            this.supplierpayments.forEach(i -> i.setSupplier(null));
        }
        if (supplierpayments != null) {
            supplierpayments.forEach(i -> i.setSupplier(this));
        }
        this.supplierpayments = supplierpayments;
    }

    public Supplier supplierpayments(Set<Supplierpayments> supplierpayments) {
        this.setSupplierpayments(supplierpayments);
        return this;
    }

    public Supplier addSupplierpayments(Supplierpayments supplierpayments) {
        this.supplierpayments.add(supplierpayments);
        supplierpayments.setSupplier(this);
        return this;
    }

    public Supplier removeSupplierpayments(Supplierpayments supplierpayments) {
        this.supplierpayments.remove(supplierpayments);
        supplierpayments.setSupplier(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Supplier)) {
            return false;
        }
        return id != null && id.equals(((Supplier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Supplier{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", webPage='" + getWebPage() + "'" +
            ", notes='" + getNotes() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", businessPhone='" + getBusinessPhone() + "'" +
            ", homePhone='" + getHomePhone() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", faxNumber='" + getFaxNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", zipPostalCode='" + getZipPostalCode() + "'" +
            ", countryRegion='" + getCountryRegion() + "'" +
            ", attachments='" + getAttachments() + "'" +
            ", attachmentsContentType='" + getAttachmentsContentType() + "'" +
            "}";
    }
}
