package web.controlecarros.repository.queries.carro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.controlecarros.filter.CarroFilter;
import web.controlecarros.model.Carro;

public interface CarroQueries {

    Page<Carro> pesquisar(CarroFilter filtro, Pageable pageable);
}
