package com.Orale.Alura.ForoHub.model;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contraseña;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_perfiles", joinColumns = @JoinColumn(name = "id_usuario"))
    @Column(name = "perfil")
    private Set<String> perfiles = new HashSet<>();

    // Getters and Setters

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Set<String> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(Set<String> perfiles) {
        this.perfiles = perfiles;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", perfiles=" + perfiles +
                '}';
    }
}
