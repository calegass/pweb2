package web.controlevacinacao.repository.queries.pessoa;

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
import web.controlevacinacao.filter.PessoaFilter;
import web.controlevacinacao.model.Pessoa;
import web.controlevacinacao.model.Status;
import web.controlevacinacao.repository.pagination.PaginacaoUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PessoaQueriesImpl implements PessoaQueries {

    private static final Logger logger = LoggerFactory.getLogger(PessoaQueriesImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Pessoa> pesquisar(PessoaFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteriaQuery = builder.createQuery(Pessoa.class);
        Root<Pessoa> p = criteriaQuery.from(Pessoa.class);
        TypedQuery<Pessoa> typedQuery;
        List<Predicate> predicateList = new ArrayList<>();
        List<Predicate> predicateListTotal = new ArrayList<>();
        Predicate[] predArray;
        Predicate[] predArrayTotal;
        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(p.<Long>get("codigo"), filtro.getCodigo()));
        }
        if (StringUtils.hasText(filtro.getNome())) {
            predicateList.add(builder.like(builder.lower(p.<String>get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getCpf())) {
            predicateList.add(builder.like(builder.lower(p.<String>get("cpf")),
                    "%" + filtro.getCpf().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getProfissao())) {
            predicateList.add(builder.like(builder.lower(p.<String>get("profissao")),
                    "%" + filtro.getProfissao().toLowerCase() + "%"));
        }
        if (filtro.getNascimentoDe() != null) {
            predicateList.add(builder.greaterThanOrEqualTo(p.<LocalDate>get("dataNascimento"),
                    filtro.getNascimentoDe()));
        }
        if (filtro.getNascimentoAte() != null) {
            predicateList.add(builder.lessThanOrEqualTo(p.<LocalDate>get("dataNascimento"),
                    filtro.getNascimentoAte()));
        }
        predicateList.add(builder.equal(p.<Status>get("status"), Status.ATIVO));

        predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);
        criteriaQuery.select(p).where(predArray);
        PaginacaoUtil.prepararOrdem(p, criteriaQuery, builder, pageable);
        typedQuery = em.createQuery(criteriaQuery);
        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
        typedQuery.setHint("hibernate.query.passDistinctThrough", false);
        List<Pessoa> pessoas = typedQuery.getResultList();
        logger.info("Calculando o total de registros que o filtro retornará.");
        CriteriaQuery<Long> criteriaQueryTotal = builder.createQuery(Long.class);
        Root<Pessoa> pTotal = criteriaQueryTotal.from(Pessoa.class);
        criteriaQueryTotal.select(builder.count(pTotal));
        if (filtro.getCodigo() != null) {
            predicateListTotal.add(builder.equal(pTotal.<Long>get("codigo"), filtro.getCodigo()));
        }
        if (StringUtils.hasText(filtro.getNome())) {
            predicateListTotal.add(builder.like(builder.lower(pTotal.<String>get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getCpf())) {
            predicateListTotal.add(builder.like(builder.lower(pTotal.<String>get("cpf")),
                    "%" + filtro.getCpf().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(filtro.getProfissao())) {
            predicateListTotal.add(builder.like(builder.lower(pTotal.<String>get("profissao")),
                    "%" + filtro.getProfissao().toLowerCase() + "%"));
        }
        if (filtro.getNascimentoDe() != null) {
            predicateListTotal.add(builder.greaterThanOrEqualTo(pTotal.<LocalDate>get("dataNascimento"),
                    filtro.getNascimentoDe()));
        }
        if (filtro.getNascimentoAte() != null) {
            predicateListTotal.add(builder.lessThanOrEqualTo(pTotal.<LocalDate>get("dataNascimento"),
                    filtro.getNascimentoAte()));
        }
        predicateList.add(builder.equal(pTotal.<Status>get("status"), Status.ATIVO));

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
