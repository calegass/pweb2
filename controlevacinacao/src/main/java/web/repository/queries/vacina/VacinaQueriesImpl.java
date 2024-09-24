package web.repository.queries.vacina;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;
import web.filter.VacinaFilter;
import web.model.Vacina;

import java.util.ArrayList;
import java.util.List;

public class VacinaQueriesImpl implements VacinaQueries {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Vacina> pesquisar(VacinaFilter filtro) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Vacina> criteriaQuery = builder.createQuery(Vacina.class);
        Root<Vacina> v = criteriaQuery.from(Vacina.class);
        TypedQuery<Vacina> typedQuery;
        List<Predicate> predicateList = new ArrayList<>();
        Predicate[] predArray;

        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(v.<Long>get("codigo"), filtro.getCodigo()));
        }
        if (StringUtils.hasText(filtro.getNome())) {
            predicateList.add(builder.like(builder.lower(v.<String>get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getDescricao())) {
            predicateList.add(builder.like(builder.lower(v.<String>get("descricao")),
                    "%" + filtro.getDescricao().toLowerCase() + "%"));
        }

        predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(v).where(predArray).distinct(true);
        typedQuery = em.createQuery(criteriaQuery);
        typedQuery.setHint("hibernate.query.passDistinctThrough", false);

        List<Vacina> vacinas = typedQuery.getResultList();

        return vacinas;
    }

}
