package web.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping(value = "/nova", headers = "HX-Request")
    public String abrirCadastroPessoaHTMX(Pessoa pessoa) {
        return "pessoas/nova :: formulario";
    }

    @PostMapping(value = "/nova", headers = "HX-Request")
    public String cadastrarVacinaHTMX(@Valid Pessoa pessoa, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            logger.info("A pessoa recebida para cadastrar não é válida.");
            logger.info("Erros encontrados:");
            for (FieldError erro : result.getFieldErrors()) {
                logger.info("{}", erro);
            }
            return "pessoa/nova :: formulario";
        } else {
            pessoaService.salvar(pessoa);
            response.setHeader("HX-Location", "{\"path\":\"/pessoas/sucesso\", \"target\":\"#main\"}");
            return "mensagem";
        }
    }

    @GetMapping(value = "/sucesso", headers = "HX-Request")
    public String abrirMensagemSucessoHTMX(Model model) {
        model.addAttribute("mensagem", "Pessoa cadastrada com sucesso");
        return "mensagem :: texto";
    }
}

//@GetMapping(value = "/nova", headers = "HX-Request")
//public String abrirCadastroVacinaHTMX(Vacina vacina) {
//    return "vacinas/nova :: formulario";
//}
//
//@PostMapping(value = "/nova", headers = "HX-Request")
//public String cadastrarVacinaHTMX(@Valid Vacina vacina, BindingResult result, HttpServletResponse response) {
//    if (result.hasErrors()) {
//        logger.info("A vacina recebida para cadastrar não é válida.");
//        logger.info("Erros encontrados:");
//        for (FieldError erro : result.getFieldErrors()) {
//            logger.info("{}", erro);
//        }
//        return "vacinas/nova :: formulario";
//    } else {
//        vacinaService.salvar(vacina);
//        response.setHeader("HX-Location", "{\"path\":\"/vacinas/sucesso\", \"target\":\"#main\"}");
//        return "mensagem";
//    }
//}
//
//@GetMapping(value = "/sucesso", headers = "HX-Request")
//public String abrirMensagemSucessoHTMX(Model model) {
//    model.addAttribute("mensagem", "Vacina cadastrada com sucesso");
//    return "mensagem :: texto";
//}
//
//@GetMapping(value = "/abrirpesquisar", headers = "HX-Request")
//public String abrirPaginaPesquisaHTMX() {
//    return "vacinas/pesquisar :: formulario";
//}
//
//@GetMapping(value = "/pesquisar", headers = "HX-Request")
//public String pesquisarHTMX(VacinaFilter filtro, Model model,
//                            @PageableDefault(size = 7) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
//                            HttpServletRequest request) {
//    Page<Vacina> pagina = repository.pesquisar(filtro, pageable);
//    logger.info("Vacinas pesquisadas: {}", pagina);
//    PageWrapper<Vacina> paginaWrapper = new PageWrapper<>(pagina, request);
//    model.addAttribute("pagina", paginaWrapper);
//    return "vacinas/vacinas :: tabela";
//}
//
//@PostMapping(value = "/abriralterar", headers = "HX-Request")
//public String abrirAlterarHTMX(Vacina vacina) {
//    return "vacinas/alterar :: formulario";
//}
//
//@PostMapping(value = "/alterar", headers = "HX-Request")
//public String alterarHTMX(Vacina vacina, HttpServletResponse response) {
//    vacinaService.alterar(vacina);
//    response.setHeader("HX-Location", "{\"path\":\"/vacinas/sucesso2\", \"target\":\"#main\"}");
//    return "mensagem";
//}
//
//@GetMapping(value = "/sucesso2", headers = "HX-Request")
//public String abrirMensagemSucesso2HTMX(Model model) {
//    model.addAttribute("mensagem", "Vacina alterada com sucesso");
//    return "mensagem :: texto";
//}
//
//@PostMapping(value = "/abrirremover", headers = "HX-Request")
//public String abrirRemoverHTMX(Vacina vacina) {
//    return "vacinas/confirmarremocao :: confirmacao";
//}
//
//@PostMapping(value = "/remover", headers = "HX-Request")
//public String removerHTMX(Vacina vacina, HttpServletResponse response) {
//    vacina.setStatus(Status.INATIVO);
//    vacinaService.alterar(vacina);
//    response.setHeader("HX-Location", "{\"path\":\"/vacinas/sucesso3\", \"target\":\"#main\"}");
//    return "mensagem";
//}
//
//@GetMapping(value = "/sucesso3", headers = "HX-Request")
//public String abrirMensagemSucesso3HTMX(Model model) {
//    model.addAttribute("mensagem", "Vacina removida com sucesso");
//    return "mensagem :: texto";
//}