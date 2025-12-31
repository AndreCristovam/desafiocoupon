package com.andrecristovam.desafiocoupon.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrecristovam.desafiocoupon.infrastructure.entity.CouponEntity;

public interface CouponRepository extends JpaRepository<CouponEntity, String>{

	boolean existsByCode(String code);

}
