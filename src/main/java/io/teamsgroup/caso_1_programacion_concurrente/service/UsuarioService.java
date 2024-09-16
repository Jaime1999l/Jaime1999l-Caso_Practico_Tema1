package io.teamsgroup.caso_1_programacion_concurrente.service;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Credenciales;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Rol;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorAcceso;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorMovimiento;
import io.teamsgroup.caso_1_programacion_concurrente.domain.SensorTemperatura;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Usuario;
import io.teamsgroup.caso_1_programacion_concurrente.model.UsuarioDTO;
import io.teamsgroup.caso_1_programacion_concurrente.repos.CredencialesRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.RolRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorAccesoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorMovimientoRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.SensorTemperaturaRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.UsuarioRepository;
import io.teamsgroup.caso_1_programacion_concurrente.util.NotFoundException;
import io.teamsgroup.caso_1_programacion_concurrente.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final CredencialesRepository credencialesRepository;
    private final SensorMovimientoRepository sensorMovimientoRepository;
    private final SensorTemperaturaRepository sensorTemperaturaRepository;
    private final SensorAccesoRepository sensorAccesoRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository,
                          final RolRepository rolRepository, final CredencialesRepository credencialesRepository,
                          final SensorMovimientoRepository sensorMovimientoRepository,
                          final SensorTemperaturaRepository sensorTemperaturaRepository,
                          final SensorAccesoRepository sensorAccesoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.credencialesRepository = credencialesRepository;
        this.sensorMovimientoRepository = sensorMovimientoRepository;
        this.sensorTemperaturaRepository = sensorTemperaturaRepository;
        this.sensorAccesoRepository = sensorAccesoRepository;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("id"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final Integer id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getId();
    }

    public void update(final Integer id, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Integer id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido1(usuario.getApellido1());
        usuarioDTO.setApellido2(usuario.getApellido2());
        usuarioDTO.setCorreo(usuario.getCorreo());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setDireccion(usuario.getDireccion());
        usuarioDTO.setUsuarios(usuario.getUsuarios() == null ? null : usuario.getUsuarios().getId());
        usuarioDTO.setUsuario(usuario.getUsuario() == null ? null : usuario.getUsuario().getId());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido1(usuarioDTO.getApellido1());
        usuario.setApellido2(usuarioDTO.getApellido2());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccion(usuarioDTO.getDireccion());
        final Rol rolUsuario = usuarioDTO.getUsuarios() == null
                ? null
                : rolRepository.findById(usuarioDTO.getUsuarios())
                .orElseThrow(() -> new NotFoundException("Rol no encontrado"));
        usuario.setUsuarios(rolUsuario);
        final Credenciales credencialesUsuario = usuarioDTO.getUsuario() == null
                ? null
                : credencialesRepository.findById(usuarioDTO.getUsuario())
                .orElseThrow(() -> new NotFoundException("Credenciales no encontradas"));
        usuario.setUsuario(credencialesUsuario);

        return usuario;
    }


    public boolean usuarioExists(final Integer id) {
        return usuarioRepository.existsById(id);
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SensorMovimiento sensoresMovimientoSensorMovimiento = sensorMovimientoRepository.findFirstBySensoresMovimiento(usuario);
        if (sensoresMovimientoSensorMovimiento != null) {
            referencedWarning.setKey("usuario.sensorMovimiento.sensoresMovimiento.referenced");
            referencedWarning.addParam(sensoresMovimientoSensorMovimiento.getId());
            return referencedWarning;
        }
        final SensorTemperatura sensorTemperaturaSensorTemperatura = sensorTemperaturaRepository.findFirstBySensorTemperatura(usuario);
        if (sensorTemperaturaSensorTemperatura != null) {
            referencedWarning.setKey("usuario.sensorTemperatura.sensorTemperatura.referenced");
            referencedWarning.addParam(sensorTemperaturaSensorTemperatura.getId());
            return referencedWarning;
        }
        final SensorAcceso sensorAccesoSensorAcceso = sensorAccesoRepository.findFirstBySensorAcceso(usuario);
        if (sensorAccesoSensorAcceso != null) {
            referencedWarning.setKey("usuario.sensorAcceso.sensorAcceso.referenced");
            referencedWarning.addParam(sensorAccesoSensorAcceso.getId());
            return referencedWarning;
        }
        return null;
    }

}

