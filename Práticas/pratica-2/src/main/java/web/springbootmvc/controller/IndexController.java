package web.springbootmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping(value = {"/", "/index.html"} )
    public String index() {
        logger.trace(">>>>>>>>>>>>>>>> Entrou em index");
        logger.trace(">>>>>>>>>>>>>>>> Encaminhando para a view index");
        return "index";
    }

}
