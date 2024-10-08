package web.controlevacinacao.repository.queries.profissao;

import java.util.List;

import web.controlevacinacao.filter.ProfissaoFilter;
import web.controlevacinacao.model.Profissao;

public interface ProfissaoQueries {

	List<Profissao> pesquisar(ProfissaoFilter filtro);
	
}
