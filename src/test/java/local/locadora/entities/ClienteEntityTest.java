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
import org.junit.Assert;
import static org.junit.Assert.*;

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
    public void cpfDeveSerValido() {
        Cliente cliente = new Cliente();
        cliente.setNome("Lucas");
        cliente.setCpf("030.919.520-xx");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O CPF não é válido"));
    }
    
    @Test
    public void nomeDeveSerEntre4e50Caracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("Luc");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void naoPodeTerCaracteresEspeciaisOuNumeros() {
        Cliente cliente = new Cliente();
        cliente.setNome("Lucas123");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }
    
    @Test
    public void naoPodeEspacosInicioFimDoNome() {
        Cliente cliente = new Cliente();
        cliente.setNome(" Lucas");
        
        assertEquals("Lucas", cliente.getNome());
    }
    
    @Test
    public void primeiraLetraDeveSerMaiuscula() {
        Cliente cliente = new Cliente();
        cliente.setNome("lucas");
        
        assertEquals("Lucas", cliente.getNome());
    }
}