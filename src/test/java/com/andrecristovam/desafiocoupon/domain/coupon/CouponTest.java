package com.andrecristovam.desafiocoupon.domain.coupon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import com.andrecristovam.desafiocoupon.domain.coupon.enun.ECouponStatus;

public class CouponTest {

	@Test
	void criarCupomValidoTest() {

		Coupon coupon = Coupon.create("AB1232!", "Cupom de teste", new BigDecimal("10.0"), OffsetDateTime.now().plusDays(1),
				true);

		assertNotNull(coupon);
		assertEquals("AB1232", coupon.getCode());
		assertEquals(new BigDecimal("10.0"), coupon.getDiscountValue());
		assertTrue(coupon.isPublished());
		assertEquals(ECouponStatus.ACTIVE, coupon.getStatus());
		assertFalse(coupon.isRedeemed());
	}

	@Test
	void criarCupomComCodigoInvalidoTest() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			Coupon.create("A!2", "Cupom inválido", new BigDecimal("10.0"), OffsetDateTime.now().plusDays(1), true);
		});
		assertEquals("Código do cupom deve conter 6 caracteres alfanuméricos", exception.getMessage());
	}
	
	@Test
	void criarCupomComCodigoVazioNullTest() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			Coupon.create(null, "Cupom inválido", new BigDecimal("10.0"), OffsetDateTime.now().plusDays(1), true);
		});
		assertEquals("Código do cupom deve conter 6 caracteres alfanuméricos", exception.getMessage());
	}
	
	@Test
	void criarCupomComSemDescricaoTest() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			Coupon.create("AB1232", "", new BigDecimal("10.0"), OffsetDateTime.now().plusDays(1), true);
		});
		assertEquals("Descrição é obrigatória", exception.getMessage());
	}

	@Test
	void deletarCupomValidoTest() {
		Coupon coupon = Coupon.create("ABC123", "Cupom para deletar", new BigDecimal("5.0"),
				OffsetDateTime.now().plusDays(1), false);

		assertEquals(ECouponStatus.ACTIVE, coupon.getStatus());
		coupon.delete();
		assertEquals(ECouponStatus.DELETED, coupon.getStatus());
	}

	@Test
	void deletarCupomJaDeletadoTest() {
		Coupon coupon = Coupon.create("ABC123", "Cupom já deletado", new BigDecimal("15.0"),
				OffsetDateTime.now().plusDays(1), true);

		coupon.delete();

		IllegalStateException exception = assertThrows(IllegalStateException.class, coupon::delete);
		assertEquals("Cupom já está deletado", exception.getMessage());
	}

	@Test
	void criarCupomDescontoMenorQueMinimoTest() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			Coupon.create("ABC123", "Cupom com desconto inválido", new BigDecimal("0.3"), OffsetDateTime.now().plusDays(1),
					true);
		});
		assertEquals("Valor mínimo de desconto é 0.5", exception.getMessage());
	}

	@Test
	void criarCupomComDataNoPassadoTest() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			Coupon.create("ABC123", "Cupom expirado", new BigDecimal("10.0"), OffsetDateTime.now().minusDays(1), true);
		});
		assertEquals("Data de expiração não pode estar no passado", exception.getMessage());
	}
}
