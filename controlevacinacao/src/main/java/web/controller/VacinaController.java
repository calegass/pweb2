package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.Vacina;
import web.repository.VacinaRepository;

import java.util.List;

@Controller
@RequestMapping("/vacinas")
public class VacinaController {
    private VacinaRepository repository;

    public VacinaController(VacinaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todas")
    public String todasVacinas(Model model) {
        List<Vacina> vacinas = repository.findAll();
        model.addAttribute("vacinas", vacinas);
        
        return "vacinas/todas";
    }
}