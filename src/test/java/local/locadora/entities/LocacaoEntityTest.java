/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jones
 */
public class LocacaoEntityTest {

    private static Validator validator;

    public LocacaoEntityTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void naoDeveValidarUmaLocacaoSemCliente() {
        //Cenário
        Locacao locacao = new Locacao();
        Date dataRetorno = new Date();
        locacao.setDataRetorno(dataRetorno);
        locacao.setCliente(null);
        
        //Ação
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        //Validação
        assertThat(message, is("Um cliente deve ser selecionado"));
    }

    @Test
    public void naoDeveValidarUmaLocacaoSemPeloMenosUmFilme() {
        //Cenário
        Locacao locacao = new Locacao();

        //Ação
        locacao.setFilmes(null);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        //Validação
        assertThat(message, is("Pelo menos um filme deve ser selecionado"));
    }

    @Test
    public void naoDeveValidarUmaLocacaoDeFilmeSemEstoque() {
        //Cenário
        Locacao locacao = new Locacao();
        Filme filme = new Filme("Matrix", 0, 4.0);

        //Ação
        try {
            locacao.setFilmes((List<Filme>) filme);
            fail();
        } catch (Exception e) {
            Object ExceptionLocacao = null;
            
            Assert.assertSame(ExceptionLocacao, e);
        }
    }
    
    public void naoDeveValidarUmaLocacaoSemDataDeDevolucao() {
        //Cenário
        Locacao locacao = new Locacao();
        Cliente cliente = new Cliente();
        List<Filme> filmes = null;
        Filme filme = new Filme("Matrix", 0, 4.0);
        filmes.add(filme);
        
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setDataRetorno(null);
        
        //Ação
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        //Validação
        assertThat(message, is("A data de retorno não deve ser nula"));
    }
    
    public void naoDeveValidarUmaLocacaoSemDataDeLocacao() {
        //Cenário
        Locacao locacao = new Locacao();
        Cliente cliente = new Cliente();
        List<Filme> filmes = null;
        Filme filme = new Filme("Matrix", 0, 4.0);
        filmes.add(filme);
        Date dataLocacao = new Date();
        
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setDataLocacao(dataLocacao);
        
        //Ação
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        //Validação
        assertThat(message, is("A data deve retorno deve ser futura"));
    }
    
}
