package com.karadimas.tyres.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.karadimas.tyres.domain.CompanyProfile} entity. This class is used
 * in {@link com.karadimas.tyres.web.rest.CompanyProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /company-profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CompanyProfileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter companyName;

    private StringFilter address1;

    private StringFilter address2;

    private StringFilter suburb;

    private StringFilter state;

    private StringFilter postcode;

    private StringFilter phone;

    private StringFilter mobile;

    private StringFilter email;

    private StringFilter emailInfo;

    private StringFilter language;

    private Boolean distinct;

    public CompanyProfileCriteria() {}

    public CompanyProfileCriteria(CompanyProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.companyName = other.companyName == null ? null : other.companyName.copy();
        this.address1 = other.address1 == null ? null : other.address1.copy();
        this.address2 = other.address2 == null ? null : other.address2.copy();
        this.suburb = other.suburb == null ? null : other.suburb.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.postcode = other.postcode == null ? null : other.postcode.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.emailInfo = other.emailInfo == null ? null : other.emailInfo.copy();
        this.language = other.language == null ? null : other.language.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CompanyProfileCriteria copy() {
        return new CompanyProfileCriteria(this);
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

    public StringFilter getCompanyName() {
        return companyName;
    }

    public StringFilter companyName() {
        if (companyName == null) {
            companyName = new StringFilter();
        }
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public StringFilter getAddress1() {
        return address1;
    }

    public StringFilter address1() {
        if (address1 == null) {
            address1 = new StringFilter();
        }
        return address1;
    }

    public void setAddress1(StringFilter address1) {
        this.address1 = address1;
    }

    public StringFilter getAddress2() {
        return address2;
    }

    public StringFilter address2() {
        if (address2 == null) {
            address2 = new StringFilter();
        }
        return address2;
    }

    public void setAddress2(StringFilter address2) {
        this.address2 = address2;
    }

    public StringFilter getSuburb() {
        return suburb;
    }

    public StringFilter suburb() {
        if (suburb == null) {
            suburb = new StringFilter();
        }
        return suburb;
    }

    public void setSuburb(StringFilter suburb) {
        this.suburb = suburb;
    }

    public StringFilter getState() {
        return state;
    }

    public StringFilter state() {
        if (state == null) {
            state = new StringFilter();
        }
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getPostcode() {
        return postcode;
    }

    public StringFilter postcode() {
        if (postcode == null) {
            postcode = new StringFilter();
        }
        return postcode;
    }

    public void setPostcode(StringFilter postcode) {
        this.postcode = postcode;
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

    public StringFilter getEmailInfo() {
        return emailInfo;
    }

    public StringFilter emailInfo() {
        if (emailInfo == null) {
            emailInfo = new StringFilter();
        }
        return emailInfo;
    }

    public void setEmailInfo(StringFilter emailInfo) {
        this.emailInfo = emailInfo;
    }

    public StringFilter getLanguage() {
        return language;
    }

    public StringFilter language() {
        if (language == null) {
            language = new StringFilter();
        }
        return language;
    }

    public void setLanguage(StringFilter language) {
        this.language = language;
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
        final CompanyProfileCriteria that = (CompanyProfileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(address1, that.address1) &&
            Objects.equals(address2, that.address2) &&
            Objects.equals(suburb, that.suburb) &&
            Objects.equals(state, that.state) &&
            Objects.equals(postcode, that.postcode) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(email, that.email) &&
            Objects.equals(emailInfo, that.emailInfo) &&
            Objects.equals(language, that.language) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            companyName,
            address1,
            address2,
            suburb,
            state,
            postcode,
            phone,
            mobile,
            email,
            emailInfo,
            language,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyProfileCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (companyName != null ? "companyName=" + companyName + ", " : "") +
            (address1 != null ? "address1=" + address1 + ", " : "") +
            (address2 != null ? "address2=" + address2 + ", " : "") +
            (suburb != null ? "suburb=" + suburb + ", " : "") +
            (state != null ? "state=" + state + ", " : "") +
            (postcode != null ? "postcode=" + postcode + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (mobile != null ? "mobile=" + mobile + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (emailInfo != null ? "emailInfo=" + emailInfo + ", " : "") +
            (language != null ? "language=" + language + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
