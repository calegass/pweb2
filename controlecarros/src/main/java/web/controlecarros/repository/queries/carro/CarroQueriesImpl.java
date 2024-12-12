package web.controlecarros.repository.queries.carro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import web.controlecarros.filter.CarroFilter;
import web.controlecarros.model.Carro;
import web.controlecarros.model.Status;
import web.controlecarros.repository.pagination.PaginacaoUtil;

import java.util.ArrayList;
import java.util.List;

public class CarroQueriesImpl implements CarroQueries {

	private static final Logger logger = LoggerFactory.getLogger(CarroQueriesImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<Carro> pesquisar(CarroFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);
		Root<Carro> c = criteriaQuery.from(Carro.class);
		TypedQuery<Carro> typedQuery;
		List<Predicate> predicateList = new ArrayList<>();
		List<Predicate> predicateListTotal = new ArrayList<>();
		Predicate[] predArray;
		Predicate[] predArrayTotal;
		if (filtro.getCodigo() != null) {
			predicateList.add(builder.equal(c.<Long>get("codigo"), filtro.getCodigo()));
		}
		if (filtro.getMarca() != null) {
			predicateList.add(builder.like(builder.lower(c.get("marca")),
					"%" + filtro.getMarca().toLowerCase() + "%"));
		}
		if (filtro.getModelo() != null) {
			predicateList.add(builder.like(builder.lower(c.get("modelo")),
					"%" + filtro.getModelo().toLowerCase() + "%"));
		}
		if (filtro.getCor() != null) {
			predicateList.add(builder.like(builder.lower(c.get("cor")),
					"%" + filtro.getCor().toLowerCase() + "%"));
		}
		if (filtro.getPlaca() != null) {
			predicateList.add(builder.like(builder.lower(c.get("placa")),
					"%" + filtro.getPlaca().toLowerCase() + "%"));
		}
		if (filtro.getAnoDe() != null) {
			predicateList.add(builder.greaterThanOrEqualTo(c.get("ano"),
					filtro.getAnoDe()));
		}
		if (filtro.getAnoAte() != null) {
			predicateList.add(builder.lessThanOrEqualTo(c.get("ano"),
					filtro.getAnoAte()));
		}
		predicateList.add(builder.equal(c.<Boolean>get("isActive"), true));

		predArray = new Predicate[predicateList.size()];
		predicateList.toArray(predArray);
		criteriaQuery.select(c).where(predArray);
		PaginacaoUtil.prepararOrdem(c, criteriaQuery, builder, pageable);
		typedQuery = em.createQuery(criteriaQuery);
		PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
		typedQuery.setHint("hibernate.query.passDistinctThrough", false);
		List<Carro> carros = typedQuery.getResultList();
		logger.info("Calculando o total de registros que o filtro retornará.");
		CriteriaQuery<Long> criteriaQueryTotal = builder.createQuery(Long.class);
		Root<Carro> cTotal = criteriaQueryTotal.from(Carro.class);
		criteriaQueryTotal.select(builder.count(cTotal));
		if (filtro.getCodigo() != null) {
			predicateListTotal.add(builder.equal(cTotal.<Long>get("codigo"), filtro.getCodigo()));
		}
		if (StringUtils.hasText(filtro.getMarca())) {
			predicateListTotal.add(builder.like(builder.lower(cTotal.get("marca")),
					"%" + filtro.getMarca().toLowerCase() + "%"));
		}
		if (StringUtils.hasText(filtro.getModelo())) {
			predicateListTotal.add(builder.like(builder.lower(cTotal.get("modelo")),
					"%" + filtro.getModelo().toLowerCase() + "%"));
		}
		if (StringUtils.hasText(filtro.getCor())) {
			predicateListTotal.add(builder.like(builder.lower(cTotal.get("cor")),
					"%" + filtro.getCor().toLowerCase() + "%"));
		}
		if (StringUtils.hasText(filtro.getPlaca())) {
			predicateListTotal.add(builder.like(builder.lower(cTotal.get("placa")),
					"%" + filtro.getPlaca().toLowerCase() + "%"));
		}
		if (filtro.getAnoDe() != null) {
			predicateListTotal.add(builder.greaterThanOrEqualTo(cTotal.get("ano"),
					filtro.getAnoDe()));
		}
		if (filtro.getAnoAte() != null) {
			predicateListTotal.add(builder.lessThanOrEqualTo(cTotal.get("ano"),
					filtro.getAnoAte()));
		}
		predicateListTotal.add(builder.equal(cTotal.<Status>get("status"), Status.DISPONIVEL));
		predicateListTotal.add(builder.equal(cTotal.<Boolean>get("isActive"), true));

		predArrayTotal = new Predicate[predicateListTotal.size()];
		predicateListTotal.toArray(predArrayTotal);
		criteriaQueryTotal.where(predArrayTotal);
		TypedQuery<Long> typedQueryTotal = em.createQuery(criteriaQueryTotal);
		Long totalCarros = typedQueryTotal.getSingleResult();
		logger.info("O filtro retornará {} registros.", totalCarros);
		Page<Carro> page = new PageImpl<>(carros, pageable, totalCarros);
		return page;
	}
}
