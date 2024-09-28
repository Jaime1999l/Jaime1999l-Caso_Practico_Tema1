LINK al repositorio: https://github.com/Jaime1999l/Jaime1999l-Caso_Practico_Tema1.git

PARTICIPACIONES:

Jaime López Díaz

Nicolás Jiménez

Fran López

Daniel Andrés


# Caso 1 Programacion Concurrente

This app was created with Bootify.io - tips on working with the code [can be found here](https://bootify.io/next-steps/).

## Development

When starting the application `docker compose up` is called and the app will connect to the contained services.
[Docker](https://www.docker.com/get-started/) must be available on the current system.

During development it is recommended to use the profile `local`. In IntelliJ `-Dspring.profiles.active=local` can be
added in the VM options of the Run Configuration after enabling this property in "Modify options". Create your own
`application-local.yml` file to override settings for development.

Lombok must be supported by your IDE. For IntelliJ install the Lombok plugin and enable annotation processing -
[learn more](https://bootify.io/next-steps/spring-boot-with-lombok.html).

In addition to the Spring Boot application, the DevServer must also be started - for this
[Node.js](https://nodejs.org/) version 20 is required. On first usage and after updates the dependencies have to be installed:

```
npm install
```

The DevServer can be started as follows:

```
npm run devserver
```

Using a proxy the whole application is now accessible under `localhost:8081`. All changes to the templates and JS/CSS
files are immediately visible in the browser.

## Build

The application can be built using the following command:

```
mvnw clean package
```

Node.js is automatically downloaded using the `frontend-maven-plugin` and the final JS/CSS files are integrated into the jar.

Start your application with the following command - here with the profile `production`:

```
java -Dspring.profiles.active=production -jar ./target/Caso-1-Programacion-Concurrente-0.0.1-SNAPSHOT.jar
```

If required, a Docker image can be created with the Spring Boot plugin. Add `SPRING_PROFILES_ACTIVE=production` as
environment variable when running the container.

```
mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=io.teamsgroup/caso-1-programacion-concurrente
```

## Further readings

* [Maven docs](https://maven.apache.org/guides/index.html)
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/reference/jpa.html)
* [Thymeleaf docs](https://www.thymeleaf.org/documentation.html)
* [Webpack concepts](https://webpack.js.org/concepts/)
* [npm docs](https://docs.npmjs.com/)
* [Bootstrap docs](https://getbootstrap.com/docs/5.3/getting-started/introduction/)
* [Htmx in a nutshell](https://htmx.org/docs/)
* [Learn Spring Boot with Thymeleaf](https://www.wimdeblauwe.com/books/taming-thymeleaf/)

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Sensor Monitoring

## Descripción

Este proyecto es una aplicación para monitorear sensores de movimiento, acceso y temperatura. Está desarrollado con **Spring Boot**, **Hibernate** y un frontend que utiliza **React** y **Node.js**.

## Estructura del Proyecto

**Backend**:

- **`service`**: Contiene la lógica de negocio para manejar usuarios, sensores y eventos.
- **`controller`**: Se encarga de manejar y dirigir las solicitudes HTTP entrantes (como `GET`, `POST`, `PUT`, `DELETE`).
- **`repos`**: Repositorios para acceder a la base de datos.
- **`domain`**: Entidades del dominio.
- **`model`**: Clases DTO y objetos de transferencia.
- **`config`**: Configuraciones de seguridad, swagger, etc.


- **Frontend**:

  - **`components`**: Componentes reutilizables para el frontend.
  - **`pages`**: Páginas principales de la aplicación.
  - **`services`**: Servicios para la comunicación con el backend.

## Casos de Uso

1. **Registro de Usuarios**: Permite registrar nuevos usuarios con diferentes roles.
2. **Gestión de Sensores**: Los administradores pueden ver y monitorear todos los sensores activos, mientras que los usuarios solo pueden ver y monitorear los sensores de temperatura.
3. **Monitoreo de Eventos**: Visualización de eventos registrados por los sensores.
4. **Autenticación**: Inicio de sesión para usuarios registrados.

## Usuarios Pre-Registrados

- **Administrador**:
  - **Correo**: admin@gmail.com
  - **Contraseña**: a12345_67
  - **Rol**: admin
- **Usuario**:
  - **Correo**: usuario@gmail.com
  - **Contraseña**: a12345_67
  - **Rol**: user

## Bibliografía

- Stack Overflow
- Repositorios de GitHub
- Foros de Programación

## Funcionamiento del Sistema

1. **Registro de Usuarios**: Se realiza a través del servicio `AuthService`. Se valida el correo y la contraseña entre otras cosas antes de realizar el registro.
2. **Gestión de Sensores**: Cada sensor tiene un token generado predictivamente.
3. **Generación de Eventos**: Se generan eventos concurrentes para cada sensor, los cuales se registran en la base de datos y se les asigna un token.

## Requisitos

- JDK 11+
- Node.js 14+
- Maven 3+

## Ejecución

1. Clonar el repositorio.
2. Ejecutar `mvn spring-boot:run` para iniciar el backend.
3. Ejecutar `npm install && npm start` en la carpeta `frontend_main` para iniciar el frontend.
