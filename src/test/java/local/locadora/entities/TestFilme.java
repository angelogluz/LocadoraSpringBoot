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
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author william
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestFilme {
    
        private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void NaoDevePassarFilmeComUmCaracter() {
        Filme filme = new Filme();
        filme.setNome("B");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        //}

        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
       @Test
    public void NaoDeveSerCampoUnico() throws Exception {
        Filme filme = new Filme();
        filme.setNome("B");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        //}
try{
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }catch(Exception e){
        throw new Exception("Campo unico", e);
        
    }
}

    @Test
    public void FilmeComMaisDeDoisEmenosQueCem(){
        
        Filme filme = new Filme();
        filme.setNome("esquadrao");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
        
        
    }
    
    
    @Test
    public void OestoqueDeveSerPositivoVersaoBrasileiraAlamo(){
        
        Filme filme = new Filme();
        filme.setEstoque(2);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
           String message = "O Estoque deve ser positivo";
           
           while(it.hasNext()){
         ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
     
                
                message = x.getMessage();
           }
        assertThat(message, is("O Estoque deve ser positivo"));
    }
    
    
  
}