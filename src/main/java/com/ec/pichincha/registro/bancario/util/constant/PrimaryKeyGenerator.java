package com.ec.pichincha.registro.bancario.util.constant;

import java.util.UUID;

public class PrimaryKeyGenerator {

    public static String generatePrimaryKey() {
        return UUID.randomUUID().toString();
    }
}
