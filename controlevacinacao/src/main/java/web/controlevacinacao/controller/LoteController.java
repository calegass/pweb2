package web.controlevacinacao.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxLocation;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTriggerAfterSwap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
import web.controlevacinacao.model.Lote;
import web.controlevacinacao.model.Vacina;
import web.controlevacinacao.notificacao.NotificacaoSweetAlert2;
import web.controlevacinacao.notificacao.TipoNotificaoSweetAlert2;
import web.controlevacinacao.pagination.PageWrapper;
import web.controlevacinacao.repository.LoteRepository;
import web.controlevacinacao.repository.VacinaRepository;
import web.controlevacinacao.service.LoteService;
import web.controlevacinacao.service.VacinaService;

@Controller
@RequestMapping("/lotes")
public class LoteController {

    private static final Logger logger = LoggerFactory.getLogger(LoteController.class);

    private LoteService loteService;
    private LoteRepository loteRepository;

    private VacinaService vacinaService;
    private VacinaRepository vacinaRepository;

    public LoteController(LoteRepository loteRepository, LoteService loteService, VacinaRepository vacinaRepository, VacinaService vacinaService) {
        this.loteRepository = loteRepository;
        this.loteService = loteService;
        this.vacinaRepository = vacinaRepository;
        this.vacinaService = vacinaService;
    }

    @HxRequest
    @GetMapping("/abrirescolhervacina")
    public String abrirPaginaEscolherVacina() {
        return "lotes/escolhervacina :: formulario";
    }

    @HxRequest
    @HxTriggerAfterSwap("htmlAtualizado")
    @GetMapping("/escolhervacina")
    public String escolherVacina(VacinaFilter filtro, Model model,
                                 @PageableDefault(size = 7) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
                                 HttpServletRequest request) {
        Page<Vacina> pagina = vacinaRepository.pesquisar(filtro, pageable);
        logger.info("Lotes buscados: {}", pagina);
        PageWrapper<Vacina> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        return "lotes/vacinas :: tabela";
    }

    @HxRequest
    @PostMapping("/abrircadastrar")
    public String abrirCadastroLote(Lote lote, Vacina vacina, HttpSession session) {
        session.setAttribute("vacina", vacina);
        logger.info("Vacina escolhida: {}", vacina);
        return "lotes/nova :: formulario";
    }

    @HxRequest
    @PostMapping("/nova")
    public String cadastrarLote(@Valid Lote lote, BindingResult result, HtmxResponse.Builder htmxResponse, HttpSession session) {
        lote.setVacina((Vacina) session.getAttribute("vacina"));
        logger.info(("teste"));
        logger.info("Lote recebido para cadastrar: {}", lote);
        logger.info("Vacina do lote: {}", lote.getVacina());
        if (result.hasErrors()) {
            logger.info("O lote recebido para cadastrar não é válido.");
            logger.info("Erros encontrados:");
            for (FieldError erro : result.getFieldErrors()) {
                logger.info("{}", erro);
            }
            return "lotes/nova :: formulario";
        }
        lote.setVacina((Vacina) session.getAttribute("vacina"));
        logger.info("Lote recebido para cadastrar: {}", lote);
        logger.info("Vacina do lote: {}", lote.getVacina());
        loteService.salvar(lote);
        HtmxLocation hl = new HtmxLocation("/lotes/sucesso");
        hl.setTarget("#main");
        hl.setSwap("outerHTML");
        htmxResponse.location(hl);
        return "redirect:/lotes/sucesso";
    }

    @HxRequest
    @HxTriggerAfterSwap("htmlAtualizado")
    @GetMapping("/sucesso")
    public String abrirMensagemSucessoHTMX(Lote lote, Model model) {
        model.addAttribute("notificacao", new NotificacaoSweetAlert2("Lote cadastrado com sucesso!",
                TipoNotificaoSweetAlert2.SUCCESS, 4000));
        return "lotes/nova :: formulario";
    }
}
