package web.repository.queries.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.filter.PessoaFilter;
import web.model.Pessoa;

public interface PessoaQueries {

    Page<Pessoa> pesquisar(PessoaFilter filtro, Pageable pageable);

}
