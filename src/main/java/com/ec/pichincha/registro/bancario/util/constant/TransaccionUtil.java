package com.ec.pichincha.registro.bancario.util.constant;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.ec.pichincha.registro.bancario.util.constant.ConstantsUtil.*;

public class TransaccionUtil {


    public static Double getMovimiento(String tipoMovimiento, Double valor){
     return DEPOSITO_SALDO.equals(tipoMovimiento) ? valor : - valor;
    }

    public static Double getSaldoDisponible (Double saldoInicial, Double movimiento){
        return saldoInicial + movimiento;
    }

    public static Double getSaldoInicial (Double SaldoDisponible, Double movimiento){
        return SaldoDisponible - movimiento;
    }

    public static LocalDateTime getFechaInicial(String initFecha){
       return LocalDate.parse(initFecha, DATE_TIME_FORMATTER).atStartOfDay();
    }

    public static LocalDateTime getFechaFin(String finFecha){
        return LocalDate.parse(finFecha, DATE_TIME_FORMATTER).atTime(23, 59, 59);
    }
}
