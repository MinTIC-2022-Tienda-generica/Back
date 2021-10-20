package principal.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "productos")
public class ProductoModel {


    @Id
    @Column(name = "codigo_producto", unique = true, nullable = false)
    private Long codigoProducto;
    @Column(name = "nombre_producto", unique = true, nullable = false)
    private String nombreProducto;
    @Column(name = "nit_proveedor", nullable = false)
    private Long nitProveedor;
    @Column(name = "precio_compra", nullable = false)
    private Long precioCompra;
    @Column(name = "ivacompra", nullable = false)
    private Integer ivaCompra;
    @Column(name = "precio_venta", nullable = false)
    private Long precioVenta;

    public Long getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Long codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Long getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(Long nitProveedor) {
        this.nitProveedor = nitProveedor;
    }

    public Long getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Long precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Integer getIvaCompra() {
        return ivaCompra;
    }

    public void setIvaCompra(Integer ivaCompra) {
        this.ivaCompra = ivaCompra;
    }

    public Long getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Long precioVenta) {
        this.precioVenta = precioVenta;
    }

    /**
     * Remplaza unicamente los valores no nulos del objeto con los valores correspondietes del parametro
     *
     * @param productoModel Objeto del cual extraer los datos
     */
    public void copyNotNullValues(ProductoModel productoModel) {
        if (productoModel.getCodigoProducto() != null) {
            this.setCodigoProducto(productoModel.getCodigoProducto());
        }
        if (productoModel.getNombreProducto() != null) {
            this.setNombreProducto(productoModel.getNombreProducto());
        }
        if (productoModel.getNitProveedor() != null) {
            this.setNitProveedor(productoModel.getNitProveedor());
        }
        if (productoModel.getPrecioCompra() != null) {
            this.setPrecioCompra(productoModel.getPrecioCompra());
        }
        if (productoModel.getIvaCompra() != null) {
            this.setIvaCompra(productoModel.getIvaCompra());
        }
        if (productoModel.getPrecioVenta() != null) {
            this.setPrecioVenta(productoModel.getPrecioVenta());
        }
    }

    @Override
    public String toString() {
        return "ProductosModel{" +
                "codigoProducto=" + codigoProducto +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", nitProveedor='" + nitProveedor + '\'' +
                ", precioCompra='" + precioCompra + '\'' +
                ", ivaCompra='" + ivaCompra + '\'' +
                ", precioVenta='" + precioVenta + '\'' +
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
                final ProductoModel a = (ProductoModel) o;
                // field comparison
                return Objects.equals(a, o);
            }
    }
}
