/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.controller;

import local.locadora.dao.ClienteDAO;
import local.locadora.dao.FilmeDAO;
import local.locadora.dao.LocacaoDAO;
import local.locadora.entities.Cliente;
import local.locadora.entities.Filme;
import local.locadora.entities.Locacao;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.LocadoraException;
import local.locadora.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static local.locadora.utils.DataUtils.adicionarDias;

/**
 * @author angelogl
 */
@Controller
public class LocacaoController {

    @Autowired
    private LocacaoDAO locacaoRepository;

    @Autowired
    private FilmeDAO filmeRepository;

    @Autowired
    private ClienteDAO clienteRepository;

    public LocacaoController() {
    }

    public LocacaoController(LocacaoDAO locacaoDAO) {
        this.locacaoRepository = locacaoDAO;
    }

    public LocacaoController(LocacaoDAO locacaoDAO, FilmeDAO filmeDAO, ClienteDAO clienteDAO) {
        this.locacaoRepository = locacaoDAO;
        this.filmeRepository = filmeDAO;
        this.clienteRepository = clienteDAO;
    }

    @GetMapping({"/locacao"})
    //@ResponseBody
    public String list(Model model) {

        model.addAttribute("todosFilmes", filmeRepository.findByEstoqueGreaterThan(0));

        model.addAttribute("locacao", new Locacao());

        model.addAttribute("clientes", clienteRepository.findAll());

        model.addAttribute("locacoes", locacaoRepository.findAll());
        return "pages/locacao";
    }

    @PostMapping({"/locacao"})
    public ModelAndView alugarFilme(Locacao locacao, BindingResult bindingResult, RedirectAttributes flash) throws FilmeSemEstoqueException, LocadoraException {
        ModelAndView view = new ModelAndView();
        try {
            Cliente cliente = locacao.getCliente();
            locacao.setDataLocacao(new Date());
            List<Filme> filmes = locacao.getFilmes();

            if (cliente == null) {
                throw new LocadoraException("Impossivel locar sem um cliente");
            }
            if (filmes == null) {
                throw new LocadoraException("Nenhum filme foi selecionado");
            }
            // Locacao locacao2 = new Locacao();
            Double valorTotal = 0d;
            for (int i = 0; i < filmes.size(); i++) {
                Filme filme = filmes.get(i);
                if (filme.getEstoque() == 0) {
                    throw new FilmeSemEstoqueException("Filme sem estoque");
                }
                double valorFilme = filme.getPrecoLocacao();
                switch (i) {
                    case 2:
                        valorFilme = valorFilme * 0.75;
                        break;
                    case 3:
                        valorFilme = valorFilme * 0.50;
                        break;
                    case 4:
                        valorFilme = 0;
                        break;
                    default:
                }
                filme.setEstoque(filme.getEstoque() - 1);
                //locacao2.addFilme(filme);
                valorTotal += valorFilme;
                //Entrega no dia seguinte
                Date dataEntrega = new Date();
                dataEntrega = adicionarDias(dataEntrega, filmes.size());
                if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
                    dataEntrega = adicionarDias(dataEntrega, 1);
                }
                locacao.setDataRetorno(dataEntrega);


            }
            locacao.setValor(valorTotal);
            locacao.setCliente(cliente);
            locacao.setDataLocacao(new Date());

            if (bindingResult.hasErrors()) {
                return new ModelAndView("redirect/locacao");

            } else {

                locacaoRepository.save(locacao);
                flash.addFlashAttribute("sucessmessage", "Locação realizado com sucesso");
                if (view != null) {
                    view.addObject("todosFilmes", filmeRepository.findByEstoqueGreaterThan(0));

                    view.addObject("locacao", new Locacao());

                    view.addObject("clientes", clienteRepository.findAll());

                    view.addObject("locacoes", locacaoRepository.findAll());
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            flash.addFlashAttribute("errormessage", "Um erro inesperado ocorreu. Provavelmente nenhum filme foi" +
                    " selecionado para locacação");
        }

        view.setViewName("redirect:/locacao");
        return view;
    }

    public LocacaoDAO getLocacaoRepository() {
        return locacaoRepository;
    }

    public FilmeDAO getFilmeRepository() {
        return filmeRepository;
    }

    public ClienteDAO getClienteRepository() {
        return clienteRepository;
    }

    @PostConstruct
    public void population() {
        filmeRepository.save(new Filme("Os 3 patetas", 4, 4.0));
        filmeRepository.save(new Filme("Robocop", 2, 4.0));
        filmeRepository.save(new Filme("Os Vingadores", 4, 4.0));
        filmeRepository.save(new Filme("Um drink no inferno", 3, 4.0));
        filmeRepository.save(new Filme("A espera de um milagre", 8, 4.0));
        clienteRepository.save(new Cliente("Angelo Luz", "01403551030"));
        clienteRepository.save(new Cliente("Mussum"));
        clienteRepository.save(new Cliente("Axl Rose"));


    }


}
