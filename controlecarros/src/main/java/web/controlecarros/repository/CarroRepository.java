package web.controlecarros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.controlecarros.model.Carro;
import web.controlecarros.model.Status;
import web.controlecarros.repository.queries.carro.CarroQueries;
import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, Long>, CarroQueries {

    public List<Carro> findByStatus(Status status);

}
