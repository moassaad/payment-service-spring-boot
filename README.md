# Payment Service API Documentation

---

## Team Work

- Eng. Rawan Medhat
- Eng. Mohammad Asaad
- Eng. Mahmoud Hany
- Eng. Kareem Mohammad

---

## Starting

Base URL:

```code
http://{domain-or-IPv4}:8084/api/v1
```

## Authentication

### 1. Register New Client

Create a new client application.

Method: `POST`

Endpoint:

```code
/auth/register
```

Request Body

```json
{
  "username": "Kareem",
  "password": "123456"
}
```

Responses

200 OK

```json
{
  "key": "jtu3ui4-yrui-trog-utiyteer"
}
```

403 Forbidden

```json
{
  "message": "username exists"
}
```

### 2. Login Client

Authenticate existing client.

Method: `POST`

Endpoint:

```code
/auth/login
```

Request Body

```json
{
  "username": "Kareem",
  "password": "123456"
}
```

Responses

200 OK

```json
{
  "key": "jtu3ui4-yrui-trog-utiyteer"
}
```

401 Unauthorized

```json
{
  "message": "username not found"
}
```

## Payments

All payment endpoints require `apikey` in request header.

```code
apikey: dfyuf-nfdfsh-nfnfh-fdjdhjf
```

### 3. Create New Payment

Create a payment for an order.

Method: `POST`

Endpoint:

```code
/payments
```

Headers

```json
{
  "apikey": "dfyuf-nfdfsh-nfnfh-fdjdhjf"
}
```

Request Body

```json
{
  "order_id": 10,
  "customer_id": 20,
  "amount": 44.99
}
```

Responses

200 OK

```json
{
  "id": 1,
  "status": "success | failure | pending",
  "order_id": 10,
  "customer_id": 20,
  "amount": 40.99,
  "processed_at": "19-12-2026",
  "created_at": "18-12-2026"
}
```

422 Validation Error

```json
[
  {
    "field": "customer_id",
    "message": "customer required"
  }
]
```

406 Not Acceptable

```json
{
  "message": "order process duplicated"
}
```

### 4. List Customer Payments

Retrieve all payments for a specific customer.

Method: `GET`

Endpoint:

```code
/payments/{customerID}?status=success
```

Headers

```json
{
  "apikey": "dfyuf-nfdfsh-nfnfh-fdjdhjf"
}
```

Responses

200 OK

```json
[
  {
    "id": 1,
    "status": "success | failure | pending | refunded",
    "order_id": 10,
    "customer_id": 20,
    "amount": 40.99,
    "processed_at": "19-12-2026",
    "created_at": "18-12-2026"
  }
]
```

404 Not Found

```json
{
  "message": "no customer exists"
}
```

### 5. Get Payment Details

Retrieve details of a specific payment.

Method: `GET`

Endpoint:

```code
/payments/{id}
```

Headers

```json
{
  "apikey": "dfyuf-nfdfsh-nfnfh-fdjdhjf"
}
```

Responses

200 OK

```json
{
  "id": 1,
  "status": "success | failure | pending | refunded",
  "order_id": 10,
  "customer_id": 20,
  "amount": 40.99,
  "processed_at": "19-12-2026",
  "created_at": "18-12-2026"
}
```

404 Not Found

```json
{
  "message": "no payment exists"
}
```

### 6. Refund Payment

Refund a specific payment.

Method: `POST`

Endpoint:

```code
/payments/{id}/refund
```

Headers

```json
{
  "apikey": "dfyuf-nfdfsh-nfnfh-fdjdhjf"
}
```

Responses
200 OK

```json
{
  "id": 1,
  "status": "refunded",
  "order_id": 10,
  "customer_id": 20,
  "amount": 40.99,
  "refunded_at": "20-12-2026",
  "created_at": "18-12-2026"
}
```

404 Not Found

```json
{
  "message": "no payment exists"
}
```

406 Not Acceptable

```json
{
  "message": "payment cannot be refunded"
}
```

## Payment Status List

Possible payment statuses:

- success
- failure
- pending
- refunded
