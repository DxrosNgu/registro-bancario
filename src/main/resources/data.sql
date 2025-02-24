-- Insert into CUENTA
INSERT INTO CUENTA (cuenta_id, cliente_id, numero_cuenta, tipo_cuenta, saldo_inicial, estado)
VALUES ('fdf5cd64-a128-4720-8cfa-894600725311', '550e8422-e29b-41d4-a716-446655440000', '123456789', 'Ahorros', 7000.00, TRUE);

INSERT INTO CUENTA (cuenta_id, cliente_id, numero_cuenta, tipo_cuenta, saldo_inicial, estado)
VALUES ('535ac557-7093-4bc8-845b-1d31557364ef', '330e8422-e29b-41d4-a716-223311430000', '987654321', 'Corriente', 8500.00, TRUE);

-- Insert into MOVIMIENTOS
INSERT INTO MOVIMIENTO (movimiento_id, cuenta_id, fecha, tipo_movimiento, valor, saldo)
VALUES ('266d833d-9e8f-4ccf-8bd5-a19831356b83', 'fdf5cd64-a128-4720-8cfa-894600725311', '2025-02-07', 'Deposito', 2000.00, 7000.00);

INSERT INTO MOVIMIENTO (movimiento_id, cuenta_id, fecha, tipo_movimiento, valor, saldo)
VALUES ('c88b5cd2-71f4-42e0-aaea-7d0f218fee8d', '535ac557-7093-4bc8-845b-1d31557364ef', '2025-02-07', 'Retiro', 1500.00, 8500.00);