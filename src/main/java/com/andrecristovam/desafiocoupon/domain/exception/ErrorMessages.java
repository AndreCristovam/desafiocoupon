package com.andrecristovam.desafiocoupon.domain.exception;

public final class ErrorMessages {

	public static final String COUPON_NOT_FOUND = "Cupom não encontrado";
    public static final String COUPON_ALREADY_DELETED = "Cupom já está deletado";
    public static final String CODE_INVALID = "Código do cupom deve conter 6 caracteres alfanuméricos";
    public static final String DESCRIPTION_REQUIRED = "Descrição é obrigatória";
    public static final String DISCOUNT_MIN_VALUE = "Valor mínimo de desconto é 0.5";
    public static final String EXPIRATION_IN_PAST = "Data de expiração não pode estar no passado";
    public static final String DUPLICATE_CODE = "Já existe um cupom cadastrado com este código";

    private ErrorMessages() {}
}
