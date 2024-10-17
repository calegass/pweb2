package web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.Pessoa;
import web.repository.PessoaRepository;
import web.service.PessoaService;

import java.util.List;


@Controller
@RequestMapping("/pessoas")
public class PessoaController {
    private Logger logger = LoggerFactory.getLogger(PessoaController.class);
    private final PessoaRepository repository;
    private final PessoaService pessoaService;

    public PessoaController(PessoaRepository repository, PessoaService pessoaService) {
        this.repository = repository;
        this.pessoaService = pessoaService;
    }

    @GetMapping("/todas")
    public String todasPessoas(Model model) {
        List<Pessoa> pessoas = repository.findAll();
        model.addAttribute("pessoas", pessoas);
        logger.info("Exibindo todas as pessoas: {}", pessoas);

        return "pessoas/todas";
    }
}
