package web.controlecarros.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.controlecarros.model.Aluguel;
import web.controlecarros.model.Carro;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
    Optional<Aluguel> findByCarroAndDataFinalIsNull(Carro carro);

    List<Aluguel> findAllByDataFinalIsNotNull();

}