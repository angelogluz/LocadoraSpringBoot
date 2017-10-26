/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author aluno
 */
public class FilmeEntityTest {
    
    private static Validator validator;
    
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void naoDeveValidarUmNomeComMaisDeUmCampo() {
        Filme filme = new Filme();
        try {
            filme.setNome("Armagedom O Retorno");
            
            Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
            Iterator it = violations.iterator();
            ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
            String message = x.getMessage();
        
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }
    }
    
    @Test
    public void nomeDeveSerGravadoSemEspacos() {
        Filme filme = new Filme();
        filme.setNome(" Marco ");
        
        assertThat(filme.getNome(), is("Marco"));
    }
    
    @Test
    public void nomeDeveTerMaximo100Caracteres() {
        Filme filme = new Filme();
        String nome = "";
        for (int i = 0; i < 101; i++) {
            nome += "a";
        }
        filme.setNome(nome);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
            Iterator it = violations.iterator();
            ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
            String message = x.getMessage();
        
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
    public void nomeDeveTerMinimo2Caracteres() {
        Filme filme = new Filme();
        
        filme.setNome("a");
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
    public void naoDeveTerEstoqueComValorNegativo() {
        Filme filme = new Filme();
        
        filme.setNome("Foo");
        filme.setEstoque(-5);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Estoque deve ser positivo"));
    }
    
    @Test
    public void naoDeveTerPrecoCentenario() {
        Filme filme = new Filme();
        
        filme.setNome("Foo");
        filme.setPrecoLocacao(100.00);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void deveTerPrecoComDuasCasasDecimias() {
        Filme filme = new Filme();
        
        filme.setNome("Foo");
        filme.setPrecoLocacao(99.0);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void naoDeveTerPrecoComValorNegativo() {
        Filme filme = new Filme();
        
        filme.setNome("Foo");
        filme.setPrecoLocacao(-99.00);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Valor da locação deve ser positivo"));
    }
    
}
