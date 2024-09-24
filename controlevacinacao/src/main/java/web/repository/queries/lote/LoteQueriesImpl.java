package web.repository.queries.lote;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class LoteQueriesImpl implements LoteQueries {

    @PersistenceContext
    private EntityManager manager;

//	@Override
//	public List<Lote> pesquisar(LoteFilter filtro) {
//
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//		CriteriaQuery<Lote> criteriaQuery = builder.createQuery(Lote.class);
//		Root<Lote> l = criteriaQuery.from(Lote.class);
//		l.fetch("vacina", JoinType.INNER);
//		TypedQuery<Lote> typedQuery;
//		List<Predicate> predicateList = new ArrayList<>();
//
//		if (filtro.getCodigo() != null) {
//			predicateList.add(builder.equal(l.<Long>get("codigo"), filtro.getCodigo()));
//		}
//		if (filtro.getInicioValidade() != null) {
//			predicateList.add(builder.greaterThanOrEqualTo(l.<LocalDate>get("validade"), filtro.getInicioValidade()));
//		}
//		if (filtro.getFimValidade() != null) {
//			predicateList.add(builder.lessThanOrEqualTo(l.<LocalDate>get("validade"), filtro.getFimValidade()));
//		}
//		if (filtro.getMinimoDosesLote() != null) {
//			predicateList.add(builder.greaterThanOrEqualTo(l.<Integer>get("nroDosesDoLote"), filtro.getMinimoDosesLote()));
//		}
//		if (filtro.getMaximoDosesLote() != null) {
//			predicateList.add(builder.lessThanOrEqualTo(l.<Integer>get("nroDosesDoLote"), filtro.getMaximoDosesLote()));
//		}
//		if (filtro.getMinimoDosesAtual() != null) {
//			predicateList.add(builder.greaterThanOrEqualTo(l.<Integer>get("nroDosesAtual"), filtro.getMinimoDosesAtual()));
//		}
//		if (filtro.getMaximoDosesAtual() != null) {
//			predicateList.add(builder.lessThanOrEqualTo(l.<Integer>get("nroDosesAtual"), filtro.getMaximoDosesAtual()));
//		}
//		if (filtro.getVacina() != null) {
//			predicateList.add(builder.equal(l.<Vacina>get("vacina"), filtro.getVacina()));
//		}
//
//		Predicate[] predArray = new Predicate[predicateList.size()];
//		predicateList.toArray(predArray);
//
//		Order order = builder.asc(l.<Long>get("codigo"));
//		criteriaQuery.select(l).where(predArray).distinct(true).orderBy(order);
//		typedQuery = manager.createQuery(criteriaQuery);
//
//		List<Lote> lotes = typedQuery.getResultList();
//
//		return lotes;
//	}

}
