package com.ec.pichincha.registro.bancario.util.constant;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class ConstantsUtil {

    public static final String DEPOSITO_SALDO = "Deposito";
    public static final String RETIRO_SALDO = "Retiro";
    public static final Double ZERO = 0.0;
    public static final Boolean CUENTA_VALIDA = true;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
