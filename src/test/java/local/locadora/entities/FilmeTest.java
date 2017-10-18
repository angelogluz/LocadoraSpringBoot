/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Pedro Cantarelli
 */
public class FilmeTest {

    @Test
    public void naoAceitarEstoqueNegativo() {
        Filme filme = new Filme("Teste teste", 2, 2.50);

        assertTrue(filme.getEstoque() > 0);
    }

    @Ignore
    @Test
    public void naoAceitarPrecoLocacaoNegativo() {
        Filme filme = new Filme("Teste teste", 2, -2.50);

        assertTrue(filme.getPrecoLocacao() > 0);
    }

    @Ignore
    @Test
    public void naoAceitarSomenteEspacoNoNome() {
        Filme filme = new Filme(" ", 2, 2.50);

        assertFalse(filme.getNome().matches("[ ]*"));
    }
}
