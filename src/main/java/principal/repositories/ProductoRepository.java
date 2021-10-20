package principal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import principal.models.ProductoModel;

@Repository

public interface ProductoRepository extends JpaRepository<ProductoModel, Long> {

    ProductoModel findByNombreProducto(String nombre_producto);

    boolean existsByNombreProducto(String nombre_producto);
}
