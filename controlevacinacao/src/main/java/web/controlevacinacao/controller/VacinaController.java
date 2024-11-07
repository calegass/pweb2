package web.controlevacinacao.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.controlevacinacao.filter.VacinaFilter;
import web.controlevacinacao.model.Status;
import web.controlevacinacao.model.Vacina;
import web.controlevacinacao.notificacao.NotificacaoSweetAlert2;
import web.controlevacinacao.notificacao.TipoNotificaoSweetAlert2;
import web.controlevacinacao.pagination.PageWrapper;
import web.controlevacinacao.repository.VacinaRepository;
import web.controlevacinacao.service.VacinaService;

@Controller
@RequestMapping("/vacinas")
public class VacinaController {

    private static final Logger logger = LoggerFactory.getLogger(VacinaController.class);

    private VacinaRepository vacinaRepository;
    private VacinaService vacinaService;

    public VacinaController(VacinaRepository vacinaRepository, VacinaService vacinaService) {
        this.vacinaRepository = vacinaRepository;
        this.vacinaService = vacinaService;
    }

    // @GetMapping("/todas")
    // public String mostrarTodasVacinas(Model model) {
    // List<Vacina> vacinas = vacinaRepository.findAll();
    // logger.info("Vacinas buscadas: {}", vacinas);
    // model.addAttribute("vacinas", vacinas);
    // return "vacinas/todas";
    // }

    @GetMapping("/nova")
    public String abrirCadastroVacina(Vacina vacina) {
        return "vacinas/nova";
    }

    @HxRequest
    @GetMapping("/nova")
    public String abrirCadastroVacinaHTMX(Vacina vacina) {
        return "vacinas/nova :: formulario";
    }

    @PostMapping("/nova")
    public String cadastrarVacina(Vacina vacina) {
        vacinaService.salvar(vacina);
        return "redirect:/vacinas/sucesso";
    }

    @GetMapping("/sucesso")
    public String abrirMensagemSucesso(Model model) {
        model.addAttribute("mensagem", "Vacina cadastrada com sucesso");
        return "mensagem";
    }

    @HxRequest
    @PostMapping("/nova")
    public String cadastrarVacinaHTMX(@Valid Vacina vacina, BindingResult result, HtmxResponse.Builder htmxResponse) {
        if (result.hasErrors()) {
            logger.info("A vacina recebida para cadastrar não é válida.");
            logger.info("Erros encontrados:");
            for (FieldError erro : result.getFieldErrors()) {
                logger.info("{}", erro);
            }
            return "vacinas/nova :: formulario";
        } else {
            vacinaService.salvar(vacina);
            HtmxLocation hl = new HtmxLocation("/vacinas/sucesso");
            hl.setTarget("#main");
            hl.setSwap("outerHTML");
            htmxResponse.location(hl);
            return "mensagem";
        }
    }

    @HxRequest
    @HxTriggerAfterSwap("htmlAtualizado")
    @GetMapping("/sucesso")
    public String abrirMensagemSucessoHTMX(Vacina vacina, Model model) {
        model.addAttribute("notificacao", new NotificacaoSweetAlert2("Vacina cadastrada com sucesso!",
                TipoNotificaoSweetAlert2.SUCCESS, 4000));
        return "vacinas/nova :: formulario";
    }

    @GetMapping("/abrirpesquisar")
    public String abrirPaginaPesquisa() {
        return "vacinas/pesquisar";
    }

    @HxRequest
    @GetMapping("/abrirpesquisar")
    public String abrirPaginaPesquisaHTMX() {
        return "vacinas/pesquisar :: formulario";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(VacinaFilter filtro, Model model,
                            @PageableDefault(size = 7) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
                            HttpServletRequest request) {
        Page<Vacina> pagina = vacinaRepository.pesquisar(filtro, pageable);
        logger.info("Vacinas pesquisadas: {}", pagina);
        PageWrapper<Vacina> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        return "vacinas/vacinas";
    }

    @HxRequest
    @HxTriggerAfterSwap("htmlAtualizado")
    @GetMapping("/pesquisar")
    public String pesquisarHTMX(VacinaFilter filtro, Model model,
                                @PageableDefault(size = 7) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
                                HttpServletRequest request) {
        Page<Vacina> pagina = vacinaRepository.pesquisar(filtro, pageable);
        logger.info("Vacinas pesquisadas: {}", pagina);
        PageWrapper<Vacina> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        return "vacinas/vacinas :: tabela";
    }

    @PostMapping("/abriralterar")
    public String abrirAlterar(Vacina vacina) {
        return "vacinas/alterar";
    }

    @HxRequest
    @PostMapping("/abriralterar")
    public String abrirAlterarHTMX(Vacina vacina) {
        return "vacinas/alterar :: formulario";
    }

    @PostMapping("/alterar")
    public String alterar(Vacina vacina) {
        vacinaService.alterar(vacina);
        return "redirect:/vacinas/sucesso2";
    }

    @GetMapping("/sucesso2")
    public String abrirMensagemSucesso2(Model model) {
        model.addAttribute("mensagem", "Vacina alterada com sucesso");
        return "mensagem";
    }

    @HxRequest
    @PostMapping("/alterar")
    public String alterarHTMX(@Valid Vacina vacina, BindingResult result, HtmxResponse.Builder htmxResponse) {
        if (result.hasErrors()) {
            logger.info("A vacina recebida para alterar não é válida.");
            logger.info("Erros encontrados:");
            for (FieldError erro : result.getFieldErrors()) {
                logger.info("{}", erro);
            }
            return "vacinas/alterar :: formulario";
        } else {
            vacinaService.alterar(vacina);
            HtmxLocation hl = new HtmxLocation("/vacinas/sucesso2");
            hl.setTarget("#main");
            hl.setSwap("outerHTML");
            htmxResponse.location(hl);
            return "mensagem";
        }
    }

    @HxRequest
    @HxTriggerAfterSwap("htmlAtualizado")
    @GetMapping("/sucesso2")
    public String abrirMensagemSucesso2HTMX(Model model) {
        model.addAttribute("notificacao", new NotificacaoSweetAlert2("Vacina alterada com sucesso!",
                TipoNotificaoSweetAlert2.SUCCESS, 4000));
        return "vacinas/pesquisar :: formulario";
    }

    @PostMapping("/remover")
    public String remover(Vacina vacina) {
        vacina.setStatus(Status.INATIVO);
        vacinaService.alterar(vacina);
        return "redirect:/vacinas/sucesso3";
    }

    @GetMapping("/sucesso3")
    public String abrirMensagemSucesso3(Model model) {
        model.addAttribute("mensagem", "Vacina removida com sucesso");
        return "mensagem";
    }

    @HxRequest
    @HxLocation(path = "/vacinas/sucesso3", target = "#main", swap = "outerHTML")
    @PostMapping("/remover")
    public String removerHTMX(Vacina vacina) {
        vacina.setStatus(Status.INATIVO);
        vacinaService.alterar(vacina);
        return "mensagem";
    }

    @HxRequest
    @HxTriggerAfterSwap("htmlAtualizado")
    @GetMapping("/sucesso3")
    public String abrirMensagemSucesso3HTMX(Model model) {
        model.addAttribute("notificacao", new NotificacaoSweetAlert2("Vacina removida com sucesso!",
                TipoNotificaoSweetAlert2.SUCCESS, 4000));
        return "vacinas/pesquisar :: formulario";
    }

}
