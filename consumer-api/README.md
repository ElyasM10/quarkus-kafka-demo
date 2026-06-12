# consumer-api

Microservicio Quarkus que consume mensajes de un topic de Kafka y los expone a través de un endpoint REST.

## Tecnologías

- Java 17
- Quarkus 3.27.2
- SmallRye Reactive Messaging (Kafka)
- Quarkus REST + Jackson
- SmallRye OpenAPI

## Requisitos previos

- JDK 17+
- Maven 3.8+
- Kafka corriendo en `localhost:9092` (ver `docker-compose.yml` en la raíz del proyecto)

## Configuración

`src/main/resources/application.properties`:

| Propiedad | Valor por defecto | Descripción |
|---|---|---|
| `quarkus.http.port` | `8082` | Puerto HTTP del servicio |
| `kafka.bootstrap.servers` | `localhost:9092` | Broker de Kafka |
| `mp.messaging.incoming.messages-in.topic` | `test-topic` | Topic que se consume |
| `mp.messaging.incoming.messages-in.group.id` | `consumer-api-group` | Consumer group |
| `mp.messaging.incoming.messages-in.auto.offset.reset` | `earliest` | Lee desde el inicio si no hay offset guardado |

## Levantar infraestructura Kafka

Desde la raíz del proyecto:

```bash
docker compose up -d
```

Esto levanta Zookeeper, Kafka y Kafka UI (`http://localhost:8080`).

## Ejecutar en modo desarrollo

```bash
./mvnw quarkus:dev
```

El servicio queda disponible en `http://localhost:8082`.

## Endpoints

### `GET /messages`

Devuelve todos los mensajes recibidos desde Kafka desde que el servicio inició.

**Response `200 OK`:**
```json
[
  {
    "key": "mi-clave",
    "payload": "contenido del mensaje",
    "receivedAt": "2026-06-12T12:00:00Z"
  }
]
```

> Los mensajes se acumulan en memoria. Al reiniciar el servicio se pierden, pero con `auto.offset.reset=earliest` vuelve a consumir desde el inicio del topic.

## Swagger UI

Con el servicio levantado, acceder a:

```
http://localhost:8082/q/swagger-ui
```

## Build para producción

```bash
# JVM
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar

# Nativo
./mvnw package -Pnative
./target/consumer-api-1.0.0-SNAPSHOT-runner
```
