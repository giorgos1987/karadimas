package com.karadimas.tyres.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.karadimas.tyres.domain.enumeration.Typeofcustomer;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Customers.
 */
@Entity
@Table(name = "customers")
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "car")
    private String car;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Column(name = "phone")
    private String phone;

    @Column(name = "tyres")
    private String tyres;

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

    @Column(name = "companyphone")
    private String companyphone;

    @Column(name = "email")
    private String email;

    @Column(name = "address_line")
    private String addressLine;

    @Column(name = "city_country")
    private String cityCountry;

    @OneToMany(mappedBy = "customers")
    @JsonIgnoreProperties(value = { "jobs", "customers" }, allowSetters = true)
    private Set<Cart> carts = new HashSet<>();

    @OneToMany(mappedBy = "customers")
    @JsonIgnoreProperties(value = { "customers" }, allowSetters = true)
    private Set<Customerpayments> customerpayments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Customers name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCar() {
        return this.car;
    }

    public Customers car(String car) {
        this.setCar(car);
        return this;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getNotes() {
        return this.notes;
    }

    public Customers notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhone() {
        return this.phone;
    }

    public Customers phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTyres() {
        return this.tyres;
    }

    public Customers tyres(String tyres) {
        this.setTyres(tyres);
        return this;
    }

    public void setTyres(String tyres) {
        this.tyres = tyres;
    }

    public String getPlates() {
        return this.plates;
    }

    public Customers plates(String plates) {
        this.setPlates(plates);
        return this;
    }

    public void setPlates(String plates) {
        this.plates = plates;
    }

    public Typeofcustomer getCustomertype() {
        return this.customertype;
    }

    public Customers customertype(Typeofcustomer customertype) {
        this.setCustomertype(customertype);
        return this;
    }

    public void setCustomertype(Typeofcustomer customertype) {
        this.customertype = customertype;
    }

    public Instant getLastseen() {
        return this.lastseen;
    }

    public Customers lastseen(Instant lastseen) {
        this.setLastseen(lastseen);
        return this;
    }

    public void setLastseen(Instant lastseen) {
        this.lastseen = lastseen;
    }

    public Instant getFirstseen() {
        return this.firstseen;
    }

    public Customers firstseen(Instant firstseen) {
        this.setFirstseen(firstseen);
        return this;
    }

    public void setFirstseen(Instant firstseen) {
        this.firstseen = firstseen;
    }

    public String getProselesysis() {
        return this.proselesysis;
    }

    public Customers proselesysis(String proselesysis) {
        this.setProselesysis(proselesysis);
        return this;
    }

    public void setProselesysis(String proselesysis) {
        this.proselesysis = proselesysis;
    }

    public String getMobile() {
        return this.mobile;
    }

    public Customers mobile(String mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyphone() {
        return this.companyphone;
    }

    public Customers companyphone(String companyphone) {
        this.setCompanyphone(companyphone);
        return this;
    }

    public void setCompanyphone(String companyphone) {
        this.companyphone = companyphone;
    }

    public String getEmail() {
        return this.email;
    }

    public Customers email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLine() {
        return this.addressLine;
    }

    public Customers addressLine(String addressLine) {
        this.setAddressLine(addressLine);
        return this;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCityCountry() {
        return this.cityCountry;
    }

    public Customers cityCountry(String cityCountry) {
        this.setCityCountry(cityCountry);
        return this;
    }

    public void setCityCountry(String cityCountry) {
        this.cityCountry = cityCountry;
    }

    public Set<Cart> getCarts() {
        return this.carts;
    }

    public void setCarts(Set<Cart> carts) {
        if (this.carts != null) {
            this.carts.forEach(i -> i.setCustomers(null));
        }
        if (carts != null) {
            carts.forEach(i -> i.setCustomers(this));
        }
        this.carts = carts;
    }

    public Customers carts(Set<Cart> carts) {
        this.setCarts(carts);
        return this;
    }

    public Customers addCart(Cart cart) {
        this.carts.add(cart);
        cart.setCustomers(this);
        return this;
    }

    public Customers removeCart(Cart cart) {
        this.carts.remove(cart);
        cart.setCustomers(null);
        return this;
    }

    public Set<Customerpayments> getCustomerpayments() {
        return this.customerpayments;
    }

    public void setCustomerpayments(Set<Customerpayments> customerpayments) {
        if (this.customerpayments != null) {
            this.customerpayments.forEach(i -> i.setCustomers(null));
        }
        if (customerpayments != null) {
            customerpayments.forEach(i -> i.setCustomers(this));
        }
        this.customerpayments = customerpayments;
    }

    public Customers customerpayments(Set<Customerpayments> customerpayments) {
        this.setCustomerpayments(customerpayments);
        return this;
    }

    public Customers addCustomerpayments(Customerpayments customerpayments) {
        this.customerpayments.add(customerpayments);
        customerpayments.setCustomers(this);
        return this;
    }

    public Customers removeCustomerpayments(Customerpayments customerpayments) {
        this.customerpayments.remove(customerpayments);
        customerpayments.setCustomers(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customers)) {
            return false;
        }
        return id != null && id.equals(((Customers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customers{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", car='" + getCar() + "'" +
            ", notes='" + getNotes() + "'" +
            ", phone='" + getPhone() + "'" +
            ", tyres='" + getTyres() + "'" +
            ", plates='" + getPlates() + "'" +
            ", customertype='" + getCustomertype() + "'" +
            ", lastseen='" + getLastseen() + "'" +
            ", firstseen='" + getFirstseen() + "'" +
            ", proselesysis='" + getProselesysis() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", companyphone='" + getCompanyphone() + "'" +
            ", email='" + getEmail() + "'" +
            ", addressLine='" + getAddressLine() + "'" +
            ", cityCountry='" + getCityCountry() + "'" +
            "}";
    }
}
