package io.teamsgroup.caso_1_programacion_concurrente.service.auth;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Credenciales;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Rol;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Usuario;
import io.teamsgroup.caso_1_programacion_concurrente.model.auth.AuthResponse;
import io.teamsgroup.caso_1_programacion_concurrente.model.auth.LoginRequest;
import io.teamsgroup.caso_1_programacion_concurrente.model.auth.RegisterRequest;
import io.teamsgroup.caso_1_programacion_concurrente.repos.RolRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.UsuarioRepository;
import io.teamsgroup.caso_1_programacion_concurrente.repos.CredencialesRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CredencialesRepository credencialesRepository;

    @Autowired
    private RolRepository rolRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(loginRequest.getCorreo());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Forzar la carga del objeto Rol
            Hibernate.initialize(usuario.getUsuarios());
            // Verificar la contraseña
            if (passwordEncoder.matches(loginRequest.getContrasena(), usuario.getUsuario().getContrasena())) {
                // Login exitoso: agrega el rol al AuthResponse
                String rol = usuario.getUsuarios().getNombre(); // Obtener el nombre del rol
                return ResponseEntity.ok(new AuthResponse("Login exitoso", "FAKE_JWT_TOKEN", rol));
            } else {
                return ResponseEntity.status(401).body(new AuthResponse("Credenciales incorrectas", null, null));
            }
        }

        return ResponseEntity.status(404).body(new AuthResponse("Usuario no encontrado", null, null));
    }

    @Transactional
    public ResponseEntity<AuthResponse> register(RegisterRequest registerRequest, String rolNombre) {
        // Verificamos si el usuario ya existe
        if (usuarioRepository.findByCorreo(registerRequest.getCorreo()).isPresent()) {
            return ResponseEntity.status(400).body(new AuthResponse("El usuario ya existe", null, null));
        }

        // Validamos el rol ingresado por el administrador
        if (!"admin".equalsIgnoreCase(rolNombre) && !"user".equalsIgnoreCase(rolNombre)) {
            return ResponseEntity.status(400).body(new AuthResponse("Rol no válido. Use 'admin' o 'user'.", null, null));
        }

        // Buscar si el rol ya existe
        Optional<Rol> rolOpt = rolRepository.findByNombre(rolNombre);
        Rol rol;
        if (rolOpt.isEmpty()) {
            // Si el rol no existe, lo creamos y guardamos
            rol = new Rol();
            rol.setNombre(rolNombre);
            rol = rolRepository.save(rol); // Guardar el rol para que tenga un ID válido
        } else {
            // Si ya existe, lo usamos
            rol = rolOpt.get();
        }

        // Crear las credenciales con la contraseña encriptada
        Credenciales credenciales = new Credenciales();
        credenciales.setContrasena(passwordEncoder.encode(registerRequest.getContrasena()));
        credencialesRepository.save(credenciales); // Guardar las credenciales

        // Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registerRequest.getNombre());
        nuevoUsuario.setApellido1(registerRequest.getApellido1());
        nuevoUsuario.setApellido2(registerRequest.getApellido2());
        nuevoUsuario.setCorreo(registerRequest.getCorreo());
        nuevoUsuario.setTelefono(registerRequest.getTelefono());
        nuevoUsuario.setDireccion(registerRequest.getDireccion());
        nuevoUsuario.setUsuario(credenciales);
        nuevoUsuario.setUsuarios(rol);
        System.out.println("Asignando el rol con ID: " + rol.getId() + " al usuario.");

        usuarioRepository.save(nuevoUsuario); // Guardar el usuario en la base de datos

        // Respuesta exitosa con el rol asignado
        return ResponseEntity.ok(new AuthResponse("Usuario registrado con éxito", null, rolNombre));
    }
}
