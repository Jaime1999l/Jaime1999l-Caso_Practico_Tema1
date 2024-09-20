package io.teamsgroup.caso_1_programacion_concurrente.model;

import io.teamsgroup.caso_1_programacion_concurrente.domain.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Usuarios")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class UsuarioDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Importante para que el ID se genere automáticamente
    @Column(nullable = false, updatable = false)
    private Integer id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private String apellido1;

    @NotNull
    @Size(max = 255)
    private String apellido2;

    @NotNull
    @Size(max = 255)
    private String correo;

    @NotNull
    private Integer telefono;

    @Size(max = 255)
    private String direccion;

    @ManyToOne(fetch = FetchType.EAGER) // Relación con la entidad Rol
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Rol usuarios1;

    @NotNull
    private Integer usuarios;

    @NotNull
    @UsuarioUsuarioUnique
    private Integer usuario;

}
