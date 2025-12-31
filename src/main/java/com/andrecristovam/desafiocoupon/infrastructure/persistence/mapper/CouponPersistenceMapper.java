package com.andrecristovam.desafiocoupon.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.andrecristovam.desafiocoupon.domain.coupon.Coupon;
import com.andrecristovam.desafiocoupon.infrastructure.persistence.entity.CouponEntity;

@Mapper(componentModel = "spring")
public interface CouponPersistenceMapper {

	default CouponEntity toEntity(Coupon c) {
		return CouponEntity.builder().code(c.getCode()).description(c.getDescription())
				.discountValue(c.getDiscountValue()).expirationDate(c.getExpirationDate()).status(c.getStatus())
				.published(c.isPublished()).redeemed(c.isRedeemed()).build();
	}

	default Coupon toDomain(CouponEntity e) {
		return Coupon.restore(e.getCode(), e.getDescription(), e.getDiscountValue(), e.getExpirationDate(),
				e.isPublished(), e.getStatus(), e.isRedeemed());
	}
}
