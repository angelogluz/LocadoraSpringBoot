/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Iterator;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lucas
 */
public class FilmeEntityTest {
    
    private static Validator validator;
    
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void nomeNaoDeveTerEspacosInicioEfim() {
        Filme filme = new Filme();
        filme.setNome(" Filme 1 ");
        filme.setEstoque(10);
        filme.setPrecoLocacao(3.0);
        
        assertEquals("Filme 1", filme.getNome());
    }
    
    @Test
    public void filmeDeveTerEntre2e100Caracteres() {
        Filme filme = new Filme();
        filme.setNome("A");
        filme.setEstoque(10);
        filme.setPrecoLocacao(3.0);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
    public void estoqueFilmeDeveSerPositivo() {
        Filme filme = new Filme();
        filme.setNome("Filme 1");
        filme.setEstoque(-10);
        filme.setPrecoLocacao(3.0);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Estoque deve ser positivo"));
    }
    
    @Test
    public void precoNaoDeveTerMaisDeDoisDigitos() {
        Filme filme = new Filme();
        filme.setNome("Filme 1");
        filme.setEstoque(10);
        filme.setPrecoLocacao(300.00);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void precoLocacaoDeveSerPositivo() {
        Filme filme = new Filme();
        filme.setNome("Filme 1");
        filme.setEstoque(10);
        filme.setPrecoLocacao(-3.0);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Valor da locação deve ser positivo"));
    }
}