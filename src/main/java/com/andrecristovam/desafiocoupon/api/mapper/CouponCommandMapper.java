package com.andrecristovam.desafiocoupon.api.mapper;

import org.mapstruct.Mapper;

import com.andrecristovam.desafiocoupon.api.dto.CreateCouponRequest;
import com.andrecristovam.desafiocoupon.application.model.CreateCouponCommand;

@Mapper(componentModel = "spring")
public interface CouponCommandMapper {
	
	CreateCouponCommand toCommand(CreateCouponRequest request);
}
