package com.karadimas.tyres.service.criteria;

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
 * Criteria class for the {@link com.karadimas.tyres.domain.Neworders} entity. This class is used
 * in {@link com.karadimas.tyres.web.rest.NewordersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /neworders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class NewordersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter orderDate;

    private StringFilter elastika1;

    private StringFilter elastika2;

    private StringFilter elastika3;

    private StringFilter elastika4;

    private StringFilter elastika5;

    private StringFilter elastika6;

    private StringFilter elastika7;

    private StringFilter elastika8;

    private StringFilter elastika9;

    private StringFilter elastika10;

    private StringFilter elastika11;

    private StringFilter elastika12;

    private StringFilter elastika13;

    private Boolean distinct;

    public NewordersCriteria() {}

    public NewordersCriteria(NewordersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderDate = other.orderDate == null ? null : other.orderDate.copy();
        this.elastika1 = other.elastika1 == null ? null : other.elastika1.copy();
        this.elastika2 = other.elastika2 == null ? null : other.elastika2.copy();
        this.elastika3 = other.elastika3 == null ? null : other.elastika3.copy();
        this.elastika4 = other.elastika4 == null ? null : other.elastika4.copy();
        this.elastika5 = other.elastika5 == null ? null : other.elastika5.copy();
        this.elastika6 = other.elastika6 == null ? null : other.elastika6.copy();
        this.elastika7 = other.elastika7 == null ? null : other.elastika7.copy();
        this.elastika8 = other.elastika8 == null ? null : other.elastika8.copy();
        this.elastika9 = other.elastika9 == null ? null : other.elastika9.copy();
        this.elastika10 = other.elastika10 == null ? null : other.elastika10.copy();
        this.elastika11 = other.elastika11 == null ? null : other.elastika11.copy();
        this.elastika12 = other.elastika12 == null ? null : other.elastika12.copy();
        this.elastika13 = other.elastika13 == null ? null : other.elastika13.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NewordersCriteria copy() {
        return new NewordersCriteria(this);
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

    public InstantFilter getOrderDate() {
        return orderDate;
    }

    public InstantFilter orderDate() {
        if (orderDate == null) {
            orderDate = new InstantFilter();
        }
        return orderDate;
    }

    public void setOrderDate(InstantFilter orderDate) {
        this.orderDate = orderDate;
    }

    public StringFilter getElastika1() {
        return elastika1;
    }

    public StringFilter elastika1() {
        if (elastika1 == null) {
            elastika1 = new StringFilter();
        }
        return elastika1;
    }

    public void setElastika1(StringFilter elastika1) {
        this.elastika1 = elastika1;
    }

    public StringFilter getElastika2() {
        return elastika2;
    }

    public StringFilter elastika2() {
        if (elastika2 == null) {
            elastika2 = new StringFilter();
        }
        return elastika2;
    }

    public void setElastika2(StringFilter elastika2) {
        this.elastika2 = elastika2;
    }

    public StringFilter getElastika3() {
        return elastika3;
    }

    public StringFilter elastika3() {
        if (elastika3 == null) {
            elastika3 = new StringFilter();
        }
        return elastika3;
    }

    public void setElastika3(StringFilter elastika3) {
        this.elastika3 = elastika3;
    }

    public StringFilter getElastika4() {
        return elastika4;
    }

    public StringFilter elastika4() {
        if (elastika4 == null) {
            elastika4 = new StringFilter();
        }
        return elastika4;
    }

    public void setElastika4(StringFilter elastika4) {
        this.elastika4 = elastika4;
    }

    public StringFilter getElastika5() {
        return elastika5;
    }

    public StringFilter elastika5() {
        if (elastika5 == null) {
            elastika5 = new StringFilter();
        }
        return elastika5;
    }

    public void setElastika5(StringFilter elastika5) {
        this.elastika5 = elastika5;
    }

    public StringFilter getElastika6() {
        return elastika6;
    }

    public StringFilter elastika6() {
        if (elastika6 == null) {
            elastika6 = new StringFilter();
        }
        return elastika6;
    }

    public void setElastika6(StringFilter elastika6) {
        this.elastika6 = elastika6;
    }

    public StringFilter getElastika7() {
        return elastika7;
    }

    public StringFilter elastika7() {
        if (elastika7 == null) {
            elastika7 = new StringFilter();
        }
        return elastika7;
    }

    public void setElastika7(StringFilter elastika7) {
        this.elastika7 = elastika7;
    }

    public StringFilter getElastika8() {
        return elastika8;
    }

    public StringFilter elastika8() {
        if (elastika8 == null) {
            elastika8 = new StringFilter();
        }
        return elastika8;
    }

    public void setElastika8(StringFilter elastika8) {
        this.elastika8 = elastika8;
    }

    public StringFilter getElastika9() {
        return elastika9;
    }

    public StringFilter elastika9() {
        if (elastika9 == null) {
            elastika9 = new StringFilter();
        }
        return elastika9;
    }

    public void setElastika9(StringFilter elastika9) {
        this.elastika9 = elastika9;
    }

    public StringFilter getElastika10() {
        return elastika10;
    }

    public StringFilter elastika10() {
        if (elastika10 == null) {
            elastika10 = new StringFilter();
        }
        return elastika10;
    }

    public void setElastika10(StringFilter elastika10) {
        this.elastika10 = elastika10;
    }

    public StringFilter getElastika11() {
        return elastika11;
    }

    public StringFilter elastika11() {
        if (elastika11 == null) {
            elastika11 = new StringFilter();
        }
        return elastika11;
    }

    public void setElastika11(StringFilter elastika11) {
        this.elastika11 = elastika11;
    }

    public StringFilter getElastika12() {
        return elastika12;
    }

    public StringFilter elastika12() {
        if (elastika12 == null) {
            elastika12 = new StringFilter();
        }
        return elastika12;
    }

    public void setElastika12(StringFilter elastika12) {
        this.elastika12 = elastika12;
    }

    public StringFilter getElastika13() {
        return elastika13;
    }

    public StringFilter elastika13() {
        if (elastika13 == null) {
            elastika13 = new StringFilter();
        }
        return elastika13;
    }

    public void setElastika13(StringFilter elastika13) {
        this.elastika13 = elastika13;
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
        final NewordersCriteria that = (NewordersCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(orderDate, that.orderDate) &&
            Objects.equals(elastika1, that.elastika1) &&
            Objects.equals(elastika2, that.elastika2) &&
            Objects.equals(elastika3, that.elastika3) &&
            Objects.equals(elastika4, that.elastika4) &&
            Objects.equals(elastika5, that.elastika5) &&
            Objects.equals(elastika6, that.elastika6) &&
            Objects.equals(elastika7, that.elastika7) &&
            Objects.equals(elastika8, that.elastika8) &&
            Objects.equals(elastika9, that.elastika9) &&
            Objects.equals(elastika10, that.elastika10) &&
            Objects.equals(elastika11, that.elastika11) &&
            Objects.equals(elastika12, that.elastika12) &&
            Objects.equals(elastika13, that.elastika13) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            orderDate,
            elastika1,
            elastika2,
            elastika3,
            elastika4,
            elastika5,
            elastika6,
            elastika7,
            elastika8,
            elastika9,
            elastika10,
            elastika11,
            elastika12,
            elastika13,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NewordersCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (orderDate != null ? "orderDate=" + orderDate + ", " : "") +
            (elastika1 != null ? "elastika1=" + elastika1 + ", " : "") +
            (elastika2 != null ? "elastika2=" + elastika2 + ", " : "") +
            (elastika3 != null ? "elastika3=" + elastika3 + ", " : "") +
            (elastika4 != null ? "elastika4=" + elastika4 + ", " : "") +
            (elastika5 != null ? "elastika5=" + elastika5 + ", " : "") +
            (elastika6 != null ? "elastika6=" + elastika6 + ", " : "") +
            (elastika7 != null ? "elastika7=" + elastika7 + ", " : "") +
            (elastika8 != null ? "elastika8=" + elastika8 + ", " : "") +
            (elastika9 != null ? "elastika9=" + elastika9 + ", " : "") +
            (elastika10 != null ? "elastika10=" + elastika10 + ", " : "") +
            (elastika11 != null ? "elastika11=" + elastika11 + ", " : "") +
            (elastika12 != null ? "elastika12=" + elastika12 + ", " : "") +
            (elastika13 != null ? "elastika13=" + elastika13 + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
