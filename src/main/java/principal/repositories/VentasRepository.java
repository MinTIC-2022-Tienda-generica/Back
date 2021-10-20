package principal.repositories;


import org.springframework.stereotype.Repository;
import principal.models.VentasModel;

@Repository
public interface VentasRepository {

    VentasModel findByCodigoVenta(Long codigoVenta);

    boolean existsByCodigoVenta(Long codigoVenta);
}
