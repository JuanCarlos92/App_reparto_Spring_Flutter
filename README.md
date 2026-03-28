# Aplicación de Reparto

Aplicación móvil de reparto desarrollada con **Flutter (frontend)** y **Spring Boot (backend)**.  
Permite gestionar jornadas laborales de repartidores, asignación de clientes, rutas y entregas en tiempo real.

---

## Descripción

Esta aplicación está diseñada para optimizar el trabajo de repartidores, proporcionando herramientas como:

- Inicio de sesión seguro
- Gestión de jornada laboral (inicio/fin con cronómetro)
- Lista de clientes asignados
- Visualización de rutas en mapa
- Cálculo de distancias y tiempos estimados
- Confirmación de entregas con firma del cliente

---

## Arquitectura del Proyecto

El proyecto está dividido en dos partes principales:

 ┣ backend (Spring Boot API REST)  
 ┗ frontend (Flutter App)

---

## Backend (Spring Boot)

API REST encargada de gestionar toda la lógica de negocio y comunicación con la base de datos.

### Tecnologías utilizadas

- Java + Spring Boot
- Spring Security + JWT (autenticación)
- Lombok
- Swagger (documentación API)
- JPA / Hibernate
- Arquitectura por capas:
  - Controllers
  - Services
  - Repositories
  - DTOs
  - Entities
  - Converters

### Funcionalidades principales

- 🔐 Login de usuarios mediante JWT
- 📋 Obtención de clientes asignados
- ⏱️ Control de jornada:
  - Inicio
  - Fin
  - Tiempo actual trabajado
- 📦 Gestión de pedidos:
  - Marcar pedido como completado
  - Confirmación de entrega
- 🛒 Endpoint para listado de productos

### Futuras mejoras

- Integración con **Eureka Server** (arquitectura de microservicios)
- Implementación de **CI/CD** para automatizar testing y despliegues
- Desarrollo de **tests unitarios y de integración** en el backend con:
  - JUnit
  - Mockito

---

## Frontend (Flutter)

Aplicación móvil que consume la API REST y muestra la información al usuario.

### Tecnologías utilizadas

- Flutter (Dart)
- Integración con APIs externas:
  - Google Maps API
  - GraphHopper API

### Funcionalidades principales

- Login de usuario
- Inicio de jornada con cronómetro
- Visualización de clientes en mapa
- Cálculo de rutas
- Distancia y tiempo estimado de entrega
- Firma del cliente al recibir el pedido
- Confirmación de entrega por el repartidor

---

## APIs Externas

La aplicación hace uso de servicios externos para mejorar la experiencia:

- Google Maps API
- GraphHopper API

---

## Flujo de la aplicación

1. El repartidor inicia sesión
2. Comienza su jornada → se inicia el cronómetro
3. Se le asigna una lista de clientes
4. Puede:
   - Ver ubicación del cliente
   - Consultar ruta y tiempo estimado
5. Entrega el pedido
6. El cliente firma
7. El repartidor confirma la entrega
8. Continúa con el siguiente cliente o finaliza jornada

---

## Instalación y ejecución

### Backend

```bash
cd backend
mvn spring-boot:run
```

Acceso a Swagger:
http://localhost:8080/swagger-ui.html

---

### Frontend

```bash
cd frontend
flutter pub get
flutter run
```

---

## Seguridad

- Autenticación basada en JWT
- Control de acceso a endpoints protegidos
- Validación de usuario en cada petición

---

## Estado del proyecto

En desarrollo

---

## Autor

Juan Carlos Filter Martín  

---

## Notas

- La aplicación está orientada a dispositivos móviles
- Se recomienda configurar correctamente las claves de APIs externas
- Preparado para futura escalabilidad con microservicios
- El proyecto aún sigue en desarrollo
