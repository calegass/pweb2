package web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import web.filter.PessoaFilter;
import web.filter.VacinaFilter;
import web.model.Pessoa;
import web.model.Status;
import web.model.Vacina;
import web.pagination.PageWrapper;
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

    @GetMapping("/nova")
    public String abrirCadastroPessoa(Model model) {
        Pessoa pessoa = new Pessoa();
        model.addAttribute("pessoa", pessoa);
        return "pessoas/nova";
    }

    @PostMapping("/nova")
    public String cadastrarPessoa(Pessoa pessoa) {
        pessoaService.salvar(pessoa);
        return "redirect:/pessoas/sucesso";
    }

    @GetMapping("/sucesso")
    public String sucessoCadastro(Model model) {
        model.addAttribute("mensagem", "Pessoa cadastrada com sucesso!");
        return "mensagem";
    }

    @GetMapping("/abrirpesquisar")
    public String pesquisar(Model model) {
        model.addAttribute("pessoaFilter", new PessoaFilter());
        return "pessoas/pesquisar";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(PessoaFilter filter, Model model,
                            @PageableDefault(size = 7)
                            @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
                            HttpServletRequest request) {
        Page<Pessoa> pagina = repository.pesquisar(filter, pageable);
        logger.info("Exibindo a página de pessoas pesquisadas: {}", pagina);
        PageWrapper<Pessoa> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        return "pessoas/pessoas";
    }

    @PostMapping("/abriralterar")
    public String abrirAlterar(Pessoa pessoa) {
        return "pessoas/alterar";
    }

    @PostMapping("/alterar")
    public String alterar(Pessoa pessoa) {
        pessoaService.alterar(pessoa);
        return "redirect:/pessoas/sucesso2";
    }

    @GetMapping("/sucesso2")
    public String sucessoAlteracao(Model model) {
        model.addAttribute("mensagem", "Pessoa alterada com sucesso!");
        return "mensagem";
    }

    @PostMapping("/abrirremover")
    public String abrirRemover(Pessoa pessoa, Model model) {
        model.addAttribute("pessoa", pessoa);
        return "pessoas/confirmarremocao";
    }

    @PostMapping("/remover")
    public String remover(Pessoa pessoa) {
        pessoa.setStatus(Status.INATIVO);
        pessoaService.alterar(pessoa);
        return "redirect:/pessoas/sucesso3";
    }

    @GetMapping("/sucesso3")
    public String sucessoRemocao(Model model) {
        model.addAttribute("mensagem", "Pessoa removida com sucesso!");
        return "mensagem";
    }


    @GetMapping(value = "/nova", headers = "HX-Request")
    public String abrirCadastroPessoaHTMX(Pessoa pessoa) {
        return "pessoas/nova :: formulario";
    }

    @PostMapping(value = "/nova", headers = "HX-Request")
    public String cadastrarPessoaHTMX(@Valid Pessoa pessoa, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            logger.info("A pessoa recebida para cadastrar não é valida");
            logger.info("Erros encontrados:");
            for (FieldError erro: result.getFieldErrors()) {
                logger.info("{}", erro);
            }
            return "pessoas/nova :: formulario";
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

    @GetMapping(value = "/abrirpesquisar", headers = "HX-Request")
    public String abrirPaginaPesquisaHTMX() {
        return "pessoas/pesquisar :: formulario";
    }


    @GetMapping(value = "/pesquisar", headers = "HX-Request")
    public String pesquisarHTMX(PessoaFilter filtro, Model model,
                                @PageableDefault(size = 7) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
                                HttpServletRequest request) {
        Page<Pessoa> pagina = repository.pesquisar(filtro, pageable);
        logger.info("Pessoas pesquisadas: {}", pagina);
        PageWrapper<Pessoa> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        return "pessoas/pessoas :: tabela";
    }
  

    @PostMapping(value = "/abriralterar", headers = "HX-Request")
    public String abrirAlterarHTMX(Pessoa pessoa) {
        return "pessoas/alterar :: formulario";
    }

    @PostMapping(value = "/alterar", headers = "HX-Request")
    public String alterarHTMX(Pessoa pessoa, HttpServletResponse response) {
        pessoaService.alterar(pessoa);
        response.setHeader("HX-Location", "{\"path\":\"/pessoas/sucesso2\", \"target\":\"#main\"}");
        return "mensagem";
    }

    @GetMapping(value = "/sucesso2", headers = "HX-Request")
    public String abrirMensagemSucesso2HTMX(Model model) {
        model.addAttribute("mensagem", "Pessoa alterada com sucesso");
        return "mensagem :: texto";
    }

    @PostMapping(value = "/abrirremover", headers = "HX-Request")
    public String abrirRemoverHTMX(Pessoa pessoa) {
        return "pessoas/confirmarremocao :: confirmacao";
    }

    @PostMapping(value = "/remover", headers = "HX-Request")
    public String removerHTMX(Pessoa pessoa, HttpServletResponse response) {
        pessoa.setStatus(Status.INATIVO);
        pessoaService.alterar(pessoa);
        response.setHeader("HX-Location", "{\"path\":\"/pessoas/sucesso3\", \"target\":\"#main\"}");
        return "mensagem";
    }

    @GetMapping(value = "/sucesso3", headers = "HX-Request")
    public String abrirMensagemSucesso3HTMX(Model model) {
        model.addAttribute("mensagem", "Pessoa removida com sucesso");
        return "mensagem :: texto";
    }
}