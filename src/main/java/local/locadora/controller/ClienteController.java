package local.locadora.controller;

import local.locadora.entities.Cliente;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import local.locadora.dao.ClienteDAO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import java.io.IOException;

@Controller
public class ClienteController {

    @Autowired
    private ClienteDAO clienteRepository;


    public ClienteController(ClienteDAO usuarioRepository) {
        this.clienteRepository = usuarioRepository;
        
    }


    @GetMapping("/cliente")
    public String list(Model model) {
        model.addAttribute("cliente", new Cliente());
            model.addAttribute("clientes", clienteRepository.findAll());
        return "pages/cliente";
    }


    @PostMapping("/cliente")
    public ModelAndView save(@Valid Cliente cliente, BindingResult bindingResult,RedirectAttributes flash) throws RuntimeException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView ("pages/cliente","clientes",clienteRepository.findAll());

        }else {
            try {
                clienteRepository.save(cliente);
                flash.addFlashAttribute("sucessmessage", "Cadastro realizado com sucesso");
            }catch (Exception ex){
                flash.addFlashAttribute("errormessage", "Nome já cadastrado");
                throw new RuntimeException("Erro ao tentar persistir o registro");
            }
            return new ModelAndView  ("redirect:/cliente");
        }
    }

    public ClienteController() {
    }

    public ClienteDAO getClienteRepository() {
        return clienteRepository;
    }
}


