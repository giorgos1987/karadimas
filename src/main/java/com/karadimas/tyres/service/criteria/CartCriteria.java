package com.karadimas.tyres.service.criteria;

import com.karadimas.tyres.domain.enumeration.OrderStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.karadimas.tyres.domain.Cart} entity. This class is used
 * in {@link com.karadimas.tyres.web.rest.CartResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /carts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CartCriteria implements Serializable, Criteria {

    /**
     * Class for filtering OrderStatus
     */
    public static class OrderStatusFilter extends Filter<OrderStatus> {

        public OrderStatusFilter() {}

        public OrderStatusFilter(OrderStatusFilter filter) {
            super(filter);
        }

        @Override
        public OrderStatusFilter copy() {
            return new OrderStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter plate;

    private InstantFilter placeddate;

    private InstantFilter scheduleddate;

    private InstantFilter deliverydate;

    private StringFilter brand;

    private StringFilter model;

    private IntegerFilter numbertyres;

    private OrderStatusFilter status;

    private StringFilter mechanic;

    private StringFilter phone;

    private StringFilter address;

    private IntegerFilter workorder;

    private StringFilter kilometers;

    private StringFilter paymentMethod;

    private BigDecimalFilter totalPrice;

    private LongFilter jobId;

    private LongFilter customersId;

    private Boolean distinct;

    public CartCriteria() {}

    public CartCriteria(CartCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.plate = other.plate == null ? null : other.plate.copy();
        this.placeddate = other.placeddate == null ? null : other.placeddate.copy();
        this.scheduleddate = other.scheduleddate == null ? null : other.scheduleddate.copy();
        this.deliverydate = other.deliverydate == null ? null : other.deliverydate.copy();
        this.brand = other.brand == null ? null : other.brand.copy();
        this.model = other.model == null ? null : other.model.copy();
        this.numbertyres = other.numbertyres == null ? null : other.numbertyres.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.mechanic = other.mechanic == null ? null : other.mechanic.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.workorder = other.workorder == null ? null : other.workorder.copy();
        this.kilometers = other.kilometers == null ? null : other.kilometers.copy();
        this.paymentMethod = other.paymentMethod == null ? null : other.paymentMethod.copy();
        this.totalPrice = other.totalPrice == null ? null : other.totalPrice.copy();
        this.jobId = other.jobId == null ? null : other.jobId.copy();
        this.customersId = other.customersId == null ? null : other.customersId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CartCriteria copy() {
        return new CartCriteria(this);
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

    public StringFilter getPlate() {
        return plate;
    }

    public StringFilter plate() {
        if (plate == null) {
            plate = new StringFilter();
        }
        return plate;
    }

    public void setPlate(StringFilter plate) {
        this.plate = plate;
    }

    public InstantFilter getPlaceddate() {
        return placeddate;
    }

    public InstantFilter placeddate() {
        if (placeddate == null) {
            placeddate = new InstantFilter();
        }
        return placeddate;
    }

    public void setPlaceddate(InstantFilter placeddate) {
        this.placeddate = placeddate;
    }

    public InstantFilter getScheduleddate() {
        return scheduleddate;
    }

    public InstantFilter scheduleddate() {
        if (scheduleddate == null) {
            scheduleddate = new InstantFilter();
        }
        return scheduleddate;
    }

    public void setScheduleddate(InstantFilter scheduleddate) {
        this.scheduleddate = scheduleddate;
    }

    public InstantFilter getDeliverydate() {
        return deliverydate;
    }

    public InstantFilter deliverydate() {
        if (deliverydate == null) {
            deliverydate = new InstantFilter();
        }
        return deliverydate;
    }

    public void setDeliverydate(InstantFilter deliverydate) {
        this.deliverydate = deliverydate;
    }

    public StringFilter getBrand() {
        return brand;
    }

    public StringFilter brand() {
        if (brand == null) {
            brand = new StringFilter();
        }
        return brand;
    }

    public void setBrand(StringFilter brand) {
        this.brand = brand;
    }

    public StringFilter getModel() {
        return model;
    }

    public StringFilter model() {
        if (model == null) {
            model = new StringFilter();
        }
        return model;
    }

    public void setModel(StringFilter model) {
        this.model = model;
    }

    public IntegerFilter getNumbertyres() {
        return numbertyres;
    }

    public IntegerFilter numbertyres() {
        if (numbertyres == null) {
            numbertyres = new IntegerFilter();
        }
        return numbertyres;
    }

    public void setNumbertyres(IntegerFilter numbertyres) {
        this.numbertyres = numbertyres;
    }

    public OrderStatusFilter getStatus() {
        return status;
    }

    public OrderStatusFilter status() {
        if (status == null) {
            status = new OrderStatusFilter();
        }
        return status;
    }

    public void setStatus(OrderStatusFilter status) {
        this.status = status;
    }

    public StringFilter getMechanic() {
        return mechanic;
    }

    public StringFilter mechanic() {
        if (mechanic == null) {
            mechanic = new StringFilter();
        }
        return mechanic;
    }

    public void setMechanic(StringFilter mechanic) {
        this.mechanic = mechanic;
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

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public IntegerFilter getWorkorder() {
        return workorder;
    }

    public IntegerFilter workorder() {
        if (workorder == null) {
            workorder = new IntegerFilter();
        }
        return workorder;
    }

    public void setWorkorder(IntegerFilter workorder) {
        this.workorder = workorder;
    }

    public StringFilter getKilometers() {
        return kilometers;
    }

    public StringFilter kilometers() {
        if (kilometers == null) {
            kilometers = new StringFilter();
        }
        return kilometers;
    }

    public void setKilometers(StringFilter kilometers) {
        this.kilometers = kilometers;
    }

    public StringFilter getPaymentMethod() {
        return paymentMethod;
    }

    public StringFilter paymentMethod() {
        if (paymentMethod == null) {
            paymentMethod = new StringFilter();
        }
        return paymentMethod;
    }

    public void setPaymentMethod(StringFilter paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimalFilter getTotalPrice() {
        return totalPrice;
    }

    public BigDecimalFilter totalPrice() {
        if (totalPrice == null) {
            totalPrice = new BigDecimalFilter();
        }
        return totalPrice;
    }

    public void setTotalPrice(BigDecimalFilter totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LongFilter getJobId() {
        return jobId;
    }

    public LongFilter jobId() {
        if (jobId == null) {
            jobId = new LongFilter();
        }
        return jobId;
    }

    public void setJobId(LongFilter jobId) {
        this.jobId = jobId;
    }

    public LongFilter getCustomersId() {
        return customersId;
    }

    public LongFilter customersId() {
        if (customersId == null) {
            customersId = new LongFilter();
        }
        return customersId;
    }

    public void setCustomersId(LongFilter customersId) {
        this.customersId = customersId;
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
        final CartCriteria that = (CartCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(plate, that.plate) &&
            Objects.equals(placeddate, that.placeddate) &&
            Objects.equals(scheduleddate, that.scheduleddate) &&
            Objects.equals(deliverydate, that.deliverydate) &&
            Objects.equals(brand, that.brand) &&
            Objects.equals(model, that.model) &&
            Objects.equals(numbertyres, that.numbertyres) &&
            Objects.equals(status, that.status) &&
            Objects.equals(mechanic, that.mechanic) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(address, that.address) &&
            Objects.equals(workorder, that.workorder) &&
            Objects.equals(kilometers, that.kilometers) &&
            Objects.equals(paymentMethod, that.paymentMethod) &&
            Objects.equals(totalPrice, that.totalPrice) &&
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(customersId, that.customersId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            plate,
            placeddate,
            scheduleddate,
            deliverydate,
            brand,
            model,
            numbertyres,
            status,
            mechanic,
            phone,
            address,
            workorder,
            kilometers,
            paymentMethod,
            totalPrice,
            jobId,
            customersId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CartCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (plate != null ? "plate=" + plate + ", " : "") +
            (placeddate != null ? "placeddate=" + placeddate + ", " : "") +
            (scheduleddate != null ? "scheduleddate=" + scheduleddate + ", " : "") +
            (deliverydate != null ? "deliverydate=" + deliverydate + ", " : "") +
            (brand != null ? "brand=" + brand + ", " : "") +
            (model != null ? "model=" + model + ", " : "") +
            (numbertyres != null ? "numbertyres=" + numbertyres + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (mechanic != null ? "mechanic=" + mechanic + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (workorder != null ? "workorder=" + workorder + ", " : "") +
            (kilometers != null ? "kilometers=" + kilometers + ", " : "") +
            (paymentMethod != null ? "paymentMethod=" + paymentMethod + ", " : "") +
            (totalPrice != null ? "totalPrice=" + totalPrice + ", " : "") +
            (jobId != null ? "jobId=" + jobId + ", " : "") +
            (customersId != null ? "customersId=" + customersId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
