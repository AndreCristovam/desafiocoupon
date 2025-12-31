package com.andrecristovam.desafiocoupon.domain.coupon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import com.andrecristovam.desafiocoupon.domain.coupon.enun.ECouponStatus;
import com.andrecristovam.desafiocoupon.domain.coupon.exception.BusinessException;

public class CouponTest {

	@Test
	void criarCupomValidoTest() {

		Coupon coupon = Coupon.create("AB1232!", "Cupom de teste", new BigDecimal("10.0"), tomorrow(),
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
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			Coupon.create("A!2", "Cupom inválido", new BigDecimal("10.0"), tomorrow(), true);
		});
		assertEquals("Código do cupom deve conter 6 caracteres alfanuméricos", exception.getMessage());
	}
	
	@Test
	void criarCupomComCodigoVazioNullTest() {
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			Coupon.create(null, "Cupom inválido", new BigDecimal("10.0"), tomorrow(), true);
		});
		assertEquals("Código do cupom deve conter 6 caracteres alfanuméricos", exception.getMessage());
	}
	
	@Test
	void criarCupomComSemDescricaoTest() {
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			Coupon.create("AB1232", "", new BigDecimal("10.0"), tomorrow(), true);
		});
		assertEquals("Descrição é obrigatória", exception.getMessage());
	}

	@Test
	void deletarCupomValidoTest() {
		Coupon coupon = Coupon.create("ABC123", "Cupom para deletar", new BigDecimal("5.0"),
				tomorrow(), false);

		assertEquals(ECouponStatus.ACTIVE, coupon.getStatus());
		coupon.delete();
		assertEquals(ECouponStatus.DELETED, coupon.getStatus());
	}

	@Test
	void deletarCupomJaDeletadoTest() {
		Coupon coupon = Coupon.create("ABC123", "Cupom já deletado", new BigDecimal("15.0"),
				tomorrow(), true);

		coupon.delete();

		BusinessException exception = assertThrows(BusinessException.class, coupon::delete);
		assertEquals("Cupom já está deletado", exception.getMessage());
	}

	@Test
	void criarCupomDescontoMenorQueMinimoTest() {
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			Coupon.create("ABC123", "Cupom com desconto inválido", new BigDecimal("0.3"), tomorrow(),
					true);
		});
		assertEquals("Valor mínimo de desconto é 0.5", exception.getMessage());
	}

	@Test
	void criarCupomComDataNoPassadoTest() {
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			Coupon.create("ABC123", "Cupom expirado", new BigDecimal("10.0"), OffsetDateTime.now().minusDays(1), true);
		});
		assertEquals("Data de expiração não pode estar no passado", exception.getMessage());
	}
	
	@Test
	void removerCaracteresEspeciaisDoCodigoTest() {

	    Coupon coupon = Coupon.create("A!BC-123", "Cupom", new BigDecimal("5.0"),
	    		tomorrow(), true);

	    assertEquals("ABC123", coupon.getCode());
	}
	
	@Test
	void criarCupomComCodigoMaiorQueSeisCaracteresDeveFalharTest() {
	    assertThrows(BusinessException.class, () -> 
	        Coupon.create("ABCDEFG", "Cupom inválido",
	                new BigDecimal("5.0"), tomorrow(), true)
	    );
	}

	@Test
	void criarCupomComDescricaoApenasComEspacosDeveFalharTest() {
	    assertThrows(BusinessException.class, () ->
	        Coupon.create("ABC123", "   ",
	                new BigDecimal("5.0"), tomorrow(), true)
	    );
	}

	@Test
	void criarCupomComDescricaoNullTest() {
	    BusinessException exception = assertThrows(BusinessException.class, () -> {
	        Coupon.create("ABC123", null, new BigDecimal("10.0"), tomorrow(), true);
	    });
	    assertEquals("Descrição é obrigatória", exception.getMessage());
	}

	@Test
	void criarCupomComDescontoNullTest() {
	    BusinessException exception = assertThrows(BusinessException.class, () -> {
	        Coupon.create("ABC123", "Cupom", null, tomorrow(), true);
	    });
	    assertEquals("Valor mínimo de desconto é 0.5", exception.getMessage());
	}

	@Test
	void criarCupomSemDataDeExpiracaoTest() {
	    BusinessException exception = assertThrows(BusinessException.class, () -> {
	        Coupon.create("ABC123", "Cupom", new BigDecimal("5.0"), null, true);
	    });
	    assertEquals("Data de expiração não pode estar no passado", exception.getMessage());
	}

	@Test
	void criarCupomComCodigoNullNaoExplodeSanitizeTest() {
	    BusinessException exception = assertThrows(BusinessException.class, () -> {
	        Coupon.create(null, "Cupom", new BigDecimal("5.0"), tomorrow(), true);
	    });
	    assertEquals("Código do cupom deve conter 6 caracteres alfanuméricos", exception.getMessage());
	}
	
	@Test
	void igualdadeEntreCuponsComMesmoCodigoDeveSerVerdadeiraTest() {

	    Coupon a = Coupon.create("ABC123", "Cupom A", new BigDecimal("5.0"), tomorrow(), true);
	    Coupon b = Coupon.create("ABC123", "Outro cupom", new BigDecimal("6.0"), tomorrow(), false);

	    assertEquals(a, b);
	    assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	void cuponsComCodigosDiferentesNaoDevemSerIguaisTest() {

	    Coupon a = Coupon.create("ABC123", "Cupom A", new BigDecimal("5.0"), tomorrow(), true);
	    Coupon b = Coupon.create("XYZ999", "Cupom B", new BigDecimal("5.0"), tomorrow(), true);

	    assertNotEquals(a, b);
	}

	@Test
	void equalsDeveRetornarFalseParaNullOuOutroTipoTest() {

	    Coupon coupon = Coupon.create("ABC123", "Cupom", new BigDecimal("5.0"), tomorrow(), true);

	    assertNotEquals(coupon, null);
	    assertNotEquals(coupon, "string qualquer");
	}
	
	private OffsetDateTime tomorrow() {
	    return OffsetDateTime.now().plusDays(1);
	}
}
