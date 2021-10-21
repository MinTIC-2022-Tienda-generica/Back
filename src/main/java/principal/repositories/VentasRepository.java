package principal.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import principal.models.VentasModel;

import java.util.List;

@Repository
public interface VentasRepository extends JpaRepository<VentasModel, Long> {

    VentasModel findByCodigoVenta(Long codigoVenta);

    boolean existsByCodigoVenta(Long codigoVenta);

    List<VentasModel> findByCedulaCliente(Long cedulaCliente);
}
