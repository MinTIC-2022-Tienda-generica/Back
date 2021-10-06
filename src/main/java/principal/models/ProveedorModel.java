package principal.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "proveedores")
public class ProveedorModel {
    @Id
    @Column(nullable = false, unique = true)
    private Long nitProveedor;
    @Column(nullable = false)
    private String ciudadProveedor;
    @Column(nullable = false)
    private String direccionProveedor;
    @Column(nullable = false)
    private String nombreProveedor;
    @Column(nullable = false, unique = true)
    private String telefonoProveedor;

    public Long getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(Long nitProveedor) {
        this.nitProveedor = nitProveedor;
    }

    public String getCiudadProveedor() {
        return ciudadProveedor;
    }

    public void setCiudadProveedor(String ciudadProveedor) {
        this.ciudadProveedor = ciudadProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public void copyNotNullValues(ProveedorModel proveedorModel) {
        if (proveedorModel.getNitProveedor() != null) {
            this.setNitProveedor(proveedorModel.getNitProveedor());
        }
        if (proveedorModel.getCiudadProveedor() != null) {
            this.setCiudadProveedor(proveedorModel.getCiudadProveedor());
        }
        if (proveedorModel.getDireccionProveedor() != null) {
            this.setDireccionProveedor(proveedorModel.getDireccionProveedor());
        }
        if (proveedorModel.getNombreProveedor() != null) {
            this.setNombreProveedor(proveedorModel.getNombreProveedor());
        }
        if (proveedorModel.getTelefonoProveedor() != null) {
            this.setTelefonoProveedor(proveedorModel.getTelefonoProveedor());
        }
    }

    @Override
    public String toString() {
        return "ProveedorModel{" +
                "nitProveedor=" + nitProveedor +
                ", ciudadProveedor='" + ciudadProveedor + '\'' +
                ", direccionProveedor='" + direccionProveedor + '\'' +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", telefonoProveedor='" + telefonoProveedor + '\'' +
                '}';
    }
}
