package web.controlevacinacao.repository.queries.profissao;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import web.controlevacinacao.filter.ProfissaoFilter;
import web.controlevacinacao.model.Profissao;

public class ProfissaoQueriesImpl implements ProfissaoQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Profissao> pesquisar(ProfissaoFilter filtro) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Profissao> criteriaQuery = builder.createQuery(Profissao.class);
		Root<Profissao> p = criteriaQuery.from(Profissao.class);
		TypedQuery<Profissao> typedQuery;
		List<Predicate> predicateList = new ArrayList<>();

		if (filtro.getCodigo() != null) {
			predicateList.add(builder.equal(p.<Long>get("codigo"), filtro.getCodigo()));
		}
		if (filtro.getNome() != null && !filtro.getNome().isBlank()) {
			predicateList.add(builder.like(builder.lower(p.<String>get("nome")), "%" +
	                         filtro.getNome().toLowerCase() + "%"));
		}

		Predicate[] predArray = new Predicate[predicateList.size()];
		predicateList.toArray(predArray);

		Order order = builder.asc(p.<Profissao>get("nome"));
		criteriaQuery.select(p).where(predArray).distinct(true).orderBy(order);
		typedQuery = manager.createQuery(criteriaQuery);

		List<Profissao> profissoes = typedQuery.getResultList();

		return profissoes;
	}

}
