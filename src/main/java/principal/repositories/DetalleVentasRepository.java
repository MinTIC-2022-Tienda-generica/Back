package principal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import principal.models.DetalleVentasModel;

import java.util.List;

@Repository
public interface DetalleVentasRepository extends JpaRepository<DetalleVentasModel, Long> {

    List<DetalleVentasModel> findAllByCodigoVenta(Long codigoVenta);
}
