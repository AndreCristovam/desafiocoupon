package com.andrecristovam.desafiocoupon.application.usecase.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record CreateCouponCommand(
		String code,
        String description,
        BigDecimal discountValue,
        OffsetDateTime expirationDate,
        boolean published
) {}
