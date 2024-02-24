package com.karadimas.tyres.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.karadimas.tyres.domain.enumeration.Typeofcustomer;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A CustomerDetails.
 */
@Entity
@Table(name = "customer_details")
public class CustomerDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "plates")
    private String plates;

    @Enumerated(EnumType.STRING)
    @Column(name = "customertype")
    private Typeofcustomer customertype;

    @Column(name = "lastseen")
    private Instant lastseen;

    @Column(name = "firstseen")
    private Instant firstseen;

    @Lob
    @Column(name = "proselesysis")
    private String proselesysis;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "phone")
    private String phone;

    @Column(name = "companyphone")
    private String companyphone;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "customerDetails")
    @JsonIgnoreProperties(value = { "jobs", "customerDetails" }, allowSetters = true)
    private Set<Cart> carts = new HashSet<>();

    @OneToMany(mappedBy = "customerDetails")
    @JsonIgnoreProperties(value = { "customerDetails" }, allowSetters = true)
    private Set<Customerpayments> customerpayments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CustomerDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CustomerDetails name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlates() {
        return this.plates;
    }

    public CustomerDetails plates(String plates) {
        this.setPlates(plates);
        return this;
    }

    public void setPlates(String plates) {
        this.plates = plates;
    }

    public Typeofcustomer getCustomertype() {
        return this.customertype;
    }

    public CustomerDetails customertype(Typeofcustomer customertype) {
        this.setCustomertype(customertype);
        return this;
    }

    public void setCustomertype(Typeofcustomer customertype) {
        this.customertype = customertype;
    }

    public Instant getLastseen() {
        return this.lastseen;
    }

    public CustomerDetails lastseen(Instant lastseen) {
        this.setLastseen(lastseen);
        return this;
    }

    public void setLastseen(Instant lastseen) {
        this.lastseen = lastseen;
    }

    public Instant getFirstseen() {
        return this.firstseen;
    }

    public CustomerDetails firstseen(Instant firstseen) {
        this.setFirstseen(firstseen);
        return this;
    }

    public void setFirstseen(Instant firstseen) {
        this.firstseen = firstseen;
    }

    public String getProselesysis() {
        return this.proselesysis;
    }

    public CustomerDetails proselesysis(String proselesysis) {
        this.setProselesysis(proselesysis);
        return this;
    }

    public void setProselesysis(String proselesysis) {
        this.proselesysis = proselesysis;
    }

    public String getMobile() {
        return this.mobile;
    }

    public CustomerDetails mobile(String mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return this.phone;
    }

    public CustomerDetails phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyphone() {
        return this.companyphone;
    }

    public CustomerDetails companyphone(String companyphone) {
        this.setCompanyphone(companyphone);
        return this;
    }

    public void setCompanyphone(String companyphone) {
        this.companyphone = companyphone;
    }

    public String getEmail() {
        return this.email;
    }

    public CustomerDetails email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return this.notes;
    }

    public CustomerDetails notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAddressLine1() {
        return this.addressLine1;
    }

    public CustomerDetails addressLine1(String addressLine1) {
        this.setAddressLine1(addressLine1);
        return this;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return this.addressLine2;
    }

    public CustomerDetails addressLine2(String addressLine2) {
        this.setAddressLine2(addressLine2);
        return this;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return this.city;
    }

    public CustomerDetails city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public CustomerDetails country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Cart> getCarts() {
        return this.carts;
    }

    public void setCarts(Set<Cart> carts) {
        if (this.carts != null) {
            this.carts.forEach(i -> i.setCustomerDetails(null));
        }
        if (carts != null) {
            carts.forEach(i -> i.setCustomerDetails(this));
        }
        this.carts = carts;
    }

    public CustomerDetails carts(Set<Cart> carts) {
        this.setCarts(carts);
        return this;
    }

    public CustomerDetails addCart(Cart cart) {
        this.carts.add(cart);
        cart.setCustomerDetails(this);
        return this;
    }

    public CustomerDetails removeCart(Cart cart) {
        this.carts.remove(cart);
        cart.setCustomerDetails(null);
        return this;
    }

    public Set<Customerpayments> getCustomerpayments() {
        return this.customerpayments;
    }

    public void setCustomerpayments(Set<Customerpayments> customerpayments) {
        if (this.customerpayments != null) {
            this.customerpayments.forEach(i -> i.setCustomerDetails(null));
        }
        if (customerpayments != null) {
            customerpayments.forEach(i -> i.setCustomerDetails(this));
        }
        this.customerpayments = customerpayments;
    }

    public CustomerDetails customerpayments(Set<Customerpayments> customerpayments) {
        this.setCustomerpayments(customerpayments);
        return this;
    }

    public CustomerDetails addCustomerpayments(Customerpayments customerpayments) {
        this.customerpayments.add(customerpayments);
        customerpayments.setCustomerDetails(this);
        return this;
    }

    public CustomerDetails removeCustomerpayments(Customerpayments customerpayments) {
        this.customerpayments.remove(customerpayments);
        customerpayments.setCustomerDetails(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDetails)) {
            return false;
        }
        return id != null && id.equals(((CustomerDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDetails{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", plates='" + getPlates() + "'" +
            ", customertype='" + getCustomertype() + "'" +
            ", lastseen='" + getLastseen() + "'" +
            ", firstseen='" + getFirstseen() + "'" +
            ", proselesysis='" + getProselesysis() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", phone='" + getPhone() + "'" +
            ", companyphone='" + getCompanyphone() + "'" +
            ", email='" + getEmail() + "'" +
            ", notes='" + getNotes() + "'" +
            ", addressLine1='" + getAddressLine1() + "'" +
            ", addressLine2='" + getAddressLine2() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
