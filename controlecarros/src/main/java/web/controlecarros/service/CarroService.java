package web.controlecarros.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.controlecarros.model.Carro;
import web.controlecarros.model.Status;
import web.controlecarros.repository.CarroRepository;

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
		return carroRepository.findByStatus(Status.DISPONIVEL);
	}

	public List<Carro> getCarrosAlugados() {
		return carroRepository.findByStatus(Status.ALUGADO);
	}
}
