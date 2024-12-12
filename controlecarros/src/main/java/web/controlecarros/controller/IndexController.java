package web.controlecarros.controller;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;

@Controller
public class IndexController {

	@GetMapping(value = {"/", "/index.html"})
	public String index() {
		return "index";
	}

	@HxRequest
	@GetMapping(value = {"/", "/index.html"})
	public View indexHTMX() {
		return FragmentsRendering
				.with("index :: conteudo")
				.fragment("/layout/fragments/header :: usuariologinlogout")
				.build();
	}

	@HxRequest
	@GetMapping("/login")
	public String login() {
		return "login :: formulario";
	}

}