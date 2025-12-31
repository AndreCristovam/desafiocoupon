package com.andrecristovam.desafiocoupon.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.andrecristovam.desafiocoupon.domain.coupon.Coupon;
import com.andrecristovam.desafiocoupon.infrastructure.persistence.entity.CouponEntity;

@Mapper(componentModel = "spring")
public interface CouponMapper {

	CouponMapper INSTANCE = Mappers.getMapper(CouponMapper.class);

	@Mapping(target = "deleted", source = "deleted")
	CouponEntity toEntity(Coupon coupon);

	default Coupon toDomain(CouponEntity entity) {
		return Coupon.create(entity.getCode(), entity.getDescription(), entity.getDiscountValue(),
				entity.getExpirationDate(), entity.isPublished());
	}
}
