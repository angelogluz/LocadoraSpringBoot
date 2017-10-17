package local.locadora.controller;

import local.locadora.entities.Locacao;
import local.locadora.entities.Usuario;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.LocadoraException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import static org.junit.Assert.*;

public class LocacaoControllerTest {
    @Autowired
    Model m;

    @Test(expected = LocadoraException.class)
    public void testeLocacaoSemUsuario() throws FilmeSemEstoqueException, LocadoraException {

        // Cenário
        LocacaoController locacao1 = new LocacaoController();
        Usuario usuario = null;
        Locacao locacao = new Locacao();
        locacao.setUsuario(usuario);

        // Ação
      locacao1.alugarFilme(locacao, m);

        // Validação
    }
}