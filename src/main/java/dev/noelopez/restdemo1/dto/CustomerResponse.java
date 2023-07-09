package dev.noelopez.restdemo1.dto;

import dev.noelopez.restdemo1.model.Customer;

public record CustomerResponse(long id, Customer.Status status, CustomerPersonInfo personInfo, CustomerDetailsInfo detailsInfo) {

}

