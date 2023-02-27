package com.transport.transport.service.impl.customer;

import com.transport.transport.mapper.CustomerMapper;
import com.transport.transport.model.entity.Customer;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.request.customer.AddCustomerRequest;
import com.transport.transport.repository.CustomerRepository;
import com.transport.transport.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer findById(Long id) {
        return null;
    }

    @Override
    public void save(Customer entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Customer entity) {

    }

    @Override
    public Customer addCustomer(BookingRequest request) {
        Customer customer = customerMapper.updateCustomerFromBookingRequest(request);
        return customerRepository.save(customer);
    }
}
