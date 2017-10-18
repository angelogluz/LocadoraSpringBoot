package local.locadora.controller;

import local.locadora.entities.Filme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import local.locadora.dao.FilmeDAO;
import org.springframework.ui.Model;

@Controller
public class FilmeController {

    private final FilmeDAO filmeRepository;

    @Autowired
    public FilmeController(FilmeDAO alunoRepository) {
        this.filmeRepository = alunoRepository;
        
    }

    @GetMapping({"/filme","/"})
    //@ResponseBody
    public String list(Model model) {
        model.addAttribute("filmes",filmeRepository.findAll());
        return "filme";
    }
    
    @PostMapping("/filme")
    //@ResponseBody
    public void save(Filme filme, Model model) {
        filmeRepository.save(filme);
        list(model);
    }
  
}
