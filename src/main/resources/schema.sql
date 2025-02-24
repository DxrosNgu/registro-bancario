DROP TABLE IF EXISTS MOVIMIENTO;
DROP TABLE IF EXISTS CUENTA;

CREATE TABLE CUENTA (
    cuenta_id VARCHAR(36) PRIMARY KEY,
    cliente_id VARCHAR(36) NOT NULL,
    numero_cuenta VARCHAR(50) UNIQUE NOT NULL,
    tipo_cuenta VARCHAR(20) CHECK (tipo_cuenta IN ('Ahorros', 'Corriente')) NOT NULL,
    saldo_inicial DECIMAL(15,2) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
   -- FOREIGN KEY (cliente_id) REFERENCES CLIENTE(cliente_id) ON DELETE CASCADE
);

CREATE TABLE MOVIMIENTO (
    movimiento_id VARCHAR(36) PRIMARY KEY,
    cuenta_id VARCHAR(36) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(20) CHECK (tipo_movimiento IN ('Deposito', 'Retiro')) NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES CUENTA(cuenta_id) ON DELETE CASCADE
);