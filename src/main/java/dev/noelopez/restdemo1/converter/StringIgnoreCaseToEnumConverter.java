package dev.noelopez.restdemo1.converter;

import dev.noelopez.restdemo1.model.Customer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringIgnoreCaseToEnumConverter implements Converter<String, Customer.Status> {
    @Override
    public Customer.Status convert(String source) {
        try {
            return Customer.Status.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
