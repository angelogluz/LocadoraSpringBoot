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
import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.assertEquals;
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
public class TestCliente {
    
    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testarCpfNormal() throws Exception{
    Cliente cliente = new Cliente();
    cliente.setCpf("14032343209");
        
    Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
     Iterator it = violations.iterator();
     
     ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
           
     
        assertThat(message, is("O CPF não é válido"));
         
    }
    
    
     public void testarSemCpf() throws Exception{
    Cliente cliente = new Cliente();
    cliente.setCpf("");
        
    Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
     Iterator it = violations.iterator();
      ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("CPF não é válido"));
    }
     
     
     @Test
     public void aceitaVazio(){
         Cliente cliente = new Cliente();
         
         cliente.setCpf("");
         
         assertEquals("", cliente.getCpf());
     }
     
     
     @Test
       public void naoDeveTerSeparadores() {
         
         Cliente cliente = new Cliente();
         
         cliente.setCpf("123.123.443-09");
         
         assertEquals("12312344309", cliente.getCpf());
     }
     
     @Test
     public void naoDeveTerSeparadoresNoFim() {
         
         Cliente cliente = new Cliente();
         
         cliente.setNome(" william ");
         
         assertEquals("william", cliente.getNome());
     }
}
