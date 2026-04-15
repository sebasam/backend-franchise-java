# 🏢 Franchise API - Accenture Technical Test

API RESTful reactiva para la gestión de franquicias, sucursales y el control de inventario de productos. Este proyecto fue desarrollado aplicando estrictos principios de diseño de software para garantizar su escalabilidad, mantenibilidad y rendimiento asíncrono.

## 🚀 Tecnologías y Arquitectura

* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.4.x (Spring WebFlux)
* **Persistencia:** MongoDB (Spring Data Reactive MongoDB)
* **Arquitectura:** Clean Architecture (Domain, Application, Infrastructure)
* **Documentación:** SpringDoc OpenAPI (Swagger UI)
* **Testing:** JUnit 5, Mockito, StepVerifier, WebTestClient
* **DevOps & Cloud:** Docker, Docker Compose, Terraform, AWS (EC2)

### 🎯 Cumplimiento de Requerimientos y Puntos Extra

✅ Aplicación de programación reactiva (WebFlux + Reactive Mongo).
✅ Estructura basada en Clean Architecture (Aislamiento del dominio y uso de puertos/adaptadores).
✅ Inclusión de pruebas unitarias (Testing reactivo de flujos y controladores).
✅ Implementación de DTOs y Global Exception Handler (`@ControllerAdvice`) para respuestas limpias.
🌟 **Plus:** Aplicación empaquetada con Docker multi-stage.
🌟 **Plus:** Endpoints para actualizar nombres (Franquicias, Sucursales, Productos) implementados.
🌟 **Plus:** Persistencia de datos e infraestructura aprovisionada como código (Terraform).
🌟 **Plus:** Solución completamente desplegada en la nube (AWS).

---

## 📖 Documentación de la API (Swagger)

La API está documentada utilizando OpenAPI 3.0. Puedes probar todos los endpoints y visualizar los esquemas de datos directamente desde la interfaz gráfica.

* **Entorno Local:** `http://localhost:8080/swagger-ui/index.html`
* **Entorno Producción (AWS):** [http://98.84.119.102:8080/swagger-ui/index.html](http://98.84.119.102:8080/swagger-ui/index.html)

---

## 🛠️ Despliegue en Entorno Local

Para evaluar la aplicación en un entorno local, la forma más sencilla es utilizar Docker Compose, el cual levantará tanto la base de datos MongoDB como la aplicación Java.

### Requisitos Previos
* Docker y Docker Compose instalados.
* Java 17 y Maven (Solo si se desea compilar manualmente).

### Pasos de ejecución:

1.  **Compilar el proyecto (Opcional):**
    Si deseas generar el `.jar` localmente antes de construir la imagen:
    ```bash
    ./mvnw clean package -DskipTests
    ```
    *(En Windows usar `mvn clean package -DskipTests`)*

2.  **Levantar los contenedores:**
    En la raíz del proyecto, ejecuta:
    ```bash
    docker-compose up --build -d
    ```

3.  **Verificar los logs (Opcional):**
    ```bash
    docker-compose logs -f app
    ```

Para detener los servicios, simplemente ejecuta: `docker-compose down`.

---

## 🧪 Ejecución de Pruebas Unitarias

El proyecto cuenta con una suite de pruebas enfocada en validar la lógica de negocio asíncrona y la respuesta de los controladores HTTP.

Para ejecutar las pruebas:
```bash
./mvnw test
