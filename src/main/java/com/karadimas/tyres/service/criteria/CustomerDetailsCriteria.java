package com.karadimas.tyres.service.criteria;

import com.karadimas.tyres.domain.enumeration.Typeofcustomer;
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
 * Criteria class for the {@link com.karadimas.tyres.domain.CustomerDetails} entity. This class is used
 * in {@link com.karadimas.tyres.web.rest.CustomerDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customer-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CustomerDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Typeofcustomer
     */
    public static class TypeofcustomerFilter extends Filter<Typeofcustomer> {

        public TypeofcustomerFilter() {}

        public TypeofcustomerFilter(TypeofcustomerFilter filter) {
            super(filter);
        }

        @Override
        public TypeofcustomerFilter copy() {
            return new TypeofcustomerFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter plates;

    private TypeofcustomerFilter customertype;

    private InstantFilter lastseen;

    private InstantFilter firstseen;

    private StringFilter mobile;

    private StringFilter phone;

    private StringFilter companyphone;

    private StringFilter email;

    private StringFilter addressLine1;

    private StringFilter addressLine2;

    private StringFilter city;

    private StringFilter country;

    private LongFilter cartId;

    private LongFilter customerpaymentsId;

    private Boolean distinct;

    public CustomerDetailsCriteria() {}

    public CustomerDetailsCriteria(CustomerDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.plates = other.plates == null ? null : other.plates.copy();
        this.customertype = other.customertype == null ? null : other.customertype.copy();
        this.lastseen = other.lastseen == null ? null : other.lastseen.copy();
        this.firstseen = other.firstseen == null ? null : other.firstseen.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.companyphone = other.companyphone == null ? null : other.companyphone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.addressLine1 = other.addressLine1 == null ? null : other.addressLine1.copy();
        this.addressLine2 = other.addressLine2 == null ? null : other.addressLine2.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.cartId = other.cartId == null ? null : other.cartId.copy();
        this.customerpaymentsId = other.customerpaymentsId == null ? null : other.customerpaymentsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CustomerDetailsCriteria copy() {
        return new CustomerDetailsCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getPlates() {
        return plates;
    }

    public StringFilter plates() {
        if (plates == null) {
            plates = new StringFilter();
        }
        return plates;
    }

    public void setPlates(StringFilter plates) {
        this.plates = plates;
    }

    public TypeofcustomerFilter getCustomertype() {
        return customertype;
    }

    public TypeofcustomerFilter customertype() {
        if (customertype == null) {
            customertype = new TypeofcustomerFilter();
        }
        return customertype;
    }

    public void setCustomertype(TypeofcustomerFilter customertype) {
        this.customertype = customertype;
    }

    public InstantFilter getLastseen() {
        return lastseen;
    }

    public InstantFilter lastseen() {
        if (lastseen == null) {
            lastseen = new InstantFilter();
        }
        return lastseen;
    }

    public void setLastseen(InstantFilter lastseen) {
        this.lastseen = lastseen;
    }

    public InstantFilter getFirstseen() {
        return firstseen;
    }

    public InstantFilter firstseen() {
        if (firstseen == null) {
            firstseen = new InstantFilter();
        }
        return firstseen;
    }

    public void setFirstseen(InstantFilter firstseen) {
        this.firstseen = firstseen;
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

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getCompanyphone() {
        return companyphone;
    }

    public StringFilter companyphone() {
        if (companyphone == null) {
            companyphone = new StringFilter();
        }
        return companyphone;
    }

    public void setCompanyphone(StringFilter companyphone) {
        this.companyphone = companyphone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getAddressLine1() {
        return addressLine1;
    }

    public StringFilter addressLine1() {
        if (addressLine1 == null) {
            addressLine1 = new StringFilter();
        }
        return addressLine1;
    }

    public void setAddressLine1(StringFilter addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public StringFilter getAddressLine2() {
        return addressLine2;
    }

    public StringFilter addressLine2() {
        if (addressLine2 == null) {
            addressLine2 = new StringFilter();
        }
        return addressLine2;
    }

    public void setAddressLine2(StringFilter addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getCountry() {
        return country;
    }

    public StringFilter country() {
        if (country == null) {
            country = new StringFilter();
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public LongFilter getCartId() {
        return cartId;
    }

    public LongFilter cartId() {
        if (cartId == null) {
            cartId = new LongFilter();
        }
        return cartId;
    }

    public void setCartId(LongFilter cartId) {
        this.cartId = cartId;
    }

    public LongFilter getCustomerpaymentsId() {
        return customerpaymentsId;
    }

    public LongFilter customerpaymentsId() {
        if (customerpaymentsId == null) {
            customerpaymentsId = new LongFilter();
        }
        return customerpaymentsId;
    }

    public void setCustomerpaymentsId(LongFilter customerpaymentsId) {
        this.customerpaymentsId = customerpaymentsId;
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
        final CustomerDetailsCriteria that = (CustomerDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(plates, that.plates) &&
            Objects.equals(customertype, that.customertype) &&
            Objects.equals(lastseen, that.lastseen) &&
            Objects.equals(firstseen, that.firstseen) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(companyphone, that.companyphone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(addressLine1, that.addressLine1) &&
            Objects.equals(addressLine2, that.addressLine2) &&
            Objects.equals(city, that.city) &&
            Objects.equals(country, that.country) &&
            Objects.equals(cartId, that.cartId) &&
            Objects.equals(customerpaymentsId, that.customerpaymentsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            plates,
            customertype,
            lastseen,
            firstseen,
            mobile,
            phone,
            companyphone,
            email,
            addressLine1,
            addressLine2,
            city,
            country,
            cartId,
            customerpaymentsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (plates != null ? "plates=" + plates + ", " : "") +
            (customertype != null ? "customertype=" + customertype + ", " : "") +
            (lastseen != null ? "lastseen=" + lastseen + ", " : "") +
            (firstseen != null ? "firstseen=" + firstseen + ", " : "") +
            (mobile != null ? "mobile=" + mobile + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (companyphone != null ? "companyphone=" + companyphone + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (addressLine1 != null ? "addressLine1=" + addressLine1 + ", " : "") +
            (addressLine2 != null ? "addressLine2=" + addressLine2 + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (cartId != null ? "cartId=" + cartId + ", " : "") +
            (customerpaymentsId != null ? "customerpaymentsId=" + customerpaymentsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
