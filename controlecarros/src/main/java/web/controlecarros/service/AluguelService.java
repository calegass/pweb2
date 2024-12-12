package web.controlecarros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.controlecarros.model.Aluguel;
import web.controlecarros.model.Carro;
import web.controlecarros.model.Status;
import web.controlecarros.repository.AluguelRepository;
import web.controlecarros.repository.CarroRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AluguelService {
	@Autowired
	private AluguelRepository aluguelRepository;

	@Autowired
	private CarroRepository carroRepository;

	@Transactional
	public Aluguel alugarCarro(Carro carro, String employeeName) {
		if (isCarroAlugado(carro)) {
			throw new IllegalStateException("Carro já está alugado");
		}

		carro.setStatus(Status.ALUGADO);
		carroRepository.save(carro);

		Aluguel rental = new Aluguel(carro, employeeName);

		return aluguelRepository.save(rental);
	}

	@Transactional
	public Aluguel devolverCarro(Carro carro, Double kilometragemAtual) {
		Aluguel rental = aluguelRepository.findByCarroAndDataFinalIsNull(carro)
				.orElseThrow(() -> new IllegalArgumentException("Aluguel não encontrado"));

		if (rental.getFimAluguel() != null) {
			throw new IllegalStateException("Carro já foi devolvido");
		}
		Carro car = rental.getCarro();

		Double kmRodados = car.calcularKmRodados(kilometragemAtual);

		rental.setRentalEnd(LocalDateTime.now());
		rental.setKmRodados(kmRodados);
		aluguelRepository.save(rental);

		car.setStatus(Status.DISPONIVEL);
		carroRepository.save(car);

		return rental;
	}

	public boolean isCarroAlugado(Carro carro) {
		return aluguelRepository.findByCarroAndDataFinalIsNull(carro).isPresent();
	}

	public Aluguel getAluguelByCarro(Carro carro) {
		return aluguelRepository.findByCarroAndDataFinalIsNull(carro)
				.orElseThrow(() -> new IllegalArgumentException("Aluguel não encontrado"));
	}

	public List<Aluguel> getAlugueisFinalizados() {
		return aluguelRepository.findAllByDataFinalIsNotNull();
	}
}
