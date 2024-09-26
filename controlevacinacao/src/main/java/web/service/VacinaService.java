package web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Vacina;
import web.repository.VacinaRepository;

@Service
public class VacinaService {

    private VacinaRepository vacinaRepository;

    public VacinaService(VacinaRepository vacinaRepository) {
        this.vacinaRepository = vacinaRepository;
    }

    @Transactional
    public void salvar(Vacina vacina) {
        vacinaRepository.save(vacina);
    }

    @Transactional
    public void alterar(Vacina vacina) {
        vacinaRepository.save(vacina);
    }

    @Transactional
    public void remover(Vacina vacina) {
        vacinaRepository.delete(vacina);
    }
}