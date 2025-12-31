package com.andrecristovam.desafiocoupon.domain.coupon;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.andrecristovam.desafiocoupon.domain.coupon.enun.ECouponStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Coupon {

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
			throw new IllegalArgumentException("Código do cupom deve conter 6 caracteres alfanuméricos");
		}

		if (description == null || description.isBlank()) {
			throw new IllegalArgumentException("Descrição é obrigatória");
		}

		if (discountValue == null || discountValue.compareTo(new BigDecimal("0.5")) < 0) {
			throw new IllegalArgumentException("Valor mínimo de desconto é 0.5");
		}

		if (expirationDate == null || expirationDate.isBefore(OffsetDateTime.now())) {
			throw new IllegalArgumentException("Data de expiração não pode estar no passado");
		}
	}

	private static String sanitizeCode(String code) {
		if (code == null) {
			return null;
		}
		return code.replaceAll("[^a-zA-Z0-9]", "");
	}

	public void delete() {
		if (this.status == ECouponStatus.DELETED) {
			throw new IllegalStateException("Cupom já está deletado");
		}
		this.status = ECouponStatus.DELETED;
	}
	
	public static Coupon restore(String code, String description, BigDecimal discountValue,
			OffsetDateTime expirationDate, boolean published, ECouponStatus status, boolean redeemed) {
		
		return new Coupon(code, description, discountValue, expirationDate, published, status, redeemed);
	}
}