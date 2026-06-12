# producer-api

Microservicio Quarkus que expone un endpoint REST para publicar mensajes en un topic de Kafka.

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
| `quarkus.http.port` | `8081` | Puerto HTTP del servicio |
| `kafka.bootstrap.servers` | `localhost:9092` | Broker de Kafka |
| `mp.messaging.outgoing.messages-out.topic` | `test-topic` | Topic destino |

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

El servicio queda disponible en `http://localhost:8081`.

## Endpoints

### `POST /messages`

Publica un mensaje en el topic `test-topic`.

**Request body:**
```json
{
  "key": "mi-clave",
  "payload": "contenido del mensaje"
}
```

**Response `202 Accepted`:**
```json
{
  "status": "sent",
  "key": "mi-clave",
  "payload": "contenido del mensaje"
}
```

**Response `500 Internal Server Error`** (si falla el envío a Kafka):
```json
{
  "status": "error",
  "key": "mi-clave",
  "payload": "descripción del error"
}
```

## Swagger UI

Con el servicio levantado, acceder a:

```
http://localhost:8081/q/swagger-ui
```

## Build para producción

```bash
# JVM
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar

# Nativo
./mvnw package -Pnative
./target/producer-api-1.0.0-SNAPSHOT-runner
```
