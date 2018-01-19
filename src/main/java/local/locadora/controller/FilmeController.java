package local.locadora.controller;

import local.locadora.dao.FilmeDAO;
import local.locadora.entities.Filme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class FilmeController {

    @Autowired
    private final FilmeDAO filmeRepository;


    public FilmeController(FilmeDAO alunoRepository) {
        this.filmeRepository = alunoRepository;

    }

    @GetMapping({"/filme"})
    //@ResponseBody
    public String list(Model model) {
        model.addAttribute("filmes", filmeRepository.findAll());
        model.addAttribute("filme", new Filme());
        return "pages/filme";
    }

    @PostMapping("/filme")
    //@ResponseBody
    public ModelAndView save(@Valid Filme filme, BindingResult bindingResult, RedirectAttributes flash) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("pages/filme", "filmes", filmeRepository.findAll());

        } else {
            try {
                filmeRepository.save(filme);
                flash.addFlashAttribute("sucessmessage", "Cadastro realizado com sucesso");
            } catch (Exception ex) {
                flash.addFlashAttribute("errormessage", "Filme já cadastrado");
            }
            return new ModelAndView("redirect:/filme");
        }
    }



}
