# My Web Framework – Tomas Espitia

Este proyecto es un framework web desarrollado en Java utilizando sockets, donde implemente mi propio manejo básico del protocolo HTTP.

El framework permite:

Definir servicios REST de manera simple usando funciones lambda.

Obtener valores enviados en la URL mediante parámetros de consulta.

Servir archivos estáticos como páginas HTML y recursos desde un directorio configurado.

---

## Arquitectura

- `HttpServer`: acepta conexiones, parsea HTTP y despacha la petición.
- `Router`: mapea rutas GET a handlers (lambdas).
- `Request`: contiene path y query params.
- `Response`: status y headers (base para extender).
- `StaticFileService`: carga recursos estáticos desde `src/main/resources`.

---

## Requisitos

- Maven 3.9+
- Java:
  - En la universidad: Java 17
  - En entrega final: Java 21

---

## Cómo compilar

```bash
mvn clean package
```

Genera:

```
target/my-web-framework-1.0-SNAPSHOT.jar
```

---

## Cómo ejecutar

```bash
java -jar target/my-web-framework-1.0-SNAPSHOT.jar
```

Servidor disponible en:

```
http://localhost:8080
```

---

## Pruebas

Pruebas manuales mediante navegador:

- `/App/pi` devuelve el valor de π.
- `/App/hello?name=Tomas Espitia Quiroga` devuelve `Hello Tomas Espitia Quiroga`.

Se valida:

- Funcionamiento del servidor HTTP
- Resolución de rutas REST
- Extracción de query parameters
- Servicio de archivos estáticos

---

## Deployment

1. Compilar:

```bash
mvn clean package
```

2. Ejecutar el JAR en el servidor destino:

```bash
java -jar my-web-framework-1.0-SNAPSHOT.jar
```
---

## Built With

- Java
- Maven
- ServerSocket (Java Sockets)
- HTTP 1.1

---

## Versioning

Versionado semántico (SemVer).

Versión actual:

```
1.0-SNAPSHOT
```

---
