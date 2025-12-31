package com.andrecristovam.desafiocoupon.api.controller.mapper;

import org.mapstruct.Mapper;

import com.andrecristovam.desafiocoupon.api.controller.dto.CouponResponse;
import com.andrecristovam.desafiocoupon.domain.coupon.Coupon;

@Mapper(componentModel = "spring")
public interface CouponApiMapper {

	default CouponResponse toResponse(String id, Coupon c) {
		return new CouponResponse(id, c.getCode(), c.getDescription(), c.getDiscountValue(), c.getExpirationDate(),
				c.getStatus(), c.isPublished(), c.isRedeemed());
	}
}
