package principal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import principal.models.ProductosModel;

@Repository

public interface ProductosRepository extends JpaRepository<ProductosModel, Long> {

    ProductosModel findByNombreProducto(String nombre_producto);

    boolean existsByNombreProducto(String nombre_producto);
}
