package principal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import principal.models.ClienteModel;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

    ClienteModel findByEmailCliente(String email);

    ClienteModel findByTelefonoCliente(String telefono);

    boolean existsByEmailCliente(String email);

    boolean existsByTelefonoCliente(String email);
}
