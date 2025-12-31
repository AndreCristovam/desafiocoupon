package com.andrecristovam.desafiocoupon.application.usecase;

import org.springframework.stereotype.Service;

import com.andrecristovam.desafiocoupon.application.mapper.CouponDomainMapper;
import com.andrecristovam.desafiocoupon.application.model.CreateCouponCommand;
import com.andrecristovam.desafiocoupon.application.model.SavedCouponResult;
import com.andrecristovam.desafiocoupon.domain.exception.BusinessException;
import com.andrecristovam.desafiocoupon.domain.exception.ErrorMessages;
import com.andrecristovam.desafiocoupon.infrastructure.mapper.CouponPersistenceMapper;
import com.andrecristovam.desafiocoupon.infrastructure.repository.CouponRepository;

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
		
		if (repository.existsByCode(coupon.getCode())) {
		    throw new BusinessException(ErrorMessages.DUPLICATE_CODE);
		}

		var saved = repository.save(persistenceMapper.toEntity(coupon));
		
		return new SavedCouponResult(saved.getId(), coupon);
	}
}