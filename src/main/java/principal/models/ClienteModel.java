package principal.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "clientes")
public class ClienteModel {

    @Id
    @Column(nullable = false, unique = true)
    private Long cedulaCliente;
    @Column(nullable = false)
    private String direccionCliente;
    @Column(nullable = false, unique = true)
    private String emailCliente;
    @Column(nullable = false)
    private String nombreCliente;
    @Column(nullable = false, unique = true)
    private String telefonoCliente;

    public Long getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(long cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    /**
     * Remplaza unicamente los valores no nulos del objeto con los valores correspondietes del parametro
     *
     * @param clienteModel Objeto del cual extraer los datos
     */
    public void copyNotNullValues(ClienteModel clienteModel) {
        if (clienteModel.getDireccionCliente() != null) {
            this.setDireccionCliente(clienteModel.getDireccionCliente());
        }
        if (clienteModel.getEmailCliente() != null) {
            this.setEmailCliente(clienteModel.getEmailCliente());
        }
        if (clienteModel.getNombreCliente() != null) {
            this.setNombreCliente(clienteModel.getNombreCliente());
        }
        if (clienteModel.getTelefonoCliente() != null) {
            this.setTelefonoCliente(clienteModel.getTelefonoCliente());
        }
        if (clienteModel.getCedulaCliente() != null) {
            this.setCedulaCliente(clienteModel.getCedulaCliente());
        }
    }

    @Override
    public String toString() {
        return "ClienteModel{" +
                "cedulaCliente=" + cedulaCliente +
                ", direccionCliente='" + direccionCliente + '\'' +
                ", emailCliente='" + emailCliente + '\'' +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", telefonoCliente='" + telefonoCliente + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else
            // type check and cast
            if (getClass() != o.getClass()) {
                return false;
            } else {
                final ClienteModel a = (ClienteModel) o;
                // field comparison
                return Objects.equals(a, o);
            }
    }
}
