package com.andrecristovam.desafiocoupon.application.model;

import com.andrecristovam.desafiocoupon.domain.coupon.Coupon;

public record SavedCouponResult(
        String id,
        Coupon coupon
) {}