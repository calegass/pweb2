package web.controlecarros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.controlecarros.model.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {
}
