package com.andrecristovam.desafiocoupon.application.usecase;

import org.springframework.stereotype.Service;

import com.andrecristovam.desafiocoupon.application.usecase.mapper.CouponDomainMapper;
import com.andrecristovam.desafiocoupon.application.usecase.model.CreateCouponCommand;
import com.andrecristovam.desafiocoupon.application.usecase.model.SavedCouponResult;
import com.andrecristovam.desafiocoupon.infrastructure.persistence.mapper.CouponPersistenceMapper;
import com.andrecristovam.desafiocoupon.infrastructure.persistence.repository.CouponRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateCouponUseCase {

	private final CouponRepository repository;
	
	private final CouponPersistenceMapper persistenceMapper;
	
	private final CouponDomainMapper domainMapper;

	@Transactional
	public SavedCouponResult execute(CreateCouponCommand command) {

		var coupon = domainMapper.toDomain(command);

		var saved = repository.save(persistenceMapper.toEntity(coupon));
		
		return new SavedCouponResult(saved.getId(), coupon);
	}
}