package com.transport.transport.mapper;

import com.transport.transport.model.entity.Customer;
import com.transport.transport.model.request.booking.BookingRequest;
import com.transport.transport.model.request.customer.AddCustomerRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface CustomerMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer updateCustomerFromBookingRequest(BookingRequest request);
}
