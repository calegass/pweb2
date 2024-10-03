package web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.filter.VacinaFilter;
import web.model.Status;
import web.model.Vacina;
import web.pagination.PageWrapper;
import web.repository.VacinaRepository;
import web.service.VacinaService;

import java.util.List;

@Controller
@RequestMapping("/vacinas")
public class VacinaController {
    private Logger logger = LoggerFactory.getLogger(VacinaController.class);
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

    @GetMapping("/abrirpesquisar")
    public String pesquisar(Model model) {
        model.addAttribute("vacinaFilter", new VacinaFilter());
        return "vacinas/pesquisar";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(VacinaFilter filter, Model model,
                            @PageableDefault(size = 7)
                            @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
                            HttpServletRequest request) {
        Page<Vacina> pagina = repository.pesquisar(filter, pageable);
        logger.info("Exibindo a página de vacinas pesquisadas: {}", pagina);
        PageWrapper<Vacina> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
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

    @PostMapping("/abrirremover")
    public String abrirRemover(Vacina vacina, Model model) {
        model.addAttribute("vacina", vacina);
        return "vacinas/confirmarremocao";
    }

    @PostMapping("/remover")
    public String remover(Vacina vacina) {
        vacina.setStatus(Status.INATIVO);
        vacinaService.alterar(vacina);
        return "redirect:/vacinas/sucesso3";
    }

    @GetMapping("/sucesso3")
    public String sucessoRemocao(Model model) {
        model.addAttribute("mensagem", "Vacina removida com sucesso!");
        return "mensagem";
    }

    @GetMapping(value = "/nova", headers = "HX-Request")
    public String abrirCadastroVacinaHTMX(Vacina vacina) {
        return "vacinas/nova :: formulario";
    }

    @PostMapping(value = "/nova", headers = "HX-Request")
    public String cadastrarVacinaHTMX(Vacina vacina, HttpServletResponse response) {
        vacinaService.salvar(vacina);
        response.setHeader("HX-Location", "{\"path\":\"/vacinas/sucesso\", \"target\":\"#main\"}");
        return "mensagem";
    }

    @GetMapping(value = "/sucesso", headers = "HX-Request")
    public String abrirMensagemSucessoHTMX(Model model) {
        model.addAttribute("mensagem", "Vacina cadastrada com sucesso");
        return "mensagem :: texto";
    }

    @GetMapping(value = "/abrirpesquisar", headers = "HX-Request")
    public String abrirPaginaPesquisaHTMX() {
        return "vacinas/pesquisar :: formulario";
    }

    @GetMapping(value = "/pesquisar", headers = "HX-Request")
    public String pesquisarHTMX(VacinaFilter filtro, Model model,
                                @PageableDefault(size = 7) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
                                HttpServletRequest request) {
        Page<Vacina> pagina = repository.pesquisar(filtro, pageable);
        logger.info("Vacinas pesquisadas: {}", pagina);
        PageWrapper<Vacina> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        return "vacinas/vacinas :: tabela";
    }

    @PostMapping(value = "/abriralterar", headers = "HX-Request")
    public String abrirAlterarHTMX(Vacina vacina) {
        return "vacinas/alterar :: formulario";
    }

    @PostMapping(value = "/alterar", headers = "HX-Request")
    public String alterarHTMX(Vacina vacina, HttpServletResponse response) {
        vacinaService.alterar(vacina);
        response.setHeader("HX-Location", "{\"path\":\"/vacinas/sucesso2\", \"target\":\"#main\"}");
        return "mensagem";
    }

    @GetMapping(value = "/sucesso2", headers = "HX-Request")
    public String abrirMensagemSucesso2HTMX(Model model) {
        model.addAttribute("mensagem", "Vacina alterada com sucesso");
        return "mensagem :: texto";
    }

    @PostMapping(value = "/abrirremover", headers = "HX-Request")
    public String abrirRemoverHTMX(Vacina vacina) {
        return "vacinas/confirmarremocao :: confirmacao";
    }

    @PostMapping(value = "/remover", headers = "HX-Request")
    public String removerHTMX(Vacina vacina, HttpServletResponse response) {
        vacina.setStatus(Status.INATIVO);
        vacinaService.alterar(vacina);
        response.setHeader("HX-Location", "{\"path\":\"/vacinas/sucesso3\", \"target\":\"#main\"}");
        return "mensagem";
    }

    @GetMapping(value = "/sucesso3", headers = "HX-Request")
    public String abrirMensagemSucesso3HTMX(Model model) {
        model.addAttribute("mensagem", "Vacina removida com sucesso");
        return "mensagem :: texto";
    }
}