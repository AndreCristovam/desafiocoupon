package com.andrecristovam.desafiocoupon.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrecristovam.desafiocoupon.infrastructure.persistence.entity.CouponEntity;

public interface CouponRepository extends JpaRepository<CouponEntity, String>{

}
