
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
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class ClienteEntityTest {

    private static Validator validator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Note que <b>validator</b> aplica a validação do bean validation
     * O Iterator é utilizado para pegar as violações ocorridas
     */
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
    public void naoDeveValidarComMaisDeCinquentaCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("testesabcdefghijklmnopqrstuvxzABCDEFGHIJKLMNOPQRSTUVXZTestes");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void validarSeNomeTemNumerosOuSimbolos() {
        Cliente cliente = new Cliente();
        cliente.setNome("Leandro !123");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }

    @Test
    public void validarCPF() {
        Cliente cliente = new Cliente();
        cliente.setNome("Leandro");
        cliente.setCpf("123456789");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("O CPF não é válido"));
    }

    @Test
    public void naoValidarSeCPFEmBranco() {
        Cliente cliente = new Cliente();
        cliente.setNome("Leandro");
        cliente.setCpf("");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("O CPF não é válido"));
    }
    
    @Test
    public void naoDeveAceitarEspacoEmBrancoNoInicioEnoFim() {
        Cliente cliente = new Cliente();
        cliente.setNome("  Leandro  ");
        assertThat(cliente.getNome(), is("Leandro"));
    }
}

