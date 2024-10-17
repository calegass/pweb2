package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Pessoa;
import web.repository.queries.pessoa.PessoaQueries;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaQueries {
}
