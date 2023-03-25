package com.transport.transport.mapper;

import com.transport.transport.model.entity.Voucher;
import com.transport.transport.model.request.voucher.UpdateVoucherRequest;
import com.transport.transport.model.request.voucher.VoucherRequest;
import com.transport.transport.model.response.voucher.VoucherResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface VoucherMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "voucherCode", target = "voucherCode")
    @Mapping(source = "expiredTime", target = "expiredTime")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "discountValue", target = "discountValue")
    Voucher createVoucherFromRequest(VoucherRequest voucherRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",source = "voucherId")
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "voucherCode", target = "voucherCode")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "expiredTime", target = "expiredTime")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "discountValue", target = "discountValue")
    Voucher createVoucherFromUpdateVoucherRequest(UpdateVoucherRequest voucherRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "owner", target = "owner")
    VoucherResponse createVoucherResponseFromEntity(Voucher voucher);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "createVoucherResponseFromEntity")
    List<VoucherResponse> createVoucherResponseFromEntity(List<Voucher> voucher);

}
