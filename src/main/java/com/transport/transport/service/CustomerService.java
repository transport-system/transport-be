package com.transport.transport.service;

import com.transport.transport.model.entity.Customer;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.request.customer.AddCustomerRequest;

public interface CustomerService extends CRUDService<Customer> {
    Customer addCustomer(BookingRequest customer);
}
