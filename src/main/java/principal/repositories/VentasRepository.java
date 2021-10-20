package principal.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import principal.models.VentasModel;

@Repository
public interface VentasRepository extends JpaRepository<VentasModel, Long> {

    VentasModel findByCodigoVenta(Long codigoVenta);

    boolean existsByCodigoVenta(Long codigoVenta);
}
