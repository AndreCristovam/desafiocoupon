package com.andrecristovam.desafiocoupon.application.usecase.mapper;

import org.mapstruct.Mapper;

import com.andrecristovam.desafiocoupon.application.usecase.model.CreateCouponCommand;
import com.andrecristovam.desafiocoupon.domain.coupon.Coupon;

@Mapper(componentModel = "spring")
public interface CouponDomainMapper {

	default Coupon toDomain(CreateCouponCommand c) {
		return Coupon.create(c.code(), c.description(), c.discountValue(), c.expirationDate(), c.published());
	}
}
