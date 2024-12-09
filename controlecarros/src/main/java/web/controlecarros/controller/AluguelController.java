package web.controlecarros.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.controlecarros.filter.CarroFilter;
import web.controlecarros.model.Aluguel;
import web.controlecarros.model.Carro;
import web.controlecarros.notificacao.NotificacaoSweetAlert2;
import web.controlecarros.notificacao.TipoNotificaoSweetAlert2;
import web.controlecarros.pagination.PageWrapper;
import web.controlecarros.repository.CarroRepository;
import web.controlecarros.service.AluguelService;
import web.controlecarros.service.CarroService;

@Controller
@RequestMapping("/alugueis")
public class AluguelController {

    private static final Logger logger = LoggerFactory.getLogger(CarroController.class);

    private final AluguelService aluguelService;
    private final CarroService carroService;

    public AluguelController(AluguelService aluguelService, CarroService carroService) {
        this.carroService = carroService;
        this.aluguelService = aluguelService;
    }

    @HxRequest
    @GetMapping("")
    public String abrirPaginaAluguel(Model model) {
        List<Carro> carrosDisponiveis = carroService.getCarrosDisponiveis();
        List<Carro> carrosAlugados = carroService.getCarrosAlugados();
        logger.info("Carros disponíveis: {}", carrosDisponiveis);
        logger.info("Carros alugados: {}", carrosAlugados);
        model.addAttribute("carrosDisponiveis", carrosDisponiveis);
        model.addAttribute("carrosAlugados", carrosAlugados);

        return "controle_acesso/controle_acesso :: tabelas";
    }

    @HxRequest()
    @GetMapping("/alugar/{carroId}")
    public String abrirPaginaAlugar(@PathVariable Long carroId, Model model, HtmxResponse.Builder htmxResponse) {
        Carro carro = carroService.getCarroById(carroId);
        Aluguel aluguel = new Aluguel(carro, "");
        model.addAttribute("carro", carro);
        model.addAttribute("aluguel", aluguel);
        return "controle_acesso/alugar :: formulario";
    }

    @HxRequest
    @PostMapping("/alugar")
    public String alugarCarro(@RequestParam Long carroId, @RequestParam String nomeFuncionario, Model model,
            HtmxResponse.Builder htmxResponse) {

        Carro carro = carroService.getCarroById(carroId);

        if (nomeFuncionario == null || nomeFuncionario.isBlank()) {
            model.addAttribute("notificacao", new NotificacaoSweetAlert2("Nome do funcionário é obrigatório",
                    TipoNotificaoSweetAlert2.ERROR, 4000));
            return "controle_acesso/alugar :: formulario";
        }
        aluguelService.alugarCarro(carro, nomeFuncionario);

        return "redirect:/alugueis";
    }

    @HxRequest
    @HxTriggerAfterSwap("htmlAtualizado")
    @GetMapping("/sucesso2")
    public String abrirMensagemSucesso2(Carro carro, Model model) {
        model.addAttribute("notificacao", new NotificacaoSweetAlert2("Aluguel feito com sucesso!",
                TipoNotificaoSweetAlert2.SUCCESS, 4000));
        return "controle_acesso/controle_acesso :: tabelas";
    }

    // @HxRequest
    // @GetMapping("/novo")
    // public String abrirCadastroCarro(Carro carro) {
    // return "carros/novo :: formulario";
    // }

    // @HxRequest
    // @PostMapping("/novo")
    // public String cadastrarCarro(@Valid Carro carro, BindingResult result,
    // HtmxResponse.Builder htmxResponse) {
    // if (result.hasErrors()) {
    // logger.info("O carro recebido para cadastrar não é válido.");
    // logger.info("Erros encontrados:");
    // for (FieldError erro : result.getFieldErrors()) {
    // logger.info("{}", erro);
    // }
    // return "carros/novo :: formulario";
    // } else {
    // carroService.salvar(carro);
    // HtmxLocation hl = new HtmxLocation("/carros/sucesso");
    // hl.setTarget("#main");
    // hl.setSwap("outerHTML");
    // htmxResponse.location(hl);
    // return "mensagem";
    // }
    // }

    // @HxRequest
    // @HxTriggerAfterSwap("htmlAtualizado")
    // @GetMapping("/sucesso")
    // public String abrirMensagemSucesso(Carro carro, Model model) {
    // model.addAttribute("notificacao", new NotificacaoSweetAlert2("Carro
    // cadastrado com sucesso!",
    // TipoNotificaoSweetAlert2.SUCCESS, 4000));
    // return "carros/novo :: formulario";
    // }

    // @HxRequest
    // @GetMapping("/abrirpesquisar")
    // public String abrirPaginaPesquisa() {
    // return "carros/pesquisar :: formulario";
    // }

    // @HxRequest
    // @GetMapping("/pesquisar")
    // public String pesquisar(CarroFilter filtro, Model model,
    // @PageableDefault(size = 7) @SortDefault(sort = "codigo", direction =
    // Sort.Direction.ASC) Pageable pageable,
    // HttpServletRequest request) {
    // Page<Carro> pagina = carroRepository.pesquisar(filtro, pageable);
    // logger.info("Carros pesquisados: {}", pagina);
    // PageWrapper<Carro> paginaWrapper = new PageWrapper<>(pagina, request);
    // model.addAttribute("pagina", paginaWrapper);
    // return "carros/carros :: tabela";
    // }

    // @HxRequest
    // @PostMapping("/abriralterar")
    // public String abrirAlterar(Carro carro) {
    // return "carros/alterar :: formulario";
    // }

    // @HxRequest
    // @PostMapping("/alterar")
    // public String alterar(@Valid Carro carro, BindingResult result,
    // HtmxResponse.Builder htmxResponse) {
    // if (result.hasErrors()) {
    // logger.info("O carro recebido para alterar não é válido.");
    // logger.info("Erros encontrados:");
    // for (FieldError erro : result.getFieldErrors()) {
    // logger.info("{}", erro);
    // }
    // return "carros/alterar :: formulario";
    // } else {
    // carroService.alterar(carro);
    // HtmxLocation hl = new HtmxLocation("/carros/sucesso2");
    // hl.setTarget("#main");
    // hl.setSwap("outerHTML");
    // htmxResponse.location(hl);
    // return "mensagem";
    // }
    // }

    // @HxRequest
    // @HxTriggerAfterSwap("htmlAtualizado")
    // @GetMapping("/sucesso2")
    // public String abrirMensagemSucesso2(Carro carro, Model model) {
    // model.addAttribute("notificacao", new NotificacaoSweetAlert2("Carro alterado
    // com sucesso!",
    // TipoNotificaoSweetAlert2.SUCCESS, 4000));
    // return "carros/pesquisar :: formulario";
    // }

    // @HxRequest
    // @HxLocation(path = "/carros/sucesso3", target = "#main", swap = "outerHTML")
    // @PostMapping("/remover")
    // public String remover(Carro carro) {
    // carro.disable();
    // carroService.alterar(carro);
    // return "mensagem";
    // }

    // @HxRequest
    // @HxTriggerAfterSwap("htmlAtualizado")
    // @GetMapping("/sucesso3")
    // public String abrirMensagemSucesso3(Model model) {
    // model.addAttribute("notificacao", new NotificacaoSweetAlert2("Carro removido
    // com sucesso!",
    // TipoNotificaoSweetAlert2.SUCCESS, 4000));
    // return "carros/pesquisar :: formulario";
    // }

}