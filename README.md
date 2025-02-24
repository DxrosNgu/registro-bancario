# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.ec.pichincha.registro-bancario' is invalid and this project uses 'com.ec.pichincha.registro_bancario' instead.

# Creacion de cuentas
<span style="color:red">
<h3>Utilice los clienteId generados en el anterior microservicio para reemplazar en cliente para la creacion de la cuenta. </h3>
</span>

* Creacion de Cuenta Jose Lema - Ahorros:
```
curl --location 'http://localhost:8081/v1/registro-bancario/cuentas' \
--header 'Content-Type: application/json' \
--data '{
    "clienteId": "26062a18-1a69-4f05-9be1-a6d3e834d49a",
    "numeroCuenta": "478758",
    "tipoCuenta": "Ahorros",
    "saldoInicial": 2000.0,
    "estado": true
}'
```
* Creacion de Cuenta Marianela Montalvo - Corriente:
```
curl --location 'http://localhost:8081/v1/registro-bancario/cuentas' \
--header 'Content-Type: application/json' \
--data '{
    "clienteId": "603b0c7a-b2ff-4de8-804a-98cd257bf6ac",
    "numeroCuenta": "225487",
    "tipoCuenta": "Corriente",
    "saldoInicial": 100.0,
    "estado": true
}'
```
* Creacion de Cuenta Juan Osorio - Ahorros:
```
curl --location 'http://localhost:8081/v1/registro-bancario/cuentas' \
--header 'Content-Type: application/json' \
--data '{
    "clienteId": "f2eddf25-a89e-403e-9214-f2792e8b9b03",
    "numeroCuenta": "495878",
    "tipoCuenta": "Ahorros",
    "saldoInicial": 0.0,
    "estado": true
}'
```
* Creacion de Cuenta Marianela Montalvo - Ahorros:
```
curl --location 'http://localhost:8081/v1/registro-bancario/cuentas' \
--header 'Content-Type: application/json' \
--data '{
    "clienteId": "603b0c7a-b2ff-4de8-804a-98cd257bf6ac",
    "numeroCuenta": "496825",
    "tipoCuenta": "Ahorros",
    "saldoInicial": 540.0,
    "estado": true
}'
```
* Creacion de Cuenta Jose Lema - Corriente:
```
curl --location 'http://localhost:8081/v1/registro-bancario/cuentas' \
--header 'Content-Type: application/json' \
--data '{
    "clienteId": "26062a18-1a69-4f05-9be1-a6d3e834d49a",
    "numeroCuenta": "585545",
    "tipoCuenta": "Corriente",
    "saldoInicial": 1000.0,
    "estado": true
}'
```

# Creacion de Movimientos

* Creacion de transaccion para # de cuenta: 478758

Busqueda de informacion de la cuenta:
```
http://localhost:8081/v1/registro-bancario/cuentas/buscar?numeroCuenta=478758
```
Registrar transaccion:
```
curl --location 'http://localhost:8081/v1/registro-bancario/transaccion' \
--header 'Content-Type: application/json' \
--data '{
    "tipoMovimiento": "Retiro",
    "valor": 575.0,
    "numeroCuenta": "478758"
}'
```
* Creacion de transaccion para # de cuenta: 225487

Busqueda de informacion de la cuenta:
```
http://localhost:8081/v1/registro-bancario/cuentas/buscar?numeroCuenta=225487
```
Registrar transaccion:
```
curl --location 'http://localhost:8081/v1/registro-bancario/transaccion' \
--header 'Content-Type: application/json' \
--data '{
    "tipoMovimiento": "Deposito",
    "valor": 600.0,
    "numeroCuenta": "225487"
}'
```
* Creacion de transaccion para # de cuenta: 495878

Busqueda de informacion de la cuenta:
```
http://localhost:8081/v1/registro-bancario/cuentas/buscar?numeroCuenta=495878
```
Registrar transaccion:
```
curl --location 'http://localhost:8081/v1/registro-bancario/transaccion' \
--header 'Content-Type: application/json' \
--data '{
    "tipoMovimiento": "Deposito",
    "valor": 150.0,
    "numeroCuenta": "495878"
}'
```
* Creacion de transaccion para # de cuenta: 496825

* Busqueda de informacion de la cuenta:
```
http://localhost:8081/v1/registro-bancario/cuentas/buscar?numeroCuenta=496825
```
Registrar transaccion:
```
curl --location 'http://localhost:8081/v1/registro-bancario/transaccion' \
--header 'Content-Type: application/json' \
--data '{
    "tipoMovimiento": "Retiro",
    "valor": 540.0,
    "numeroCuenta": "496825"
}'
```

# Lista de Movimientos, por fechas x usuario.
* Hacer busqueda del cliente Marianela Montalvo:
```
curl --location 'http://localhost:8081/v1/registro-bancario/cliente/603b0c7a-b2ff-4de8-804a-98cd257bf6ac/reportes?initFecha=2025-02-01&finFecha=2025-02-30'
```

