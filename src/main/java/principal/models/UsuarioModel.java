package principal.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class UsuarioModel {
    @Id
    @Column(unique = true, nullable = false)
    private Long cedulaUsuario;
    @Column(name = "email_usuario", unique = true, nullable = false)
    private String emailUsuario;
    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;
    @Column(unique = true, nullable = false)
    private String usuario;

    private String password;

    public Long getCedulaUsuario() {
        return cedulaUsuario;
    }

    public void setCedulaUsuario(Long cedula_usuario) {
        this.cedulaUsuario = cedula_usuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Remplaza unicamente los valores no nulos del objeto con los valores correspondietes del parametro
     *
     * @param usuarioModel Objeto del cual extraer los datos
     */
    public void copyNotNullValues(UsuarioModel usuarioModel) {
        if (usuarioModel.getCedulaUsuario() != null) {
            this.setCedulaUsuario(usuarioModel.getCedulaUsuario());
        }
        if (usuarioModel.getEmailUsuario() != null) {
            this.setEmailUsuario(usuarioModel.getEmailUsuario());
        }
        if (usuarioModel.getNombreUsuario() != null) {
            this.setNombreUsuario(usuarioModel.getNombreUsuario());
        }
        if (usuarioModel.getUsuario() != null) {
            this.setUsuario(usuarioModel.getUsuario());
        }
        if (usuarioModel.getPassword() != null) {
            this.setPassword(usuarioModel.getPassword());
        }
    }

    @Override
    public String toString() {
        return "UsuarioModel{" +
                "cedula_usuario=" + cedulaUsuario +
                ", email='" + emailUsuario + '\'' +
                ", nombre='" + nombreUsuario + '\'' +
                ", usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
