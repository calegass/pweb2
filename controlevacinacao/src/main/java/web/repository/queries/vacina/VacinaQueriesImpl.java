package web.repository.queries.vacina;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import web.filter.VacinaFilter;
import web.model.Vacina;
import web.repository.pagination.PaginacaoUtil;

import java.util.ArrayList;
import java.util.List;

public class VacinaQueriesImpl implements VacinaQueries {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Vacina> pesquisar(VacinaFilter filtro, Pageable pageable) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Vacina> criteriaQuery = builder.createQuery(Vacina.class);
        Root<Vacina> c = criteriaQuery.from(Vacina.class);
        TypedQuery<Vacina> typedQuery;
        List<Predicate> predicateList = new ArrayList<>();
        Predicate[] predArray;

        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(c.<Long>get("codigo"), filtro.getCodigo()));
        }
        if (StringUtils.hasText(filtro.getNome())) {
            predicateList.add(builder.like(builder.lower(c.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getDescricao())) {
            predicateList.add(builder.like(builder.lower(c.get("descricao")), "%" + filtro.getDescricao().toLowerCase() + "%"));
        }

        predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(c).where(predArray).distinct(true);
        typedQuery = em.createQuery(criteriaQuery);
        typedQuery.setHint("hibernate.query.passDistinctThrough", false);

        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
        PaginacaoUtil.prepararOrdem(c, criteriaQuery, builder, pageable);

        List<Vacina> vacinas = typedQuery.getResultList();

        long totalRegistros = PaginacaoUtil.getTotalRegistros(c, predArray, builder, em);

        Page<Vacina> pagina = new PageImpl<>(vacinas, pageable, totalRegistros);

        return pagina;
    }

}
