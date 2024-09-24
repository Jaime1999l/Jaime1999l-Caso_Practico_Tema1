package io.teamsgroup.caso_1_programacion_concurrente.service.auth;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Credenciales;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Rol;
import io.teamsgroup.caso_1_programacion_concurrente.domain.Usuario;
import io.teamsgroup.caso_1_programacion_concurrente.util.NotFoundException;
import io.teamsgroup.caso_1_programacion_concurrente.util.UserValidationException;
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
            Hibernate.initialize(usuario.getUsuarios());
            if (passwordEncoder.matches(loginRequest.getContrasena(), usuario.getUsuario().getContrasena())) {
                String rol = usuario.getUsuarios().getNombre();
                return ResponseEntity.ok(new AuthResponse("Login exitoso", "FAKE_JWT_TOKEN", rol));
            } else {
                return ResponseEntity.status(401).body(new AuthResponse("Credenciales incorrectas", null, null));
            }
        }

        return ResponseEntity.status(404).body(new AuthResponse("Usuario no encontrado", null, null));
    }

    @Transactional
    public ResponseEntity<AuthResponse> register(RegisterRequest registerRequest, String rolNombre) {
        // Validar entrada
        validateUserInput(registerRequest);

        // Verificar si el usuario ya existe
        if (usuarioRepository.findByCorreo(registerRequest.getCorreo()).isPresent()) {
            return ResponseEntity.status(400).body(new AuthResponse("El usuario ya existe", null, null));
        }

        // Validar rol
        if (!"admin".equalsIgnoreCase(rolNombre) && !"user".equalsIgnoreCase(rolNombre)) {
            return ResponseEntity.status(400).body(new AuthResponse("Rol no válido. Use 'admin' o 'user'.", null, null));
        }

        // Buscar o crear rol
        Optional<Rol> rolOpt = rolRepository.findByNombre(rolNombre);
        Rol rol = rolOpt.orElseGet(() -> {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre(rolNombre);
            return rolRepository.save(nuevoRol);
        });

        // Crear credenciales
        Credenciales credenciales = new Credenciales();
        credenciales.setContrasena(passwordEncoder.encode(registerRequest.getContrasena()));
        credencialesRepository.save(credenciales);

        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registerRequest.getNombre());
        nuevoUsuario.setApellido1(registerRequest.getApellido1());
        nuevoUsuario.setApellido2(registerRequest.getApellido2());
        nuevoUsuario.setCorreo(registerRequest.getCorreo());
        nuevoUsuario.setTelefono(registerRequest.getTelefono());
        nuevoUsuario.setDireccion(registerRequest.getDireccion());
        nuevoUsuario.setUsuario(credenciales);
        nuevoUsuario.setUsuarios(rol);

        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok(new AuthResponse("Usuario registrado con éxito", null, rolNombre));
    }

    private void validateUserInput(RegisterRequest registerRequest) {
        // Validaciones de nombres
        if (registerRequest.getNombre() == null || !registerRequest.getNombre().matches("[A-Za-záéíóúÁÉÍÓÚñÑ]+")) {
            throw new UserValidationException("El nombre debe contener solo letras.");
        }

        if (registerRequest.getApellido1() == null || !registerRequest.getApellido1().matches("[A-Za-záéíóúÁÉÍÓÚñÑ]+")) {
            throw new UserValidationException("El primer apellido  debe contener solo letras.");
        }

        if (registerRequest.getApellido2() == null || !registerRequest.getApellido2().matches("[A-Za-záéíóúÁÉÍÓÚñÑ]+")) {
            throw new UserValidationException("El segundo apellido debe contener solo letras.");
        }

        // Validaciones de correo
        if (registerRequest.getCorreo() == null || !registerRequest.getCorreo().matches("^[\\w-\\.]+@(gmail\\.com|hotmail\\.com|yahoo\\.com)$")) {
            throw new UserValidationException("El correo debe ser de una extensión válida (@gmail.com, @hotmail.com, @yahoo.com).");
        }

        // Validaciones de teléfono
        Integer telefono = registerRequest.getTelefono();
        if (telefono == null || !isValidPhoneNumber(telefono)) {
            throw new UserValidationException("El teléfono debe contener exactamente 9 dígitos numéricos.");
        }

        String contrasena = registerRequest.getContrasena();
        if (!isValidPassword(contrasena)) {
            throw new UserValidationException("La contraseña debe contener al menos una letra, un número y un carácter especial.");
        }
    }

    private boolean isValidPhoneNumber(Integer phoneNumber) {
        String phoneStr = phoneNumber.toString();
        return phoneStr.length() == 9 && phoneStr.chars().allMatch(Character::isDigit);
    }

    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isValidPassword(String password) {
        // Verificar que la contraseña contenga al menos una letra, un número y un carácter especial
        return password != null && password.matches("^(?=.*[A-Za-z])(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,}$");
    }

}
