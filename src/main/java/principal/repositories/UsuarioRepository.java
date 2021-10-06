package principal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import principal.models.UsuarioModel;

@Repository
   
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    UsuarioModel findByUsuario(String usuario);

    UsuarioModel findByEmailUsuario(String email_usuario);

    boolean existsByUsuario(String usuario);

    boolean existsByEmailUsuario(String email_usuario);
}
