package web.repository.queries.vacina;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.filter.VacinaFilter;
import web.model.Vacina;

public interface VacinaQueries {

    Page<Vacina> pesquisar(VacinaFilter filtro, Pageable pageable);

}
