package web.controlecarros.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxTriggerAfterSwap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.controlecarros.model.Aluguel;
import web.controlecarros.model.Carro;
import web.controlecarros.notificacao.NotificacaoSweetAlert2;
import web.controlecarros.notificacao.TipoNotificaoSweetAlert2;
import web.controlecarros.service.AluguelService;
import web.controlecarros.service.CarroService;

import java.util.List;

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

	@HxRequest()
	@GetMapping("/devolver/{carroId}")
	public String abrirPaginaDevolver(@PathVariable Long carroId, Model model, HtmxResponse.Builder htmxResponse) {
		Carro carro = carroService.getCarroById(carroId);
		Aluguel aluguel = aluguelService.getAluguelByCarro(carro);
		model.addAttribute("carro", carro);
		model.addAttribute("aluguel", aluguel);
		return "controle_acesso/devolver :: formulario";
	}

	@HxRequest
	@PostMapping("/alugar")
	public String alugarCarro(@RequestParam Long carroId, @RequestParam String nomeFuncionario, Model model,
	                          HtmxResponse.Builder htmxResponse) {

		Carro carro = carroService.getCarroById(carroId);

		aluguelService.alugarCarro(carro, nomeFuncionario);

		return "redirect:/alugueis";
	}

	@HxRequest
	@PostMapping("/devolver")
	public String devolverCarro(@RequestParam Long carroId, @RequestParam Double kilometragemAtual, Model model,
	                            HtmxResponse.Builder htmxResponse) {

		Carro carro = carroService.getCarroById(carroId);

		aluguelService.devolverCarro(carro, kilometragemAtual);

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

	@HxRequest
	@GetMapping("/historico")
	public String abrirPaginaHistorico(Model model) {
		List<Aluguel> alugueis = aluguelService.getAlugueisFinalizados();

		logger.info("Aluguéis: {}", alugueis);
		model.addAttribute("alugueis", alugueis);

		return "controle_acesso/historico :: tabela";
	}
}