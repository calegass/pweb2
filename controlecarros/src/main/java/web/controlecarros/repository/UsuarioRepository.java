package web.controlecarros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.controlecarros.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByNomeUsuarioIgnoreCase(String nomeUsuario);

}
