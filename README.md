# my-web-framework

Mini web framework en Java construido sobre sockets, con soporte para:
- Rutas REST con `get(path, handler)` usando lambdas.
- Extracción de query params con `req.getValues("key")`.
- Servir archivos estáticos desde un directorio configurado con `staticfiles("/webroot")`.

## Arquitectura
- `HttpServer`: acepta conexiones, parsea HTTP y despacha la petición.
- `Router`: mapea rutas GET a handlers (lambdas).
- `Request`: contiene path y query params (`getValues`).
- `Response`: status/headers (base para extender).
- `StaticFileService`: carga recursos estáticos desde `src/main/resources`.

## Requisitos
- Maven 3.9+
- Java:
  - En la universidad: Java 17 (para compilar ahí)
  - En casa / entrega final: Java 21 (cambiar `maven.compiler.release` a 21)

## Cómo compilar
mvn clean package