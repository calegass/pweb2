package web.controlecarros.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTriggerAfterSwap;
import jakarta.validation.Valid;
import web.controlecarros.model.Papel;
import web.controlecarros.model.Usuario;
import web.controlecarros.notificacao.NotificacaoSweetAlert2;
import web.controlecarros.notificacao.TipoNotificaoSweetAlert2;
import web.controlecarros.repository.PapelRepository;
import web.controlecarros.service.CadastroUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	private PapelRepository papelRepository;
	private CadastroUsuarioService cadastroUsuarioService;
	private PasswordEncoder passwordEncoder;
	
	public UsuarioController(PapelRepository papelRepository, CadastroUsuarioService cadastroUsuarioService,
			PasswordEncoder passwordEncoder) {
		this.papelRepository = papelRepository;
		this.cadastroUsuarioService = cadastroUsuarioService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/cadastrar")
	@HxRequest
	@HxTriggerAfterSwap("htmlAtualizado")
	public String abrirCadastroUsuario(Usuario usuario, Model model) {
		List<Papel> papeis = papelRepository.findAll();
		model.addAttribute("todosPapeis", papeis);
		return "usuario/cadastrar :: formulario";
	}
	
	@PostMapping("/cadastrar")
	@HxRequest
	@HxTriggerAfterSwap("htmlAtualizado")
	public String cadastrarNovoUsuario(@Valid Usuario usuario, BindingResult resultado, Model model, RedirectAttributes redirectAttributes) {
		if (resultado.hasErrors()) {
			logger.info("O usuario recebido para cadastrar não é válido.");
			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			List<Papel> papeis = papelRepository.findAll();
			model.addAttribute("todosPapeis", papeis);
			return "usuario/cadastrar :: formulario";
		} else {
			usuario.setAtivo(true);
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			cadastroUsuarioService.salvar(usuario);
			redirectAttributes.addAttribute("mensagem", "Cadastro de usuário efetuado com sucesso.");
			return "redirect:/usuarios/cadastrosucesso";
		}
	}
	
	@GetMapping("/cadastrosucesso")
	@HxRequest
	@HxTriggerAfterSwap("htmlAtualizado") 
	public String mostrarCadastroSucesso(String mensagem, Usuario usuario, Model model) {
		List<Papel> papeis = papelRepository.findAll();
		model.addAttribute("todosPapeis", papeis);
		if (mensagem != null && !mensagem.isEmpty()) {
            model.addAttribute("notificacao", new NotificacaoSweetAlert2(mensagem,
                    TipoNotificaoSweetAlert2.SUCCESS, 4000));
        }
		return "usuario/cadastrar :: formulario";
	}
}