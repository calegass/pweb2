package web.controlecarros.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.controlecarros.model.Carro;
import web.controlecarros.model.Status;
import web.controlecarros.repository.CarroRepository;

import java.util.List;

@Service
@Transactional
public class CarroService {

	private final CarroRepository carroRepository;

	public CarroService(CarroRepository carroRepository) {
		this.carroRepository = carroRepository;
	}

	public Carro getCarroById(Long id) {
		return carroRepository.findById(id).orElse(null);
	}

	public void salvar(Carro carro) {
		carroRepository.save(carro);
	}

	public void alterar(Carro carro) {
		carroRepository.save(carro);
	}

	public List<Carro> getCarrosDisponiveis() {
		return carroRepository.findByStatusAndIsActive(Status.DISPONIVEL, true);
	}

	public List<Carro> getCarrosAlugados() {
		return carroRepository.findByStatusAndIsActive(Status.ALUGADO, true);
	}
}
