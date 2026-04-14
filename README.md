Aquí tienes un README claro y profesional para tu proyecto. Puedes copiarlo y adaptarlo según detalles específicos (nombre del proyecto, endpoints, etc.):

---

# 🐾 Sistema de Gestión de Mascotas (Backend)

Backend desarrollado en **Java** utilizando **Spring WebFlux** bajo una **arquitectura hexagonal (Ports & Adapters)**. Este sistema permite gestionar información básica de mascotas de forma reactiva, escalable y mantenible.

---

## 📌 Descripción

Este proyecto implementa un sistema básico para la gestión de mascotas, permitiendo operaciones CRUD (crear, consultar, actualizar y eliminar). Está construido con un enfoque reactivo usando Spring WebFlux, lo que permite manejar múltiples solicitudes de manera eficiente.

Se aplica arquitectura hexagonal para separar claramente la lógica de negocio del acceso a datos y de las interfaces externas.

---

## 🧱 Arquitectura

El proyecto sigue el patrón de **arquitectura hexagonal**, dividido en:

* **Dominio**: Contiene las entidades y reglas de negocio.
* **Aplicación (Casos de uso)**: Lógica que orquesta las operaciones.
* **Puertos (Ports)**: Interfaces que definen cómo interactúa el sistema.
* **Adaptadores (Adapters)**:

  * Entrada: Controladores REST (WebFlux).
  * Salida: Persistencia (base de datos, repositorios).

```
src/
 ├── domain/
 ├── application/
 ├── ports/
 ├── adapters/
 │    ├── inbound/
 │    └── outbound/
 └── config/
```

---

## ⚙️ Tecnologías utilizadas

* Java 17+
* Spring Boot
* Spring WebFlux
* Project Reactor
* Maven / Gradle
* Base de datos (ej: MongoDB o PostgreSQL, según implementación)

---

## 🚀 Características

* API REST reactiva
* Arquitectura limpia y desacoplada
* Manejo asíncrono con programación reactiva
* CRUD de mascotas
* Escalabilidad y fácil mantenimiento

---

## 📡 Endpoints principales (ejemplo)

| Método | Endpoint     | Descripción                |
| ------ | ------------ | -------------------------- |
| GET    | `/pets`      | Obtener todas las mascotas |
| GET    | `/pets/{id}` | Obtener mascota por ID     |
| POST   | `/pets`      | Crear nueva mascota        |
| PUT    | `/pets/{id}` | Actualizar mascota         |
| DELETE | `/pets/{id}` | Eliminar mascota           |

---

## ▶️ Ejecución del proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/juanpablo2005-alt/petflow-api
cd petflow-api
```

### 2. Compilar el proyecto

```bash
./mvnw clean install
```

### 3. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

---

## 🧪 Pruebas

Para ejecutar las pruebas:

```bash
./mvnw test
```

---

## 📁 Ejemplo de entidad (Mascota)

```java
public class Pet {
    private String id;
    private String name;
    private String species;
    private int age;
}
```

---

## 📌 Buenas prácticas implementadas

* Separación de responsabilidades
* Principios SOLID
* Programación reactiva
* Código limpio y mantenible

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT.

---

## ✍️ Autor

Desarrollado por
* Juan Pablo Garcia
* David Garces
* Yury Moreno

---
