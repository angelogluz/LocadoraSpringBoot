package local.locadora.entities;

import local.locadora.exceptions.FilmeException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Felipe
 */
public class FilmeTest {
    
    public FilmeTest() {
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
    
    @Test
    public void filmeNomeVazio() throws FilmeException {
        //Cenário
        Filme filme = new Filme();
        
        //Ação
        try {
            filme.setNome("");
            Assert.fail();
        } catch (FilmeException e) {
            assertEquals("Não é possível cadastrar um filme sem nome.", e.getMessage());
        }
    }
    
    @Test
    public void filmeEstoqueVazio() throws FilmeException {
        //Cenário
        Filme filme = new Filme();
        
        //Ação
        try {
            filme.setEstoque(null);
            Assert.fail();
        } catch (FilmeException e) {
            assertEquals("Não é possível cadastrar um filme sem valor no estoque.", e.getMessage());
        }
    }

    @Test
    public void filmePrecoLocacaoVazio() throws FilmeException {
        //Cenário
        Filme filme = new Filme();
        
        //Ação
        try {
            filme.setPrecoLocacao(null);
            Assert.fail();
        } catch (FilmeException e) {
            assertEquals("Não é possível cadastrar um filme sem valor de locação.", e.getMessage());
        }
    }
}