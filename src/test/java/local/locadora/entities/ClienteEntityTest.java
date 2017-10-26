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
    public void naoDeveValidarUmNomeComTresCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("Ann");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void naoDeveValidarUmNomeComNumeros() {
        Cliente cliente = new Cliente();
        cliente.setNome("Ann123");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }
    
    @Test
    public void naoDeveValidarUmNomeComCaracteresEspeciais() {
        Cliente cliente = new Cliente();
        cliente.setNome("Ann$'");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }
    
    @Test
    public void naoDeveValidarUmNomeComMaisDeUmCampo() {
        Cliente cliente = new Cliente();
        try {
            cliente.setNome("Marco Polo");
            
            Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
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
        Cliente cliente = new Cliente();
        cliente.setNome(" Marco ");
        
        assertThat(cliente.getNome(), is("Marco"));
    }
    
    @Test
    public void nomeDeveSerGravadoComPrimeiraLetraMaiuscula() {
        Cliente cliente = new Cliente();
        cliente.setNome("marco");
        
        assertThat(cliente.getNome(), is("Marco"));
    }
    
    @Test
    public void CpfPreenchidoDeveSerValidado() {
        Cliente cliente = new Cliente ("Fooo", "1234856789");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O CPF não é válido"));
    }
    
    @Test
    public void nomeDeveTerMaximo50Caracteres() {
        Cliente cliente = new Cliente();
        String nome = "";
        for (int i = 0; i < 51; i++) {
            nome += "a";
        }
        cliente.setNome(nome);
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    
}
