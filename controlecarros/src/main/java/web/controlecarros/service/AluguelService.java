package web.controlecarros.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.controlecarros.repository.CarroRepository;
import web.controlecarros.model.Aluguel;
import web.controlecarros.model.Carro;
import web.controlecarros.model.Status;
import web.controlecarros.repository.AluguelRepository;

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
    public Aluguel returnCar(Long rentalId) {
        Aluguel rental = aluguelRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Aluguel não encontrado"));

        if (rental.getFimAluguel() != null) {
            throw new IllegalStateException("Carro já foi devolvido");
        }

        rental.setRentalEnd(LocalDateTime.now());
        aluguelRepository.save(rental);

        Carro car = rental.getCarro();
        car.setStatus(Status.DISPONIVEL);
        carroRepository.save(car);

        return rental;
    }

    public boolean isCarroAlugado(Carro carro) {
        return aluguelRepository.findByCarroAndDataFinalIsNull(carro).isPresent();
    }

    // public List<Aluguel> getAlugueisAtivos() {
    // return aluguelRepository.findAlugueisAtivos();
    // }
}
