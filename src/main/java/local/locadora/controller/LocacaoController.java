/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import local.locadora.dao.FilmeDAO;
import local.locadora.dao.LocacaoDAO;
import local.locadora.dao.UsuarioDAO;
import local.locadora.entities.Filme;
import local.locadora.entities.Locacao;
import local.locadora.entities.Usuario;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.LocadoraException;
import local.locadora.utils.DataUtils;
import static local.locadora.utils.DataUtils.adicionarDias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author angelogl
 */
@Controller
public class LocacaoController {

    @Autowired
    private LocacaoDAO locacaoRepository;

    @Autowired
    private FilmeDAO filmeRepository;

    @Autowired
    private UsuarioDAO usuarioRepository;

    public LocacaoController() {
    }

    public LocacaoController(LocacaoDAO locacaoRepository) {
        this.locacaoRepository = locacaoRepository;
    }

    @GetMapping({"/locacao"})
    //@ResponseBody
    public String list(Model model) {

        model.addAttribute("todosFilmes", filmeRepository.findByEstoqueGreaterThan(0));

        model.addAttribute("locacao", new Locacao());

        model.addAttribute("usuarios", usuarioRepository.findAll());

        model.addAttribute("locacoes", locacaoRepository.findAll());
        return "locacao";
    }

    @PostMapping({"/locacao"})
    public Locacao alugarFilme(Locacao locacao, Model model) throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = locacao.getUsuario();
        locacao.setDataLocacao(new Date());
        List<Filme> filmes = locacao.getFilmes();

        if (usuario == null) {
            throw new LocadoraException("Impossivel locar sem um usuário");
        }
        if (filmes == null) {
            throw new LocadoraException("Nenhum filme foi selecionado");
        }
        Locacao locacao2 = new Locacao();
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
            filme.setEstoque(filme.getEstoque()-1);
            locacao2.addFilme(filme);
            valorTotal += valorFilme;
            //Entrega no dia seguinte
            Date dataEntrega = new Date();
            dataEntrega = adicionarDias(dataEntrega, 1);
            if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
                dataEntrega = adicionarDias(dataEntrega, 1);
            }
            locacao2.setDataRetorno(dataEntrega);
            

        }
        locacao2.setValor(valorTotal);
        locacao2.setUsuario(usuario);
        locacao2.setDataLocacao(new Date());

        // Desenvolver a persistência
        locacaoRepository.save(locacao2);

        if (model != null) {
            model.addAttribute("todosFilmes", filmeRepository.findByEstoqueGreaterThan(0));

            model.addAttribute("locacao", new Locacao());

            model.addAttribute("usuarios", usuarioRepository.findAll());

            model.addAttribute("locacoes", locacaoRepository.findAll());
        }

        return locacao2;
    }

    @PostConstruct
    public void population() {
        filmeRepository.save(new Filme("Os 3 patetas", 4, 4.0));
        filmeRepository.save(new Filme("Robocop", 2, 4.0));
        filmeRepository.save(new Filme("Os Vingadores", 4, 4.0));
        filmeRepository.save(new Filme("Um drink no inferno", 3, 4.0));
        filmeRepository.save(new Filme("A espera de um milagre", 8, 4.0));
        usuarioRepository.save(new Usuario("Angelo Luz"));
        usuarioRepository.save(new Usuario("Manoel da Silva"));
        usuarioRepository.save(new Usuario("Josevaldo da Silva "));

    }
}
