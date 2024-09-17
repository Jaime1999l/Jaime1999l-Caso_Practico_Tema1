package io.teamsgroup.caso_1_programacion_concurrente;

import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorAcceso;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorMovimiento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorTemperatura;
import io.teamsgroup.caso_1_programacion_concurrente.model.*;
import io.teamsgroup.caso_1_programacion_concurrente.service.EventoService;
import io.teamsgroup.caso_1_programacion_concurrente.service.SensorAccesoService;
import io.teamsgroup.caso_1_programacion_concurrente.service.SensorMovimientoService;
import io.teamsgroup.caso_1_programacion_concurrente.service.SensorTemperaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.List;

@SpringBootApplication
public class Caso1ProgramacionConcurrenteApplication implements CommandLineRunner {

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
        System.out.println("Iniciando sensores...\n");
        // Creamos 5 sensores de movimiento
        for (int i = 1; i <= 5; i++) {
            SensorMovimientoDTO sensorMovimientoDTO = new SensorMovimientoDTO();
            sensorMovimientoDTO.setNombre("Sensor de Movimiento " + i);
            sensorMovimientoDTO.setDatosMovimiento("Movimiento detectado en sensor " + i);
            sensorMovimientoDTO.setNotificacion(Notificacion.ACTIVADO);
            sensorMovimientoService.create(sensorMovimientoDTO);
        }

        // Creamos 5 sensores de acceso
        for (int i = 1; i <= 5; i++) {
            SensorAccesoDTO sensorAccesoDTO = new SensorAccesoDTO();
            sensorAccesoDTO.setNombre("Sensor de Acceso " + i);
            sensorAccesoDTO.setDatosAcceso("Acceso registrado en sensor " + i);
            sensorAccesoDTO.setRespuesta(i % 2 == 0); // Alternamos entre acceso permitido y denegado
            sensorAccesoDTO.setNotificacion(Notificacion.ACTIVADO);
            sensorAccesoService.create(sensorAccesoDTO);
        }

        // Creamos 5 sensores de temperatura
        for (int i = 1; i <= 5; i++) {
            SensorTemperaturaDTO sensorTemperaturaDTO = new SensorTemperaturaDTO();
            sensorTemperaturaDTO.setNombre("Sensor de Temperatura " + i);
            sensorTemperaturaDTO.setDatosTemperatura(20.0 + i);
            sensorTemperaturaDTO.setNotificacion(Notificacion.ACTIVADO);
            sensorTemperaturaService.create(sensorTemperaturaDTO);
        }

        System.out.println("Sensores creados correctamente.");

        // Mostramos los sensores de movimiento
        List<SensorMovimientoDTO> sensoresMovimiento = sensorMovimientoService.findAll();
        System.out.println("\nSensores de Movimiento Creados:");
        for (SensorMovimientoDTO sensor : sensoresMovimiento) {
            System.out.println("ID: " + sensor.getId() + ", Nombre: " + sensor.getNombre() + ", Datos: " + sensor.getDatosMovimiento());
        }

        // Mostramos los sensores de acceso
        List<SensorAccesoDTO> sensoresAcceso = sensorAccesoService.findAll();
        System.out.println("\nSensores de Acceso Creados:");
        for (SensorAccesoDTO sensor : sensoresAcceso) {
            System.out.println("ID: " + sensor.getId() + ", Nombre: " + sensor.getNombre() + ", Datos: " + sensor.getDatosAcceso() + ", Respuesta: " + (sensor.getRespuesta() ? "Permitido" : "Denegado"));
        }

        // Mostramos los sensores de temperatura
        List<SensorTemperaturaDTO> sensoresTemperatura = sensorTemperaturaService.findAll();
        System.out.println("\nSensores de Temperatura Creados:");
        for (SensorTemperaturaDTO sensor : sensoresTemperatura) {
            System.out.println("ID: " + sensor.getId() + ", Nombre: " + sensor.getNombre() + ", Temperatura: " + sensor.getDatosTemperatura());
        }
        // Preguntamos al usuario por cuanto tiempo quiere que los sensores estén activos
        Scanner scanner = new Scanner(System.in);
        System.out.print("¿Cuánto tiempo [minutos] deseas tener los sensores activos?: ");
        int minutos = scanner.nextInt();

        System.out.println("Iniciando la generación de eventos concurrentes...");

        // Iniciamos la generacion de eventos
        eventoService.iniciarGeneracionEventosConcurrentes();

        // Programamos la finalizacion de los eventos despues del tiempo especificado
        TimeUnit.MINUTES.sleep(minutos);  // Pausamos el hilo principal durante X minutos para recoger eventos

        // Detener la generación de eventos
        eventoService.detenerGeneracionEventos();
        System.out.println("Se detuvo la generación de eventos después de " + minutos + " minutos.");
    }
}


