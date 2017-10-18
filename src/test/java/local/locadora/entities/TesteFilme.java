/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import local.locadora.dao.FilmeDAO;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.FilmeSemNomeException;
import local.locadora.exceptions.FilmeSemPrecoException;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author william
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)

public class TesteFilme {
    
    @Autowired
    private FilmeDAO clientRepository;

    public TesteFilme() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
  @Mock
  private Filme filme;
  
  @Test
  public void testeFilme() throws FilmeSemNomeException, FilmeSemEstoqueException, FilmeSemPrecoException{
    when(filme.setNome("macgayver")).thenReturn("Filme com nome");
    when(filme.setNome("")).thenReturn("Filme sem nome");
    when(filme.setEstoque(3)).thenReturn("Tem em estoque");
    when(filme.setEstoque(0)).thenReturn("Filme sem estoque");
    when(filme.setPrecoLocacao(4.8)).thenReturn("Filme com preço");
    when(filme.setPrecoLocacao(0.0)).thenReturn("Filme sem preço");
   
    
    Assert.assertEquals("Filme com nome", filme.setNome("macgayver"));
    Assert.assertEquals("Filme sem nome", filme.setNome(""));
    Assert.assertEquals("Tem em estoque", filme.setEstoque(3));
    Assert.assertEquals("Filme sem estoque", filme.setEstoque(0));
    Assert.assertEquals("Filme com preço", filme.setPrecoLocacao(4.8));
    Assert.assertEquals("Filme sem preço", filme.setPrecoLocacao(0.0));
}
   
  
 
  
  
    
}
