package com.andrecristovam.desafiocoupon.domain.coupon;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.andrecristovam.desafiocoupon.domain.enun.ECouponStatus;
import com.andrecristovam.desafiocoupon.domain.exception.BusinessException;
import com.andrecristovam.desafiocoupon.domain.exception.ErrorMessages;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Coupon {

	private static final String CODE_SANITIZE_REGEX = "[^A-Za-z0-9]";
	private static final String MIN_VALUE = "0.5";
	
	@EqualsAndHashCode.Include
	private final String code;

	private final String description;
	private final BigDecimal discountValue;
	private final OffsetDateTime expirationDate;
	private final boolean published;
	private ECouponStatus status;
	private boolean redeemed;

	public static Coupon create(String code, String description, BigDecimal discountValue, OffsetDateTime expirationDate,
			boolean published) {

		String sanitizedCode = sanitizeCode(code);

		validate(sanitizedCode, description, discountValue, expirationDate);

		return new Coupon(sanitizedCode, description, discountValue, expirationDate, published, ECouponStatus.ACTIVE, false);
	}

	private static void validate(String code, String description, BigDecimal discountValue, OffsetDateTime expirationDate) {

		if (code == null || code.length() != 6) {
			throw new BusinessException(ErrorMessages.CODE_INVALID);
		}

		if (description == null || description.isBlank()) {
			throw new BusinessException(ErrorMessages.DESCRIPTION_REQUIRED);
		}

		if (discountValue == null || discountValue.compareTo(new BigDecimal(MIN_VALUE)) < 0) {
			throw new BusinessException(ErrorMessages.DISCOUNT_MIN_VALUE);
		}

		if (expirationDate == null || expirationDate.isBefore(OffsetDateTime.now())) {
			throw new BusinessException(ErrorMessages.EXPIRATION_IN_PAST);
		}
	}

	private static String sanitizeCode(String code) {
		if (code == null) {
			return null;
		}
		return code.replaceAll(CODE_SANITIZE_REGEX, "");
	}

	public void delete() {
		if (this.status == ECouponStatus.DELETED) {
			throw new BusinessException(ErrorMessages.COUPON_ALREADY_DELETED);
		}
		this.status = ECouponStatus.DELETED;
	}
	
	public static Coupon restore(String code, String description, BigDecimal discountValue,
			OffsetDateTime expirationDate, boolean published, ECouponStatus status, boolean redeemed) {
		
		return new Coupon(code, description, discountValue, expirationDate, published, status, redeemed);
	}
}