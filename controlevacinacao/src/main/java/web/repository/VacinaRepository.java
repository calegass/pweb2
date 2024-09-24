package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Vacina;
import web.repository.queries.vacina.VacinaQueries;

public interface VacinaRepository extends JpaRepository<Vacina, Long>, VacinaQueries {
}
