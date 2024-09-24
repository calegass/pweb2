package web.repository.queries.vacina;

import web.filter.VacinaFilter;
import web.model.Vacina;

import java.util.List;

public interface VacinaQueries {

    List<Vacina> pesquisar(VacinaFilter filtro);

}
