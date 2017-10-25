package local.locadora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
