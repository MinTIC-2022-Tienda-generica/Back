package principal.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "detalle_ventas")
public class DetalleVentasModel {

    @Id
    @Column(name = "codigo_detalle_venta", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoDetalleVenta;
    @Column(name = "cantidad_producto", nullable = false)
    private Integer cantidadProducto;
    @Column(name = "codigo_producto", nullable = false)
    private Long codigoProducto;
    @Column(name = "codigo_venta", nullable = false)
    private Long codigoVenta;
    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;
    @Column(name = "valor_venta", nullable = false)
    private Double valorVenta;
    @Column(name = "valoriva", nullable = false)
    private Double valorIva;

    public Long getCodigoDetalleVenta() {
        return codigoDetalleVenta;
    }

    public void setCodigoDetalleVenta(Long codigoDetalleVenta) {
        this.codigoDetalleVenta = codigoDetalleVenta;
    }

    public Integer getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public Long getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Long codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public Long getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(Long codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(Double valorVenta) {
        this.valorVenta = valorVenta;
    }

    public Double getValorIva() {
        return valorIva;
    }

    public void setValorIva(Double valorIva) {
        this.valorIva = valorIva;
    }

    public void copyNotNullValues(DetalleVentasModel detalleVentasModel) {
        if (detalleVentasModel.getCodigoVenta() != null) {
            this.setCodigoVenta(detalleVentasModel.getCodigoVenta());
        }
        if (detalleVentasModel.getCodigoDetalleVenta() != null) {
            this.setCodigoDetalleVenta(detalleVentasModel.getCodigoDetalleVenta());
        }
        if (detalleVentasModel.getCantidadProducto() != null) {
            this.setCantidadProducto(detalleVentasModel.getCantidadProducto());
        }
        if (detalleVentasModel.getValorIva() != null) {
            this.setValorIva(detalleVentasModel.getValorIva());
        }
        if (detalleVentasModel.getValorVenta() != null) {
            this.setValorVenta(detalleVentasModel.getValorVenta());
        }
        if (detalleVentasModel.getCodigoProducto() != null) {
            this.setCodigoProducto(detalleVentasModel.getCodigoProducto());
        }
        if (detalleVentasModel.getValorTotal() != null) {
            this.setValorTotal(detalleVentasModel.getValorTotal());
        }
    }

    @Override
    public String toString() {
        return "DetalleVentasModel{" +
                "codigoDetalleVenta=" + codigoDetalleVenta +
                ", cantidadProducto=" + cantidadProducto +
                ", codigoProducto=" + codigoProducto +
                ", codigoVenta=" + codigoVenta +
                ", valorTotal=" + valorTotal +
                ", valorVenta=" + valorVenta +
                ", valorIva=" + valorIva +
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
                final DetalleVentasModel a = (DetalleVentasModel) o;
                // field comparison
                return Objects.equals(a, o);
            }
    }
}
