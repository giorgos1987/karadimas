package com.karadimas.tyres.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Neworders.
 */
@Entity
@Table(name = "neworders")
public class Neworders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "elastika_1")
    private String elastika1;

    @Column(name = "elastika_2")
    private String elastika2;

    @Column(name = "elastika_3")
    private String elastika3;

    @Column(name = "elastika_4")
    private String elastika4;

    @Column(name = "elastika_5")
    private String elastika5;

    @Column(name = "elastika_6")
    private String elastika6;

    @Column(name = "elastika_7")
    private String elastika7;

    @Column(name = "elastika_8")
    private String elastika8;

    @Column(name = "elastika_9")
    private String elastika9;

    @Column(name = "elastika_10")
    private String elastika10;

    @Column(name = "elastika_11")
    private String elastika11;

    @Column(name = "elastika_12")
    private String elastika12;

    @Column(name = "elastika_13")
    private String elastika13;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Neworders id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderDate() {
        return this.orderDate;
    }

    public Neworders orderDate(Instant orderDate) {
        this.setOrderDate(orderDate);
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public String getElastika1() {
        return this.elastika1;
    }

    public Neworders elastika1(String elastika1) {
        this.setElastika1(elastika1);
        return this;
    }

    public void setElastika1(String elastika1) {
        this.elastika1 = elastika1;
    }

    public String getElastika2() {
        return this.elastika2;
    }

    public Neworders elastika2(String elastika2) {
        this.setElastika2(elastika2);
        return this;
    }

    public void setElastika2(String elastika2) {
        this.elastika2 = elastika2;
    }

    public String getElastika3() {
        return this.elastika3;
    }

    public Neworders elastika3(String elastika3) {
        this.setElastika3(elastika3);
        return this;
    }

    public void setElastika3(String elastika3) {
        this.elastika3 = elastika3;
    }

    public String getElastika4() {
        return this.elastika4;
    }

    public Neworders elastika4(String elastika4) {
        this.setElastika4(elastika4);
        return this;
    }

    public void setElastika4(String elastika4) {
        this.elastika4 = elastika4;
    }

    public String getElastika5() {
        return this.elastika5;
    }

    public Neworders elastika5(String elastika5) {
        this.setElastika5(elastika5);
        return this;
    }

    public void setElastika5(String elastika5) {
        this.elastika5 = elastika5;
    }

    public String getElastika6() {
        return this.elastika6;
    }

    public Neworders elastika6(String elastika6) {
        this.setElastika6(elastika6);
        return this;
    }

    public void setElastika6(String elastika6) {
        this.elastika6 = elastika6;
    }

    public String getElastika7() {
        return this.elastika7;
    }

    public Neworders elastika7(String elastika7) {
        this.setElastika7(elastika7);
        return this;
    }

    public void setElastika7(String elastika7) {
        this.elastika7 = elastika7;
    }

    public String getElastika8() {
        return this.elastika8;
    }

    public Neworders elastika8(String elastika8) {
        this.setElastika8(elastika8);
        return this;
    }

    public void setElastika8(String elastika8) {
        this.elastika8 = elastika8;
    }

    public String getElastika9() {
        return this.elastika9;
    }

    public Neworders elastika9(String elastika9) {
        this.setElastika9(elastika9);
        return this;
    }

    public void setElastika9(String elastika9) {
        this.elastika9 = elastika9;
    }

    public String getElastika10() {
        return this.elastika10;
    }

    public Neworders elastika10(String elastika10) {
        this.setElastika10(elastika10);
        return this;
    }

    public void setElastika10(String elastika10) {
        this.elastika10 = elastika10;
    }

    public String getElastika11() {
        return this.elastika11;
    }

    public Neworders elastika11(String elastika11) {
        this.setElastika11(elastika11);
        return this;
    }

    public void setElastika11(String elastika11) {
        this.elastika11 = elastika11;
    }

    public String getElastika12() {
        return this.elastika12;
    }

    public Neworders elastika12(String elastika12) {
        this.setElastika12(elastika12);
        return this;
    }

    public void setElastika12(String elastika12) {
        this.elastika12 = elastika12;
    }

    public String getElastika13() {
        return this.elastika13;
    }

    public Neworders elastika13(String elastika13) {
        this.setElastika13(elastika13);
        return this;
    }

    public void setElastika13(String elastika13) {
        this.elastika13 = elastika13;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Neworders)) {
            return false;
        }
        return id != null && id.equals(((Neworders) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Neworders{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", elastika1='" + getElastika1() + "'" +
            ", elastika2='" + getElastika2() + "'" +
            ", elastika3='" + getElastika3() + "'" +
            ", elastika4='" + getElastika4() + "'" +
            ", elastika5='" + getElastika5() + "'" +
            ", elastika6='" + getElastika6() + "'" +
            ", elastika7='" + getElastika7() + "'" +
            ", elastika8='" + getElastika8() + "'" +
            ", elastika9='" + getElastika9() + "'" +
            ", elastika10='" + getElastika10() + "'" +
            ", elastika11='" + getElastika11() + "'" +
            ", elastika12='" + getElastika12() + "'" +
            ", elastika13='" + getElastika13() + "'" +
            "}";
    }
}
