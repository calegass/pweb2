package web.controlevacinacao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    // private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    // private PapelRepository papelRepository;
    // private CadastroUsuarioService cadastroUsuarioService;
    // private PasswordEncoder passwordEncoder;

    // public UsuarioController(PapelRepository papelRepository, CadastroUsuarioService cadastroUsuarioService,
    // 		PasswordEncoder passwordEncoder) {
    // 	this.papelRepository = papelRepository;
    // 	this.cadastroUsuarioService = cadastroUsuarioService;
    // 	this.passwordEncoder = passwordEncoder;
    // }

    // @GetMapping("/cadastrar")
    // @HxRequest
    // @HxTriggerAfterSwap("htmlAtualizado")
    // public String abrirCadastroUsuario(Usuario usuario, Model model) {
    // 	List<Papel> papeis = papelRepository.findAll();
    // 	model.addAttribute("todosPapeis", papeis);
    // 	return "usuario/cadastrar :: formulario";
    // }

    // @PostMapping("/cadastrar")
    // @HxRequest
    // @HxTriggerAfterSwap("htmlAtualizado")
    // public String cadastrarNovoUsuario(@Valid Usuario usuario, BindingResult resultado, Model model, RedirectAttributes redirectAttributes) {
    // 	if (resultado.hasErrors()) {
    // 		logger.info("O usuario recebido para cadastrar não é válido.");
    // 		logger.info("Erros encontrados:");
    // 		for (FieldError erro : resultado.getFieldErrors()) {
    // 			logger.info("{}", erro);
    // 		}
    // 		List<Papel> papeis = papelRepository.findAll();
    // 		model.addAttribute("todosPapeis", papeis);
    // 		return "usuario/cadastrar :: formulario";
    // 	} else {
    // 		usuario.setAtivo(true);
    // 		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    // 		cadastroUsuarioService.salvar(usuario);
    // 		redirectAttributes.addAttribute("mensagem", "Cadastro de usuário efetuado com sucesso.");
    // 		return "redirect:/usuarios/cadastrosucesso";
    // 	}
    // }

    // @GetMapping("/cadastrosucesso")
    // @HxRequest
    // @HxTriggerAfterSwap("htmlAtualizado")
    // public String mostrarCadastroSucesso(String mensagem, Usuario usuario, Model model) {
    // 	List<Papel> papeis = papelRepository.findAll();
    // 	model.addAttribute("todosPapeis", papeis);
    // 	if (mensagem != null && !mensagem.isEmpty()) {
    //         model.addAttribute("notificacao", new NotificacaoSweetAlert2(mensagem,
    //                 TipoNotificaoSweetAlert2.SUCCESS, 4000));
    //     }
    // 	return "usuario/cadastrar :: formulario";
    // }
}