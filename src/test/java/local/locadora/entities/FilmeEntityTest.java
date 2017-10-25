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
     public void testeFilmeEspaçoEmBranco() {
         
         
         
        Filme filme = new Filme();
        filme.setNome("  lagoa  ");
        

        

        assertThat(filme.getNome(), is("lagoa"));
    }
     
    
    
     @Test
     public void testeNomeTamanhoMin() {
         
         
         
        Filme filme = new Filme();
        filme.setNome("L");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
     
      @Test
     public void testeNomeTamanhoMax() {
         
         
         
        Filme filme = new Filme();
        filme.setNome("LagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoaLagoa");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
     
      @Test
     public void testeFilmeEstoqueNegativo() {
         
         
         
        Filme filme = new Filme();
        filme.setEstoque(-1);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O Estoque deve ser positivo"));
    }
     
     
      @Test
     public void testeFilmeValorLocacaoNegativo() {
         
         
         
        Filme filme = new Filme();
        filme.setPrecoLocacao(-20.0);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O Valor da locação deve ser positivo"));
    }
     
     @Test
     public void testeFilmeValorLocacaoTamanho() {
         
         
         
        Filme filme = new Filme();
        filme.setPrecoLocacao(200000.000);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
     
   
     
     
     
     
     
     
}
