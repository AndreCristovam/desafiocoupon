package com.andrecristovam.desafiocoupon.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.andrecristovam.desafiocoupon.domain.coupon.enun.ECouponStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

	@Column(length = 6, nullable = false, unique = true)
    private String code;

	@Lob
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal discountValue;

    @Column(nullable = false)
    private OffsetDateTime expirationDate;
    
    @Enumerated(EnumType.STRING)
    private ECouponStatus status;

    @Column(nullable = false)
    private boolean published;

    @Column(nullable = false)
    private boolean redeemed;
}
