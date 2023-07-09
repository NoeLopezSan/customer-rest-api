package dev.noelopez.restdemo1.converter;

import dev.noelopez.restdemo1.model.Customer;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter
public class CustomerStatusConverter implements AttributeConverter<Customer.Status, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Customer.Status status) {
        return status.getStatusId();
    }

    @Override
    public Customer.Status convertToEntityAttribute(Integer statusId) {
        return Arrays.stream(Customer.Status.values())
                .filter(s -> s.getStatusId() == statusId)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
