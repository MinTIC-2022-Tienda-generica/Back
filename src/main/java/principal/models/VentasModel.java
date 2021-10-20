package principal.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "ventas")


public class VentasModel {

    @Id
    @Column(name = "codigo_venta", unique = true, nullable = false)
    private Long codigoVenta;
    @Column(name = "cedula_cliente", nullable = false)
    private Long cedulaCliente;
    @Column(name = "cedula_usuario", nullable = false)
    private Long cedulaUsuario;
    @Column(name = "ivaventa", nullable = false)
    private Double ivaventa;
    @Column(name = "total_venta", nullable = false)
    private Double totalVenta;
    @Column(name = "valor_venta", nullable = false)
    private Double valorVenta;


    public Long getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(Long codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public Long getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(Long cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public Long getCedulaUsuario() {
        return cedulaUsuario;
    }

    public void setCedulaUsuario(Long cedulaUsuario) {
        this.cedulaUsuario = cedulaUsuario;
    }

    public Double getIvaventa() {
        return ivaventa;
    }

    public void setIvaventa(Double ivaventa) {
        this.ivaventa = ivaventa;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public Double getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(Double valorVenta) {
        this.valorVenta = valorVenta;
    }


    public void copyNotNullValues(VentasModel ventasModel) {
        if (ventasModel.getCodigoVenta() != null) {
            this.setCodigoVenta(ventasModel.getCodigoVenta());
        }
        if (ventasModel.getCedulaCliente() != null) {
            this.setCedulaCliente(ventasModel.getCedulaCliente());
        }
        if (ventasModel.getCedulaUsuario() != null) {
            this.setCedulaUsuario(ventasModel.getCedulaUsuario());
        }
        if (ventasModel.getIvaventa() != null) {
            this.setIvaventa(ventasModel.getIvaventa());
        }
        if (ventasModel.getTotalVenta() != null) {
            this.setTotalVenta(ventasModel.getTotalVenta());
        }
        if (ventasModel.getValorVenta() != null) {
            this.setValorVenta(ventasModel.getValorVenta());
        }
    }

    @Override
    public String toString() {
        return "VentasModel{" +
                "codigoVenta=" + codigoVenta +
                ", cedulaCliente='" + cedulaCliente + '\'' +
                ", cedulaUsuario='" + cedulaUsuario + '\'' +
                ", ivaventa='" + ivaventa + '\'' +
                ", totalVenta='" + totalVenta + '\'' +
                ", valorVenta='" + valorVenta + '\'' +
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
                final VentasModel a = (VentasModel) o;
                // field comparison
                return Objects.equals(a, o);
            }
    }
}
