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
 * Criteria class for the {@link com.karadimas.tyres.domain.Customers} entity. This class is used
 * in {@link com.karadimas.tyres.web.rest.CustomersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CustomersCriteria implements Serializable, Criteria {

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

    private StringFilter car;

    private StringFilter phone;

    private StringFilter tyres;

    private StringFilter plates;

    private TypeofcustomerFilter customertype;

    private InstantFilter lastseen;

    private InstantFilter firstseen;

    private StringFilter mobile;

    private StringFilter companyphone;

    private StringFilter email;

    private StringFilter addressLine;

    private StringFilter cityCountry;

    private LongFilter cartId;

    private LongFilter customerpaymentsId;

    private Boolean distinct;

    public CustomersCriteria() {}

    public CustomersCriteria(CustomersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.car = other.car == null ? null : other.car.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.tyres = other.tyres == null ? null : other.tyres.copy();
        this.plates = other.plates == null ? null : other.plates.copy();
        this.customertype = other.customertype == null ? null : other.customertype.copy();
        this.lastseen = other.lastseen == null ? null : other.lastseen.copy();
        this.firstseen = other.firstseen == null ? null : other.firstseen.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.companyphone = other.companyphone == null ? null : other.companyphone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.addressLine = other.addressLine == null ? null : other.addressLine.copy();
        this.cityCountry = other.cityCountry == null ? null : other.cityCountry.copy();
        this.cartId = other.cartId == null ? null : other.cartId.copy();
        this.customerpaymentsId = other.customerpaymentsId == null ? null : other.customerpaymentsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CustomersCriteria copy() {
        return new CustomersCriteria(this);
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

    public StringFilter getCar() {
        return car;
    }

    public StringFilter car() {
        if (car == null) {
            car = new StringFilter();
        }
        return car;
    }

    public void setCar(StringFilter car) {
        this.car = car;
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

    public StringFilter getTyres() {
        return tyres;
    }

    public StringFilter tyres() {
        if (tyres == null) {
            tyres = new StringFilter();
        }
        return tyres;
    }

    public void setTyres(StringFilter tyres) {
        this.tyres = tyres;
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

    public StringFilter getAddressLine() {
        return addressLine;
    }

    public StringFilter addressLine() {
        if (addressLine == null) {
            addressLine = new StringFilter();
        }
        return addressLine;
    }

    public void setAddressLine(StringFilter addressLine) {
        this.addressLine = addressLine;
    }

    public StringFilter getCityCountry() {
        return cityCountry;
    }

    public StringFilter cityCountry() {
        if (cityCountry == null) {
            cityCountry = new StringFilter();
        }
        return cityCountry;
    }

    public void setCityCountry(StringFilter cityCountry) {
        this.cityCountry = cityCountry;
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
        final CustomersCriteria that = (CustomersCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(car, that.car) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(tyres, that.tyres) &&
            Objects.equals(plates, that.plates) &&
            Objects.equals(customertype, that.customertype) &&
            Objects.equals(lastseen, that.lastseen) &&
            Objects.equals(firstseen, that.firstseen) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(companyphone, that.companyphone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(addressLine, that.addressLine) &&
            Objects.equals(cityCountry, that.cityCountry) &&
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
            car,
            phone,
            tyres,
            plates,
            customertype,
            lastseen,
            firstseen,
            mobile,
            companyphone,
            email,
            addressLine,
            cityCountry,
            cartId,
            customerpaymentsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (car != null ? "car=" + car + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (tyres != null ? "tyres=" + tyres + ", " : "") +
            (plates != null ? "plates=" + plates + ", " : "") +
            (customertype != null ? "customertype=" + customertype + ", " : "") +
            (lastseen != null ? "lastseen=" + lastseen + ", " : "") +
            (firstseen != null ? "firstseen=" + firstseen + ", " : "") +
            (mobile != null ? "mobile=" + mobile + ", " : "") +
            (companyphone != null ? "companyphone=" + companyphone + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (addressLine != null ? "addressLine=" + addressLine + ", " : "") +
            (cityCountry != null ? "cityCountry=" + cityCountry + ", " : "") +
            (cartId != null ? "cartId=" + cartId + ", " : "") +
            (customerpaymentsId != null ? "customerpaymentsId=" + customerpaymentsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
