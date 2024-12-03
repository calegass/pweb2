package web.controlecarros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.controlecarros.model.Carro;
import web.controlecarros.repository.queries.carro.CarroQueries;

public interface CarroRepository extends JpaRepository<Carro, Long>, CarroQueries {

}
