package com.andrecristovam.desafiocoupon.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record CreateCouponRequest(
        String code,
        String description,
        BigDecimal discountValue,
        OffsetDateTime expirationDate,
        boolean published
) {}
