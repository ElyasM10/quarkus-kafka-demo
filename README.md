# quarkus-kafka-demo

Demo de comunicación entre dos microservicios Quarkus a través de Apache Kafka.

## Arquitectura

```
[producer-api :8081]  --POST /messages-->  [Kafka :9092]  -->  [consumer-api :8082]
                                           topic: test-topic
```

- **producer-api**: expone un endpoint REST que publica mensajes en Kafka
- **consumer-api**: consume mensajes de Kafka y los expone vía REST
- **Kafka + Zookeeper**: broker gestionado con Docker Compose

## Requisitos

- Java 17+
- Maven 3.8+
- Docker y Docker Compose

## Estructura del proyecto

```
quarkus-kafka-demo/
├── docker-compose.local.yml     # Kafka para desarrollo local (localhost)
├── producer-api/                # Microservicio productor (puerto 8081)
└── consumer-api/                # Microservicio consumidor (puerto 8082)
```

## Levantar localmente

### 1. Iniciar Kafka

```bash
docker compose -f docker-compose.local.yml up -d
```

### 2. Levantar las APIs

```bash
# Terminal 1
cd producer-api && mvn quarkus:dev

# Terminal 2
cd consumer-api && mvn quarkus:dev
```

### 3. Probar

Enviar un mensaje:

```bash
curl -X POST http://localhost:8081/messages \
  -H "Content-Type: application/json" \
  -d '{"key": "test-1", "payload": "Hola desde producer"}'
```

Verificar que llegó al consumer:

```bash
curl http://localhost:8082/messages
```

## Endpoints

### producer-api (puerto 8081)

| Método | Ruta        | Descripción                        |
|--------|-------------|------------------------------------|
| POST   | /messages   | Publica un mensaje en Kafka        |

Body:
```json
{
  "key": "string",
  "payload": "string"
}
```

Respuesta:
```json
{
  "status": "sent",
  "key": "string",
  "payload": "string"
}
```

### consumer-api (puerto 8082)

| Método | Ruta        | Descripción                              |
|--------|-------------|------------------------------------------|
| GET    | /messages   | Lista todos los mensajes recibidos       |

Respuesta:
```json
[
  {
    "key": "string",
    "payload": "string",
    "receivedAt": "2026-06-11T12:35:39.822833086Z"
  }
]
```