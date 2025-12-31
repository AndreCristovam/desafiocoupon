package com.andrecristovam.desafiocoupon.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrecristovam.desafiocoupon.domain.coupon.Coupon;
import com.andrecristovam.desafiocoupon.infrastructure.persistence.mapper.CouponPersistenceMapper;
import com.andrecristovam.desafiocoupon.infrastructure.persistence.repository.CouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetCouponUseCase {

	private final CouponRepository repository;
    private final CouponPersistenceMapper mapper;

    @Transactional(readOnly = true)
    public Coupon executeById(String id) {
    	
    	return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Cupom não encontrado"));
    }
}
