package com.andrecristovam.desafiocoupon.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.andrecristovam.desafiocoupon.domain.enun.ECouponStatus;

public record CouponResponse(
		String id,
        String code,
        String description,
        BigDecimal discountValue,
        OffsetDateTime expirationDate,
        ECouponStatus status,
        boolean published,
        boolean redeemed
) {}

