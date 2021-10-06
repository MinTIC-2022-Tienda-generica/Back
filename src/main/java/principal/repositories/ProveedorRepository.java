package principal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import principal.models.ProveedorModel;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorModel, Long> {

    ProveedorModel findByTelefonoProveedor(String telefonoProveedor);

    boolean existsByTelefonoProveedor(String telefonoProveedor);
}
