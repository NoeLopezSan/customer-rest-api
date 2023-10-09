package dev.noelopez.restdemo1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Table(name = "CUSTOMER_DETAILS")
@Entity(name = "CustomerDetails")
public class CustomerDetails {
    @Id
    @Column(name = "customer_id")
    private Long id;
    @Column
    private Boolean vip;
    @Lob
    @Column
    private String info;
    @Column
    private LocalDate createdOn;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "customer_id" )
    @JsonIgnore
    private Customer customer;
    public CustomerDetails() {
        createdOn = LocalDate.now();
    }
    public CustomerDetails(String info, Boolean vip) {
        this.info = info;
        this.vip = vip;
        createdOn = LocalDate.now();
    }
    public Boolean isVip() {
        return vip;
    }
    public void setVip(Boolean vip) {
        this.vip = vip;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public LocalDate getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDetails that = (CustomerDetails) o;
        return vip == that.vip && Objects.equals(id, that.id) && Objects.equals(info, that.info) && Objects.equals(createdOn, that.createdOn) && Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vip, info, createdOn, customer);
    }

    @Override
    public String toString() {
        return "CustomerDetails{" +
                "id=" + id +
                ", vip=" + vip +
                ", info='" + info + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
