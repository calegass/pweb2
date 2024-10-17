package web.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import web.model.Pessoa;
import web.repository.PessoaRepository;

@Service
public class PessoaService {

    private PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public void salvar(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
    }

    @Transactional
    public void alterar(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
    }

    @Transactional
    public void remover(Pessoa pessoa) {
        pessoaRepository.delete(pessoa);
    }
}
