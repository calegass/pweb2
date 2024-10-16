package web.repository.queries.pessoa;

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
import web.filter.PessoaFilter;
import web.model.Pessoa;
import web.model.Status;
import web.repository.pagination.PaginacaoUtil;

import java.util.ArrayList;
import java.util.List;

public class PessoaQueriesImpl implements PessoaQueries {

    @PersistenceContext
    private EntityManager em;

    private final Logger logger = LoggerFactory.getLogger(PessoaQueriesImpl.class);

    public Page<Pessoa> pesquisar(PessoaFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteriaQuery = builder.createQuery(Pessoa.class);
        Root<Pessoa> v = criteriaQuery.from(Pessoa.class);
        TypedQuery<Pessoa> typedQuery;
        List<Predicate> predicateList = new ArrayList<>();
        List<Predicate> predicateListTotal = new ArrayList<>();
        Predicate[] predArray;
        Predicate[] predArrayTotal;
        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(v.<Long>get("codigo"), filtro.getCodigo()));
        }
        if (StringUtils.hasText(filtro.getNome())) {
            predicateList.add(builder.like(builder.lower(v.<String>get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getCpf())) {
            predicateList.add(builder.like(builder.lower(v.<String>get("cpf")),
                    "%" + filtro.getCpf().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getDataNascimento())) {
            predicateList.add(builder.like(builder.lower(v.<String>get("dataNascimento")),
                    "%" + filtro.getDataNascimento().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getProfissao())) {
            predicateList.add(builder.like(builder.lower(v.<String>get("profissao")),
                    "%" + filtro.getProfissao().toLowerCase() + "%"));
        }
        predicateList.add(builder.equal(v.<Status>get("status"), Status.ATIVO));
        predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);
        criteriaQuery.select(v).where(predArray);
        PaginacaoUtil.prepararOrdem(v, criteriaQuery, builder, pageable);
        typedQuery = em.createQuery(criteriaQuery);
        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
        typedQuery.setHint("hibernate.query.passDistinctThrough", false);
        List<Pessoa> pessoas = typedQuery.getResultList();
        logger.info("Calculando o total de registros que o filtro retornará.");
        CriteriaQuery<Long> criteriaQueryTotal = builder.createQuery(Long.class);
        Root<Pessoa> vTotal = criteriaQueryTotal.from(Pessoa.class);
        criteriaQueryTotal.select(builder.count(vTotal));
        if (filtro.getCodigo() != null) {
            predicateListTotal.add(builder.equal(vTotal.<Long>get("codigo"), filtro.getCodigo()));
        }
        if (StringUtils.hasText(filtro.getNome())) {
            predicateListTotal.add(builder.like(builder.lower(vTotal.<String>get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getCpf())) {
            predicateListTotal.add(builder.like(builder.lower(vTotal.<String>get("cpf")),
                    "%" + filtro.getCpf().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getDataNascimento())) {
            predicateListTotal.add(builder.like(builder.lower(vTotal.<String>get("dataNascimento")),
                    "%" + filtro.getDataNascimento().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getProfissao())) {
            predicateListTotal.add(builder.like(builder.lower(vTotal.<String>get("profissao")),
                    "%" + filtro.getProfissao().toLowerCase() + "%"));
        }
        predicateListTotal.add(builder.equal(vTotal.<Status>get("status"), Status.ATIVO));
        predArrayTotal = new Predicate[predicateListTotal.size()];
        predicateListTotal.toArray(predArrayTotal);
        criteriaQueryTotal.where(predArrayTotal);
        TypedQuery<Long> typedQueryTotal = em.createQuery(criteriaQueryTotal);
        long totalPessoas = typedQueryTotal.getSingleResult();
        logger.info("O filtro retornará {} registros.", totalPessoas);
        Page<Pessoa> page = new PageImpl<>(pessoas, pageable, totalPessoas);
        return page;
    }
}
