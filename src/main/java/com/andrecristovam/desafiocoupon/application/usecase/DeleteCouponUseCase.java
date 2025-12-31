package com.andrecristovam.desafiocoupon.application.usecase;

import org.springframework.stereotype.Service;

import com.andrecristovam.desafiocoupon.domain.coupon.Coupon;
import com.andrecristovam.desafiocoupon.domain.exception.NotFoundException;
import com.andrecristovam.desafiocoupon.infrastructure.mapper.CouponPersistenceMapper;
import com.andrecristovam.desafiocoupon.infrastructure.repository.CouponRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteCouponUseCase {

	private final CouponRepository repository;
    private final CouponPersistenceMapper mapper;

    @Transactional
    public void execute(String id) {

        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cupom não encontrado"));

        Coupon coupon = mapper.toDomain(entity);

        coupon.delete();
        
        entity.setStatus(coupon.getStatus());
        entity.setRedeemed(coupon.isRedeemed());
        
        repository.save(entity);
    }
}
