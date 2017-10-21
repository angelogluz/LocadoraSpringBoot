package local.locadora.controller;

import local.locadora.dao.FilmeDAO;
import local.locadora.entities.Filme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    @GetMapping({"/login"})
    public String list() {
        return "login";
    }
    @GetMapping({"/index"})
    public String index() {
        return "pages/index";
    }

}
