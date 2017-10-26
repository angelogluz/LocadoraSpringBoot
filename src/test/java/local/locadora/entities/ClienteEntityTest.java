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
import javax.validation.ConstraintViolationException;
import static org.assertj.core.api.Fail.fail;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ClienteEntityTest {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void naoDeveValidarUmNomeComDoisCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("An");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void naoDeveValidarCpfInvalido() {
        Cliente cliente = new Cliente();
        cliente.setCpf("033.151.101-24");
        
         Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O CPF não é válido"));
    }
            
        @Test
    public void oNomeNaoDevePossuirCaracteresEspeciais() {
        Cliente cliente = new Cliente();
        cliente.setNome("Jo %&*");
        
         Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }
    
    @Test
    public void aAplicacaoDeveEliminarOsEspacosDoNome() {
        Cliente cliente = new Cliente();
        cliente.setNome(" Bruna Nobre ");
        
        String clienteNome = cliente.getNome();
        
        assertTrue(clienteNome.equals("Bruna Nobre"));
    }
    
     @Test
    public void primeirasLetrasDoNomeSempreEmMaiuscula() {
        Cliente cliente = new Cliente();
        cliente.setNome("bruna nobre");
        
        String clienteNome = cliente.getNome();
         assertThat(clienteNome, is("Bruna Nobre"));
        //assertTrue(clienteNome.equals("Bruna Nobre"));
    }
    
    
  
  
}
