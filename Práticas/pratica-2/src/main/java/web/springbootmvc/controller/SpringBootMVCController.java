package web.springbootmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.springbootmvc.model.ClasseQualquer;

@Controller
public class SpringBootMVCController {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootMVCController.class);

    @GetMapping("/retornarview")
    public String retornarView() {
        logger.trace(">>>>>>>>>>>>>>>> Entrou em retornarView");
        logger.trace(">>>>>>>>>>>>>>>> Encaminhando para a view viewretornada");
        return "viewretornada";
    }
    @PostMapping("/bemvindo")
    public String darBoasVindas(@RequestParam(name="nome", defaultValue="João Ninguém") String nome, Model model) {
        logger.trace(">>>>>>>>>>>>>>>> Entrou em darBoasVindas");
        logger.debug(">>>>>>>>>>>>>>>> Recebeu o nome {}", nome);
        model.addAttribute("nome", nome);
        logger.trace(">>>>>>>>>>>>>>>> Encaminhando para a view mensagemboasvindas");
        return "mensagemboasvindas";
    }
    @PostMapping("/objetoqualquer")
    public String metodoObjetoQualquer(ClasseQualquer objetoQualquer, Model model) {
        logger.trace(">>>>>>>>>>>>>>>> Entrou em metodoObjetoQualquer");
        logger.debug(">>>>>>>>>>>>>>>> Recebeu o objetoQualquer {}", objetoQualquer);
        model.addAttribute("objeto", objetoQualquer);
        logger.trace(">>>>>>>>>>>>>>>> Encaminhando para a view mostrarobjetoqualquer");
        return "mostrarobjetoqualquer";
    }
}
