package local.locadora.controller;

import local.locadora.dao.LocacaoDAO;
import local.locadora.entities.Filme;
import local.locadora.entities.Locacao;
import local.locadora.entities.Usuario;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.LocadoraException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LocacaoControllerTest {
    Model m;

    @Autowired
    LocacaoDAO locacaoRepository;

    @Test(expected = LocadoraException.class)
    public void testeLocacaoSemUsuario() throws FilmeSemEstoqueException, LocadoraException {

        // Cenário
        LocacaoController locacao1 = new LocacaoController(locacaoRepository);
        Usuario usuario = null;
        Locacao locacao = new Locacao();
        locacao.setUsuario(usuario);

        // Ação
        locacao1.alugarFilme(locacao, m);
    }

    @Test(expected = LocadoraException.class)
    public void testeLocacaoSemFilme() throws FilmeSemEstoqueException, LocadoraException {
        // Cenário
        LocacaoController locacao2 = new LocacaoController(locacaoRepository);
        Usuario usuario = new Usuario("Roger");
        List<Filme> filme = null;
        Locacao locacao = new Locacao();
        locacao.setUsuario(usuario);
        locacao.setFilmes(filme);

        // Ação
        locacao2.alugarFilme(locacao, m);
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void testeLocacaoFilmeSemEstoque() throws FilmeSemEstoqueException, LocadoraException {
        // Cenário
        LocacaoController locacao3 = new LocacaoController(locacaoRepository);
        Usuario usuario = new Usuario("Roberto");
        Filme filme = new Filme("Crossroads", 0, 4.0);
        List<Filme> filmes = new ArrayList<>();
        //System.out.println(filme);
        filmes.add(filme);
        //System.out.println(filmes);
        Locacao locacao = new Locacao();
        locacao.setUsuario(usuario);
        locacao.setFilmes(filmes);

        // Ação
        locacao3.alugarFilme(locacao, m);
    }

    @Test
    public void testePaga75PorcentoNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
        // Cenário
        LocacaoController locacao4 = new LocacaoController(locacaoRepository);
        Usuario usuario = new Usuario("Ronaldo");
        Filme filme = new Filme("Crossroads", 2, 4.0);
        Filme filme2 = new Filme("Jogos Mortais I", 2, 4.0);
        Filme filme3 = new Filme("Jogos Mortais II", 2, 4.0);
        List<Filme> filmes = new ArrayList<>();
        filmes.add(filme);
        filmes.add(filme2);
        filmes.add(filme3);
        System.out.println(filmes);
        Locacao locacao = new Locacao();
        locacao.setUsuario(usuario);
        locacao.setFilmes(filmes);

        // Ação
        Locacao loc = locacao4.alugarFilme(locacao, m);
        System.out.println(loc.getValor());

        // Validação
        assertEquals(11, loc.getValor(), 0.001);
    }


}