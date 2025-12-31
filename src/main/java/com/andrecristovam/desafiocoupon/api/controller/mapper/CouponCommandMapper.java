package com.andrecristovam.desafiocoupon.api.controller.mapper;

import org.mapstruct.Mapper;

import com.andrecristovam.desafiocoupon.api.controller.dto.CreateCouponRequest;
import com.andrecristovam.desafiocoupon.application.usecase.model.CreateCouponCommand;

@Mapper(componentModel = "spring")
public interface CouponCommandMapper {
	
	CreateCouponCommand toCommand(CreateCouponRequest request);
}
