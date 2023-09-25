package dev.noelopez.restdemo1.model;

import dev.noelopez.restdemo1.converter.CustomerStatusConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Customer {

    public enum Status {
        ACTIVATED(1), DEACTIVATED(2), SUSPENDED(3);

        int statusId;

        private Status(int statusId) {
            this.statusId = statusId;
        }

        public int getStatusId() {
            return statusId;
        }
    };

    @Id
    @SequenceGenerator(name = "customer_id_sequence", sequenceName = "customer_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_sequence")
    private Long id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    @OneToOne(mappedBy = "customer", optional = false ,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private CustomerDetails details;
    @Convert(converter = CustomerStatusConverter.class)
    private Status status;
    private Customer(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.dateOfBirth = builder.dateOfBirth;
        this.status = builder.status;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CustomerDetails getDetails() {
        return details;
    }

    public void setDetails(CustomerDetails details) {
        this.details = details;
        details.setCustomer(this);
    }


    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private LocalDate dateOfBirth;
        private Status status;

        private String info;
        private Boolean vip;
        private Builder() { }

        public static Builder newCustomer() {
            // You can easily add (lazy) default parameters
            return new Builder();
        }

        public Builder id(@NotNull Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder dob(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder withDetails(String info, Boolean vip) {
            this.info = info;
            this.vip = vip;
            return this;
        }

        public Customer build() {
            Customer customer = new Customer(this);
            customer.setDetails(new CustomerDetails(info,vip));
            return customer;
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", details=" + details +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(dateOfBirth, customer.dateOfBirth) && Objects.equals(details, customer.details) && status == customer.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, dateOfBirth, details, status);
    }



}
