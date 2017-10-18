/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.logging.Level;
import java.util.logging.Logger;
import local.locadora.dao.FilmeDAO;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.FilmeSemNomeException;
import local.locadora.exceptions.FilmeSemPrecoException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class TesteFilmeNovo {
     @Autowired
    private FilmeDAO clientRepository;

    public TesteFilmeNovo() {
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
    public void naoDeveFilmeSemNome()throws FilmeSemNomeException{
       
        //cenario
        String nome = "";
        int estoque = 4;
        double valor = 5.5;
        String retorna;
        Filme filme = new Filme();
        //ação
        try {
            retorna = filme.setNome(nome);
      
        } catch (FilmeSemNomeException e) {
            Assert.assertEquals("Filme sem nome", e.getMessage());
        }
        
        
    }
    
    @Test
    public void naoDeveFilmeComNome(){
        
        String nome = "Macgayver";
        int estoque = 3;
        double valor = 5.5;
        String retorna;
        
        Filme filme = new Filme();
        
        try{
            retorna = filme.setNome(nome);
        }catch(FilmeSemNomeException e){
            Assert.assertEquals("Filme com nome", e.getMessage());
        }
        
    }
    
    @Test
    public void naoDeveFilmeComQuantidade() throws FilmeSemNomeException{
          String nome = "Macgayver";
        int estoque = 3;
        double valor = 5.5;
        String retorna;
        
        Filme filme = new Filme();
        
        try{
            retorna = filme.setEstoque(estoque);
            
        
        } catch (FilmeSemEstoqueException ex) {
             Logger.getLogger(TesteFilmeNovo.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @Test
    public void naoDeveFilmeSemEstoque() throws FilmeSemEstoqueException{
        String nome = "Macgayver";
        int estoque = 0;
        double valor = 5.5;
        String retorna;
        
        Filme filme = new Filme();
        
        try{
            retorna = filme.setEstoque(estoque);           
        
        }catch(FilmeSemEstoqueException e){
              Assert.assertEquals("Filme sem estoque", e.getMessage());
        }
    }
    
     @Test
    public void naoDeveFilmeSemValor() throws FilmeSemPrecoException{
        String nome = "Macgayver";
        int estoque = 3;
        double valor = 0;
        String retorna;
        
        Filme filme = new Filme();
        
        try{
            retorna = filme.setPrecoLocacao(valor);
        
        }catch(FilmeSemPrecoException e){
              Assert.assertEquals("Filme sem preço", e.getMessage());
        }
    }
    
       @Test
    public void naoDeveFilmeComValor() throws FilmeSemPrecoException{
        String nome = "Macgayver";
        int estoque = 3;
        double valor = 5.5;
        String retorna;
        
        Filme filme = new Filme();
        
        try{
            retorna = filme.setPrecoLocacao(valor);
        
        }catch(FilmeSemPrecoException e){
              Assert.assertEquals("Filme com valor", e.getMessage());
        }
    }
    
}
