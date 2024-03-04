package com.karadimas.tyres.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.karadimas.tyres.domain.enumeration.OrderStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Cart.
 */
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "plate")
    private String plate;

    @Column(name = "placeddate")
    private Instant placeddate;

    @Column(name = "scheduleddate")
    private Instant scheduleddate;

    @Column(name = "deliverydate")
    private Instant deliverydate;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Min(value = 0)
    @Column(name = "numbertyres")
    private Integer numbertyres;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Column(name = "mechanic")
    private String mechanic;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "workorder")
    private Integer workorder;

    @Column(name = "kilometers")
    private String kilometers;

    @Column(name = "payment_method")
    private String paymentMethod;

    @DecimalMin(value = "0")
    @Column(name = "total_price", precision = 21, scale = 2)
    private BigDecimal totalPrice;

    @ManyToMany
    @JoinTable(name = "rel_cart__job", joinColumns = @JoinColumn(name = "cart_id"), inverseJoinColumns = @JoinColumn(name = "job_id"))
    @JsonIgnoreProperties(value = { "carts" }, allowSetters = true)
    private Set<Job> jobs = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "carts", "customerpayments" }, allowSetters = true)
    private Customers customers;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cart id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Cart name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlate() {
        return this.plate;
    }

    public Cart plate(String plate) {
        this.setPlate(plate);
        return this;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Instant getPlaceddate() {
        return this.placeddate;
    }

    public Cart placeddate(Instant placeddate) {
        this.setPlaceddate(placeddate);
        return this;
    }

    public void setPlaceddate(Instant placeddate) {
        this.placeddate = placeddate;
    }

    public Instant getScheduleddate() {
        return this.scheduleddate;
    }

    public Cart scheduleddate(Instant scheduleddate) {
        this.setScheduleddate(scheduleddate);
        return this;
    }

    public void setScheduleddate(Instant scheduleddate) {
        this.scheduleddate = scheduleddate;
    }

    public Instant getDeliverydate() {
        return this.deliverydate;
    }

    public Cart deliverydate(Instant deliverydate) {
        this.setDeliverydate(deliverydate);
        return this;
    }

    public void setDeliverydate(Instant deliverydate) {
        this.deliverydate = deliverydate;
    }

    public String getBrand() {
        return this.brand;
    }

    public Cart brand(String brand) {
        this.setBrand(brand);
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public Cart model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNumbertyres() {
        return this.numbertyres;
    }

    public Cart numbertyres(Integer numbertyres) {
        this.setNumbertyres(numbertyres);
        return this;
    }

    public void setNumbertyres(Integer numbertyres) {
        this.numbertyres = numbertyres;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public Cart status(OrderStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return this.notes;
    }

    public Cart notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMechanic() {
        return this.mechanic;
    }

    public Cart mechanic(String mechanic) {
        this.setMechanic(mechanic);
        return this;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    public String getPhone() {
        return this.phone;
    }

    public Cart phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public Cart address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getWorkorder() {
        return this.workorder;
    }

    public Cart workorder(Integer workorder) {
        this.setWorkorder(workorder);
        return this;
    }

    public void setWorkorder(Integer workorder) {
        this.workorder = workorder;
    }

    public String getKilometers() {
        return this.kilometers;
    }

    public Cart kilometers(String kilometers) {
        this.setKilometers(kilometers);
        return this;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public Cart paymentMethod(String paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public Cart totalPrice(BigDecimal totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<Job> getJobs() {
        return this.jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public Cart jobs(Set<Job> jobs) {
        this.setJobs(jobs);
        return this;
    }

    public Cart addJob(Job job) {
        this.jobs.add(job);
        job.getCarts().add(this);
        return this;
    }

    public Cart removeJob(Job job) {
        this.jobs.remove(job);
        job.getCarts().remove(this);
        return this;
    }

    public Customers getCustomers() {
        return this.customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public Cart customers(Customers customers) {
        this.setCustomers(customers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cart)) {
            return false;
        }
        return id != null && id.equals(((Cart) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cart{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", plate='" + getPlate() + "'" +
            ", placeddate='" + getPlaceddate() + "'" +
            ", scheduleddate='" + getScheduleddate() + "'" +
            ", deliverydate='" + getDeliverydate() + "'" +
            ", brand='" + getBrand() + "'" +
            ", model='" + getModel() + "'" +
            ", numbertyres=" + getNumbertyres() +
            ", status='" + getStatus() + "'" +
            ", notes='" + getNotes() + "'" +
            ", mechanic='" + getMechanic() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            ", workorder=" + getWorkorder() +
            ", kilometers='" + getKilometers() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", totalPrice=" + getTotalPrice() +
            "}";
    }
}
