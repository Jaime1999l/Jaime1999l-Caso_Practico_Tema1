package io.teamsgroup.caso_1_programacion_concurrente;

import io.teamsgroup.caso_1_programacion_concurrente.model.*;
import io.teamsgroup.caso_1_programacion_concurrente.service.AuthService;
import io.teamsgroup.caso_1_programacion_concurrente.service.EventoService;
import io.teamsgroup.caso_1_programacion_concurrente.service.SensorAccesoService;
import io.teamsgroup.caso_1_programacion_concurrente.service.SensorMovimientoService;
import io.teamsgroup.caso_1_programacion_concurrente.service.SensorTemperaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Caso1ProgramacionConcurrenteApplication implements CommandLineRunner {

    @Autowired
    private AuthService authService;
    @Autowired
    private EventoService eventoService;
    @Autowired
    private SensorMovimientoService sensorMovimientoService;
    @Autowired
    private SensorAccesoService sensorAccesoService;
    @Autowired
    private SensorTemperaturaService sensorTemperaturaService;

    public static void main(final String[] args) {
        SpringApplication.run(Caso1ProgramacionConcurrenteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Limpiar la base de datos
        limpiarBaseDeDatos();

        // Registro de un nuevo usuario
        registrarNuevoUsuario(
                "Administrador",
                "Ap",
                "Ap2",
                "usuario1@example.com",
                123456789,
                "Calle Concurrente 123",
                "12345",
                "admin"
        );
        registrarNuevoUsuario(
                "Usuario",
                "Ap",
                "Ap2",
                "usuario2@example.com",
                123456789,
                "Calle Concurrente 123",
                "12345",
                "user"
        );

        System.out.println("Usuarios registrados exitosamente.");
    }

    /**
     * Limpia los datos de la base de datos eliminando eventos y sensores.
     */
    private void limpiarBaseDeDatos() {
        System.out.println("Limpiando base de datos...");

        // Eliminar todos los eventos relacionados antes de eliminar los sensores
        eventoService.deleteAll();

        // Eliminar todos los sensores de movimiento
        sensorMovimientoService.findAll().forEach(sensor -> sensorMovimientoService.delete(sensor.getId()));

        // Eliminar todos los sensores de acceso
        sensorAccesoService.findAll().forEach(sensor -> sensorAccesoService.delete(sensor.getId()));

        // Eliminar todos los sensores de temperatura
        sensorTemperaturaService.findAll().forEach(sensor -> sensorTemperaturaService.delete(sensor.getId()));

        System.out.println("Base de datos vaciada.\n");
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param nombre     Nombre del usuario.
     * @param apellido1  Primer apellido del usuario.
     * @param apellido2  Segundo apellido del usuario.
     * @param correo     Correo electrónico del usuario.
     * @param telefono   Número de teléfono del usuario.
     * @param direccion  Dirección del usuario (opcional).
     * @param contrasena Contraseña del usuario.
     * @param rolNombre  Rol del usuario (admin/user).
     */
    private void registrarNuevoUsuario(String nombre, String apellido1, String apellido2, String correo,
                                       int telefono, String direccion, String contrasena, String rolNombre) {

        // Crear el objeto RegisterRequest con la información del usuario
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setNombre(nombre);
        registerRequest.setApellido1(apellido1);
        registerRequest.setApellido2(apellido2);
        registerRequest.setCorreo(correo);
        registerRequest.setTelefono(telefono);
        registerRequest.setDireccion(direccion);
        registerRequest.setContrasena(contrasena);

        // Llamada al método register con dos parámetros
        authService.register(registerRequest, rolNombre);

        System.out.println("Usuario registrado con nombre: " + nombre + " y rol: " + rolNombre);
        /*Scanner scanner = new Scanner(System.in);

        System.out.println("Limpiando base de datos...");

        // Eliminar todos los eventos relacionados antes de eliminar los sensores
        eventoService.deleteAll();  // Asumiendo que eventoService tiene un método para eliminar todos los eventos

        // Eliminar todos los sensores de movimiento
        List<SensorMovimientoDTO> sensoresMovimiento = sensorMovimientoService.findAll();
        for (SensorMovimientoDTO sensor : sensoresMovimiento) {
            sensorMovimientoService.delete(sensor.getId());
        }

        // Eliminar todos los sensores de acceso
        List<SensorAccesoDTO> sensoresAcceso = sensorAccesoService.findAll();
        for (SensorAccesoDTO sensor : sensoresAcceso) {
            sensorAccesoService.delete(sensor.getId());
        }

        // Eliminar todos los sensores de temperatura
        List<SensorTemperaturaDTO> sensoresTemperatura = sensorTemperaturaService.findAll();
        for (SensorTemperaturaDTO sensor : sensoresTemperatura) {
            sensorTemperaturaService.delete(sensor.getId());
        }

        System.out.println("Base de datos vaciada.\n");

        // -------- Registro de Usuario --------
        System.out.println("=== Registro de Usuario ===");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese su primer apellido: ");
        String apellido1 = scanner.nextLine();

        System.out.print("Ingrese su segundo apellido: ");
        String apellido2 = scanner.nextLine();

        System.out.print("Ingrese su correo: ");
        String correo = scanner.nextLine();

        System.out.print("Ingrese su teléfono: ");
        int telefono = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        System.out.print("Ingrese su dirección (opcional): ");
        String direccion = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        System.out.print("Ingrese el rol (admin/user): ");
        String rolNombre = scanner.nextLine();

        // Crear el objeto RegisterRequest
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setNombre(nombre);
        registerRequest.setApellido1(apellido1);
        registerRequest.setApellido2(apellido2);
        registerRequest.setCorreo(correo);
        registerRequest.setTelefono(telefono);
        registerRequest.setDireccion(direccion);
        registerRequest.setContrasena(contrasena);

        // Llamada al método register con dos parámetros
        authService.register(registerRequest, rolNombre);

        System.out.println("Usuario registrado exitosamente.");

        // ----------------- Inicio de Sesión -----------------

        System.out.println("\n=== Inicio de Sesión ===");
        System.out.print("Ingrese su correo: ");
        String loginCorreo = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String loginContrasena = scanner.nextLine();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setCorreo(loginCorreo);
        loginRequest.setContrasena(loginContrasena);

        AuthResponse authResponse = authService.login(loginRequest).getBody();

        if (authResponse.getToken() != null) {
            System.out.println("Inicio de sesión exitoso. Token generado: " + authResponse.getToken());

            // ----------------- Crear Sensores -----------------

            System.out.println("\nIniciando creación de sensores...\n");

            for (int i = 1; i <= 5; i++) {
                SensorMovimientoDTO sensorMovimientoDTO = new SensorMovimientoDTO();
                sensorMovimientoDTO.setNombre("Sensor de Movimiento " + i);
                sensorMovimientoDTO.setDatosMovimiento("Movimiento detectado en sensor " + i);
                sensorMovimientoDTO.setNotificacion(Notificacion.ACTIVADO);
                sensorMovimientoService.create(sensorMovimientoDTO);
            }

            for (int i = 1; i <= 5; i++) {
                SensorAccesoDTO sensorAccesoDTO = new SensorAccesoDTO();
                sensorAccesoDTO.setNombre("Sensor de Acceso " + i);
                sensorAccesoDTO.setDatosAcceso("Acceso registrado en sensor " + i);
                sensorAccesoDTO.setRespuesta(i % 2 == 0); // Alternar acceso permitido/denegado
                sensorAccesoDTO.setNotificacion(Notificacion.ACTIVADO);
                sensorAccesoService.create(sensorAccesoDTO);
            }

            for (int i = 1; i <= 5; i++) {
                SensorTemperaturaDTO sensorTemperaturaDTO = new SensorTemperaturaDTO();
                sensorTemperaturaDTO.setNombre("Sensor de Temperatura " + i);
                sensorTemperaturaDTO.setDatosTemperatura(20.0 + i);
                sensorTemperaturaDTO.setNotificacion(Notificacion.ACTIVADO);
                sensorTemperaturaService.create(sensorTemperaturaDTO);
            }

            System.out.println("Sensores creados correctamente.");

            // Mostrar Sensores de Movimiento
            List<SensorMovimientoDTO> sensoresMovimiento1 = sensorMovimientoService.findAll();
            System.out.println("\nSensores de Movimiento Creados:");
            for (SensorMovimientoDTO sensor : sensoresMovimiento1) {
                System.out.println("ID: " + sensor.getId() + ", Nombre: " + sensor.getNombre() + ", Datos: " + sensor.getDatosMovimiento());
            }

            // Mostrar Sensores de Acceso
            List<SensorAccesoDTO> sensoresAcceso1 = sensorAccesoService.findAll();
            System.out.println("\nSensores de Acceso Creados:");
            for (SensorAccesoDTO sensor : sensoresAcceso1) {
                System.out.println("ID: " + sensor.getId() + ", Nombre: " + sensor.getNombre() + ", Datos: " + sensor.getDatosAcceso() + ", Respuesta: " + (sensor.getRespuesta() ? "Permitido" : "Denegado"));
            }

            // Mostrar Sensores de Temperatura
            List<SensorTemperaturaDTO> sensoresTemperatura1 = sensorTemperaturaService.findAll();
            System.out.println("\nSensores de Temperatura Creados:");
            for (SensorTemperaturaDTO sensor : sensoresTemperatura1) {
                System.out.println("ID: " + sensor.getId() + ", Nombre: " + sensor.getNombre() + ", Temperatura: " + sensor.getDatosTemperatura());
            }

            // Generar eventos y activar sensores durante el tiempo indicado por el usuario
            System.out.print("¿Cuánto tiempo [minutos] deseas tener los sensores activos?: ");
            int minutos = scanner.nextInt();
            System.out.println("Iniciando la generación de eventos concurrentes...");

            eventoService.iniciarGeneracionEventosConcurrentes();
            TimeUnit.MINUTES.sleep(minutos);

            eventoService.detenerGeneracionEventos();
            System.out.println("Se detuvo la generación de eventos después de " + minutos + " minutos.");

        } else {
            System.out.println("Error de inicio de sesión. Verifica las credenciales.");
        }*/
    }
}