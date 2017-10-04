package local.locadora.controller;

import local.locadora.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import local.locadora.dao.UsuarioDAO;
import org.springframework.ui.Model;

@Controller
public class UsuarioController {

    private UsuarioDAO usuarioRepository;

    @Autowired
    public UsuarioController(UsuarioDAO usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        
    }

    @GetMapping("/usuario")
    //@ResponseBody
    public String list(Model model) {
        model.addAttribute("usuarios",usuarioRepository.findAll());
        return "usuario";
    }
    
    @PostMapping("/usuario")
    //@ResponseBody
    public void save(Usuario usuario, Model model) {
        usuarioRepository.save(usuario);
        list(model);
    }
    
}
