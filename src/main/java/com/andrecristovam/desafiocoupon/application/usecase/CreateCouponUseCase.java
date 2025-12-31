package com.andrecristovam.desafiocoupon.application.usecase;

import org.springframework.stereotype.Service;

import com.andrecristovam.desafiocoupon.domain.coupon.Coupon;
import com.andrecristovam.desafiocoupon.infrastructure.persistence.entity.CouponEntity;
import com.andrecristovam.desafiocoupon.infrastructure.persistence.mapper.CouponMapper;
import com.andrecristovam.desafiocoupon.infrastructure.persistence.repository.CouponRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateCouponUseCase {

	private final CouponRepository repository;
	private final CouponMapper mapper;

	@Transactional
	public Coupon execute(String code, String description, java.math.BigDecimal discountValue,
			java.time.LocalDate expirationDate, boolean published) {

		Coupon coupon = Coupon.create(code, description, discountValue, expirationDate, published);

		CouponEntity entity = mapper.toEntity(coupon);

		repository.save(entity);

		return coupon;
	}
}