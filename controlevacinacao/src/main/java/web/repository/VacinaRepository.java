package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Vacina;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {
}
