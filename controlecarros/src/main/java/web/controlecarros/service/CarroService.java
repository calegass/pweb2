package web.controlecarros.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.controlecarros.model.Carro;
import web.controlecarros.repository.CarroRepository;

@Service
@Transactional
public class CarroService {

	private final CarroRepository carroRepository;

	public CarroService(CarroRepository carroRepository) {
		this.carroRepository = carroRepository;
	}

	public void salvar(Carro carro) {
		carroRepository.save(carro);
	}

	public void alterar(Carro carro) {
		carroRepository.save(carro);
	}
}
