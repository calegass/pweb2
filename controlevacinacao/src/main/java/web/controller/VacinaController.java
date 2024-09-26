package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.filter.VacinaFilter;
import web.model.Vacina;
import web.repository.VacinaRepository;
import web.service.VacinaService;

import java.util.List;

@Controller
@RequestMapping("/vacinas")
public class VacinaController {
    //    private Logger logger = LoggerFactory.getLogger(VacinaController.class);
    private final VacinaRepository repository;
    private final VacinaService vacinaService;

    public VacinaController(VacinaRepository repository, VacinaService vacinaService) {
        this.repository = repository;
        this.vacinaService = vacinaService;
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
        vacinaService.salvar(vacina);
        return "redirect:/vacinas/sucesso";
    }

    @GetMapping("/sucesso")
    public String sucessoCadastro(Model model) {
        model.addAttribute("mensagem", "Vacina cadastrada com sucesso!");
        return "mensagem";
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

    @PostMapping("/abriralterar")
    public String abrirAlterar(Vacina vacina) {
        return "vacinas/alterar";
    }

    @PostMapping("/alterar")
    public String alterar(Vacina vacina) {
        vacinaService.alterar(vacina);
        return "redirect:/vacinas/sucesso2";
    }

    @GetMapping("/sucesso2")
    public String sucessoAlteracao(Model model) {
        model.addAttribute("mensagem", "Vacina alterada com sucesso!");
        return "mensagem";
    }
}