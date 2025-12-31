package com.andrecristovam.desafiocoupon.api.controller.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.andrecristovam.desafiocoupon.domain.coupon.enun.ECouponStatus;

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

