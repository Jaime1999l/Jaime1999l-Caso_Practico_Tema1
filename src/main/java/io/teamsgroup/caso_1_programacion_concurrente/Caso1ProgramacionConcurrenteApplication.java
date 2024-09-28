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

        // Registro de usuarios
        registrarNuevoUsuario(
                "Administrador", "ApellidoA", "ApellidoB", "admin@gmail.com", 123456789,
                "Calle Principal 123", "a12345_67", "admin"
        );

        registrarNuevoUsuario(
                "Usuario", "ApellidoAA", "ApellidoBB", "usuario@gmail.com", 987654321,
                "Calle Secundaria 456", "a12345_67", "user"
        );

        mostrarUsuarios();

        // Crear sensores con los tokens generados
        crearSensoresMovimiento();
        crearSensoresAcceso();
        crearSensoresTemperatura();

        // Mostrar sensores usando los tokens generados
        mostrarSensores();

        // Generar eventos concurrentes
        eventoService.iniciarGeneracionEventosConcurrentes();
        TimeUnit.MINUTES.sleep(5);
        eventoService.detenerGeneracionEventos();

        System.out.println("Caso de uso finalizado.");
        System.out.println("Eventos generados: ");
        mostrarTodosLosEventos();
    }

    private void mostrarUsuarios() {
        System.out.println("Usuarios registrados:");
        List<UsuarioDTO> usuarios = usuarioService.findAll();
        for (UsuarioDTO usuario : usuarios) {
            System.out.println("ID: " + usuario.getId() + ", Nombre: " + usuario.getNombre() + ", Correo: " + usuario.getCorreo());
        }
    }

    private void limpiarBaseDeDatos() {
        System.out.println("Limpiando base de datos...");

        // Eliminar todos los eventos
        eventoService.deleteAll();

        // Eliminar sensores de movimiento
        for (int i = 1; i <= 5; i++) {
            String tokenMovimiento = generatePredictableToken("TOKEN_MOVIMIENTO", i);
            sensorMovimientoService.findAll(tokenMovimiento).forEach(sensor -> sensorMovimientoService.delete(sensor.getId()));
        }

        // Eliminar sensores de acceso
        for (int i = 1; i <= 5; i++) {
            String tokenAcceso = generatePredictableToken("TOKEN_ACCESO", i);
            sensorAccesoService.findAll(tokenAcceso).forEach(sensor -> sensorAccesoService.delete(sensor.getId()));
        }

        // Eliminar sensores de temperatura
        for (int i = 1; i <= 5; i++) {
            String tokenTemperatura = generatePredictableToken("TOKEN_TEMPERATURA", i);
            sensorTemperaturaService.findAll(tokenTemperatura).forEach(sensor -> sensorTemperaturaService.delete(sensor.getId()));
        }

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
    private String registrarNuevoUsuario(String nombre, String apellido1, String apellido2, String correo,
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

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setCorreo(correo);
        loginRequest.setContrasena(contrasena);

        // Autenticación para obtener el token
        return Objects.requireNonNull(authService.login(loginRequest).getBody()).getToken();
    }

    private void crearSensoresMovimiento() {
        for (int i = 1; i <= 5; i++) {
            SensorMovimientoDTO sensorMovimientoDTO = new SensorMovimientoDTO();
            sensorMovimientoDTO.setNombre("Sensor de Movimiento " + i);
            sensorMovimientoDTO.setDatosMovimiento("Movimiento detectado en sensor " + i);
            sensorMovimientoDTO.setNotificacion(Notificacion.ACTIVADO);
            sensorMovimientoDTO.setToken(generatePredictableToken("TOKEN_MOVIMIENTO", i));
            System.out.println("Creando sensor de movimiento con token: " + sensorMovimientoDTO.getToken());
            sensorMovimientoService.create(sensorMovimientoDTO);
        }
    }

    private void crearSensoresAcceso() {
        for (int i = 1; i <= 5; i++) {
            SensorAccesoDTO sensorAccesoDTO = new SensorAccesoDTO();
            sensorAccesoDTO.setNombre("Sensor de Acceso " + i);
            sensorAccesoDTO.setDatosAcceso("Acceso registrado en sensor " + i);
            sensorAccesoDTO.setRespuesta(i % 2 == 0);
            sensorAccesoDTO.setNotificacion(Notificacion.ACTIVADO);
            sensorAccesoDTO.setToken(generatePredictableToken("TOKEN_ACCESO", i));
            System.out.println("Creando sensor de acceso con token: " + sensorAccesoDTO.getToken());
            sensorAccesoService.create(sensorAccesoDTO);
        }
    }

    private void crearSensoresTemperatura() {
        for (int i = 1; i <= 5; i++) {
            SensorTemperaturaDTO sensorTemperaturaDTO = new SensorTemperaturaDTO();
            sensorTemperaturaDTO.setNombre("Sensor de Temperatura " + i);
            sensorTemperaturaDTO.setDatosTemperatura(20.0 + i);
            sensorTemperaturaDTO.setNotificacion(Notificacion.ACTIVADO);
            sensorTemperaturaDTO.setToken(generatePredictableToken("TOKEN_TEMPERATURA", i));
            System.out.println("Creando sensor de temperatura con token: " + sensorTemperaturaDTO.getToken());
            sensorTemperaturaService.create(sensorTemperaturaDTO);
        }
    }


    /**
     * Mostrar todos los sensores creados en la base de datos.
     */
    /**
     * Mostrar todos los sensores creados en la base de datos usando tokens predecibles.
     */
    private void mostrarSensores() {
        System.out.println("Mostrando sensores creados:");

        // Mostrar Sensores de Movimiento
        for (int i = 1; i <= 5; i++) {
            String tokenMovimiento = generatePredictableToken("TOKEN_MOVIMIENTO", i);
            List<SensorMovimientoDTO> sensoresMovimiento = sensorMovimientoService.findAll(tokenMovimiento);
            System.out.println("\nSensores de Movimiento (Token: " + tokenMovimiento + "):");
            for (SensorMovimientoDTO sensor : sensoresMovimiento) {
                System.out.println("ID: " + sensor.getId() + ", Nombre: " + sensor.getNombre() +
                        ", Datos: " + sensor.getDatosMovimiento() + ", Token: " + sensor.getToken());
            }
        }

        // Mostrar Sensores de Acceso
        for (int i = 1; i <= 5; i++) {
            String tokenAcceso = generatePredictableToken("TOKEN_ACCESO", i);
            List<SensorAccesoDTO> sensoresAcceso = sensorAccesoService.findAll(tokenAcceso);
            System.out.println("\nSensores de Acceso (Token: " + tokenAcceso + "):");
            for (SensorAccesoDTO sensor : sensoresAcceso) {
                System.out.println("ID: " + sensor.getId() + ", Nombre: " + sensor.getNombre() +
                        ", Datos: " + sensor.getDatosAcceso() + ", Respuesta: " + (sensor.getRespuesta() ? "Permitido" : "Denegado") +
                        ", Token: " + sensor.getToken());
            }
        }

        // Mostrar Sensores de Temperatura
        for (int i = 1; i <= 5; i++) {
            String tokenTemperatura = generatePredictableToken("TOKEN_TEMPERATURA", i);
            List<SensorTemperaturaDTO> sensoresTemperatura = sensorTemperaturaService.findAll(tokenTemperatura);
            System.out.println("\nSensores de Temperatura (Token: " + tokenTemperatura + "):");
            for (SensorTemperaturaDTO sensor : sensoresTemperatura) {
                System.out.println("ID: " + sensor.getId() + ", Nombre: " + sensor.getNombre() +
                        ", Temperatura: " + sensor.getDatosTemperatura() + ", Token: " + sensor.getToken());
            }
        }
    }


    private String generatePredictableToken(String tipo, int index) {
        // El token se genera utilizando un patrón fijo basado en el tipo y el índice.
        return tipo + "_Sensor_" + index;
    }

    private void mostrarTodosLosEventos() {
        System.out.println("\nEventos Registrados:");
        List<EventoDTO> eventos = eventoService.getAllEventos();
        if (eventos.isEmpty()) {
            System.out.println("No hay eventos registrados.");
        } else {
            for (EventoDTO evento : eventos) {
                System.out.println("ID: " + evento.getId() + ", Nombre: " + evento.getNombre() +
                        ", Datos: " + evento.getDatos() + ", Token: " + evento.getToken() +
                        ", Sensor Movimiento ID: " + evento.getEventosMovimiento() +
                        ", Sensor Acceso ID: " + evento.getEventosAcceso() +
                        ", Sensor Temperatura ID: " + evento.getEventosTemperatura());
            }
        }
    }

}




