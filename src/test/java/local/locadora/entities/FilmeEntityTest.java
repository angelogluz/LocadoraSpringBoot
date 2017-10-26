/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
 * @author Ana Paula
 */
public class FilmeEntityTest {
    
    private static Validator validator;
        
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
   @Test
    public void valorDoFilmeNaoPodeSerNegativo(){
        double preco = -10.0;
        
        Filme filme = new Filme();
        filme.setPrecoLocacao(preco);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Valor da locação deve ser positivo"));	
    }
    
    @Test
    public void filmeDeveConterDe2A100Caracteres(){
        String nome = "a";
        
        Filme filme = new Filme();
        filme.setNome(nome);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));	
    }
    
    @Test
    public void valorDoEstoqueNaoPodeSerNegativo(){
        int estoque = -1;
        
        Filme filme = new Filme();
        filme.setEstoque(estoque);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Estoque deve ser positivo"));	
    }
}
