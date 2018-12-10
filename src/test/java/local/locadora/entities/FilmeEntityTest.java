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
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Matheus Vieira
 */
public class FilmeEntityTest {
    
    private static Validator validator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void nomeDoFilmeNaoDeveTerEspacosEmBranco(){
        String nome = "Os Trapalhoes";
        Filme filme = new Filme();
        filme.setNome(nome);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(filme.getNome(), is("OsTrapalhoes"));
    }
    
    @Test
    public void filmeDeveTerMaisQue2MenosQue100Caracteres(){
        String nome = "K";
        Filme filme = new Filme();
        filme.setNome(nome);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
    public void oEstoqueDoFilmeNaoPodeSerNegativo(){
        int estoque = -1;
        Filme filme = new Filme();
        filme.setEstoque(estoque);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Estoque deve ser positivo"));
    }
    
    @Test
    public void oValorDaLocacaoDeveSerPositivo(){
        double valor = -10.00;
        Filme filme = new Filme();
        filme.setPrecoLocacao(valor);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Valor da locação deve ser positivo"));
    }
    
    @Test
    public void oPrecoDaLocacaoDeveTerDoisDigitos(){
        double preco = 999.00;
        Filme filme = new Filme();
        filme.setPrecoLocacao(preco);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
}
