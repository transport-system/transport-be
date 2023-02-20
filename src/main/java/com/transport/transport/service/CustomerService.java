package com.transport.transport.service;

import com.transport.transport.model.entity.Customer;

public interface CustomerService extends CRUDService<Customer> {
    Customer addCustomer(Customer customer);
}
