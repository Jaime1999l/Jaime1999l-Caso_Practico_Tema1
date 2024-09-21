package io.teamsgroup.caso_1_programacion_concurrente;

import io.teamsgroup.caso_1_programacion_concurrente.model.*;
import io.teamsgroup.caso_1_programacion_concurrente.model.auth.LoginRequest;
import io.teamsgroup.caso_1_programacion_concurrente.model.auth.RegisterRequest;
import io.teamsgroup.caso_1_programacion_concurrente.service.auth.AuthService;
import io.teamsgroup.caso_1_programacion_concurrente.service.sensor.*;
import io.teamsgroup.caso_1_programacion_concurrente.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Caso1ProgramacionConcurrenteApplication implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;
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

        // Registro de un nuevo usuario admin y user
        registrarNuevoUsuario(
                "Administrador", "Ap", "Ap2", "admin@example.com", 123456789,
                "Calle Concurrente 123", "12345", "admin"
        );

        registrarNuevoUsuario(
                "Usuario", "Ap1", "Ap12", "usuario@example.com", 987654321,
                "Calle Evento 123", "12345", "user"
        );

        System.out.println("Usuarios registrados exitosamente.");

        // Mostrar los usuarios registrados
        mostrarUsuarios();

        System.out.println("\nIniciando creación de sensores...\n");

        // Crear sensores
        crearSensoresMovimiento();
        crearSensoresAcceso();
        crearSensoresTemperatura();

        System.out.println("Sensores creados correctamente.");

        // Mostrar sensores creados
        mostrarSensores();

        // Generar eventos concurrentes
        eventoService.iniciarGeneracionEventosConcurrentes();
        TimeUnit.MINUTES.sleep(15); // Ajustar el tiempo de ejecución según sea necesario
        eventoService.detenerGeneracionEventos();
    }

    private void mostrarUsuarios() {
        System.out.println("Usuarios registrados:");
        List<UsuarioDTO> usuarios = usuarioService.findAll();
        for (UsuarioDTO usuario : usuarios) {
            System.out.println("ID: " + usuario.getId() + ", Nombre: " + usuario.getNombre() + ", Correo: " + usuario.getCorreo());
        }
    }

    /**
     * Limpiar la base de datos eliminando eventos y sensores.
     */
    private void limpiarBaseDeDatos() {
        System.out.println("Limpiando base de datos...");

        // Eliminar todos los eventos
        eventoService.deleteAll();

        // Eliminar todos los sensores de movimiento
        sensorMovimientoService.findAll().forEach(sensor -> sensorMovimientoService.delete(sensor.getId()));

        // Eliminar todos los sensores de acceso
        sensorAccesoService.findAll().forEach(sensor -> sensorAccesoService.delete(sensor.getId()));

        // Eliminar todos los sensores de temperatura
        sensorTemperaturaService.findAll().forEach(sensor -> sensorTemperaturaService.delete(sensor.getId()));

        // Eliminar todos los usuarios
        usuarioService.findAll().forEach(usuario -> usuarioService.delete(usuario.getId()));

        System.out.println("Base de datos vaciada.\n");
    }

    /**
     * Registra un nuevo usuario en la base de datos y devuelve un token.
     *
     * @param nombre     Nombre del usuario.
     * @param apellido1  Primer apellido del usuario.
     * @param apellido2  Segundo apellido del usuario.
     * @param correo     Correo electrónico del usuario.
     * @param telefono   Número de teléfono del usuario.
     * @param direccion  Dirección del usuario (opcional).
     * @param contrasena Contraseña del usuario.
     * @param rolNombre  Rol del usuario (admin/user).
     * @return Token del usuario registrado.
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
        registerRequest.setRole(rolNombre);

        // Llamada al metodo register con dos parametros
        authService.register(registerRequest, rolNombre);

        System.out.println("Usuario registrado con nombre: " + nombre + " y rol: " + rolNombre);
    }

    /**
     * Crear sensores de movimiento.
     */
    private void crearSensoresMovimiento() {
        for (int i = 1; i <= 5; i++) {
            SensorMovimientoDTO sensorMovimientoDTO = new SensorMovimientoDTO();
            sensorMovimientoDTO.setNombre("Sensor de Movimiento " + i);
            sensorMovimientoDTO.setDatosMovimiento("Movimiento detectado en sensor " + i);
            sensorMovimientoDTO.setNotificacion(Notificacion.ACTIVADO);
            sensorMovimientoService.create(sensorMovimientoDTO);
        }
    }

    /**
     * Crear sensores de acceso.
     */
    private void crearSensoresAcceso() {
        for (int i = 1; i <= 5; i++) {
            SensorAccesoDTO sensorAccesoDTO = new SensorAccesoDTO();
            sensorAccesoDTO.setNombre("Sensor de Acceso " + i);
            sensorAccesoDTO.setDatosAcceso("Acceso registrado en sensor " + i);
            sensorAccesoDTO.setRespuesta(i % 2 == 0); // Alternar acceso permitido/denegado
            sensorAccesoDTO.setNotificacion(Notificacion.ACTIVADO);
            sensorAccesoService.create(sensorAccesoDTO);
        }
    }

    /**
     * Crear sensores de temperatura.
     */
    private void crearSensoresTemperatura() {
        for (int i = 1; i <= 5; i++) {
            SensorTemperaturaDTO sensorTemperaturaDTO = new SensorTemperaturaDTO();
            sensorTemperaturaDTO.setNombre("Sensor de Temperatura " + i);
            sensorTemperaturaDTO.setDatosTemperatura(20.0 + i);
            sensorTemperaturaDTO.setNotificacion(Notificacion.ACTIVADO);
            sensorTemperaturaService.create(sensorTemperaturaDTO);
        }
    }

    /**
     * Mostrar todos los sensores creados en la base de datos.
     */
    private void mostrarSensores() {
        // Mostrar Sensores de Movimiento
        List<SensorMovimientoDTO> sensoresMovimiento = sensorMovimientoService.findAll();
        System.out.println("\nSensores de Movimiento Creados:");
        for (SensorMovimientoDTO sensor : sensoresMovimiento) {
            System.out.println("ID: " + sensor.getId() + ", Nombre: " + sensor.getNombre() + ", Datos: " + sensor.getDatosMovimiento());
        }

        // Mostrar Sensores de Acceso
        List<SensorAccesoDTO> sensoresAcceso = sensorAccesoService.findAll();
        System.out.println("\nSensores de Acceso Creados:");
        for (SensorAccesoDTO sensor : sensoresAcceso) {
            System.out.println("ID: " + sensor.getId() + ", Nombre: " + sensor.getNombre() + ", Datos: " + sensor.getDatosAcceso() + ", Respuesta: " + (sensor.getRespuesta() ? "Permitido" : "Denegado"));
        }

        // Mostrar Sensores de Temperatura
        List<SensorTemperaturaDTO> sensoresTemperatura = sensorTemperaturaService.findAll();
        System.out.println("\nSensores de Temperatura Creados:");
        for (SensorTemperaturaDTO sensor : sensoresTemperatura) {
            System.out.println("ID: " + sensor.getId() + ", Nombre: " + sensor.getNombre() + ", Temperatura: " + sensor.getDatosTemperatura());
        }
    }

}



