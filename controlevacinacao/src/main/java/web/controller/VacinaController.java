package web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.filter.VacinaFilter;
import web.model.Vacina;
import web.repository.VacinaRepository;

import java.util.List;

@Controller
@RequestMapping("/vacinas")
public class VacinaController {
    private Logger logger = LoggerFactory.getLogger(VacinaController.class);
    private final VacinaRepository repository;

    public VacinaController(VacinaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todas")
    public String todasVacinas(Model model) {
        List<Vacina> vacinas = repository.findAll();
        model.addAttribute("vacinas", vacinas);

//        logger.info("Exibindo todas as vacinas: {}", vacinas);

        return "vacinas/todas";
    }

    @GetMapping("/nova")
    public String abrirCadastroVacina(Model model) {
        Vacina vacina = new Vacina();
        model.addAttribute("vacina", vacina);

        return "vacinas/nova";
    }

    @PostMapping("/nova")
    public String cadastrarVacina(Vacina vacina) {
        repository.save(vacina);

        return "redirect:/vacinas/sucesso";
    }

    @GetMapping("/sucesso")
    public String sucessoCadastro() {
        return "vacinas/sucesso";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(Model model) {
        model.addAttribute("vacinaFilter", new VacinaFilter());
        return "vacinas/pesquisar";
    }

    @PostMapping("/pesquisar")
    public String pesquisar(VacinaFilter filter, Model model) {
        List<Vacina> vacinas = repository.pesquisar(filter);
        model.addAttribute("vacinas", vacinas);

        return "vacinas/vacinas";
    }
}