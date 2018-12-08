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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
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

    
    @Test
    public void naoPodeValidarAbaixoDeDoisCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cu");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }

    @Test
    public void naoPodeValidarAcimaDeCinquentaCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("testesabcdefghijklmnopqrstuvxzABCDEFGHIJKLMNOPQRSTUVXZTestes");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }

    @Test
    public void validarSeNomeTemNumerosOuSimbolos() {
        Cliente cliente = new Cliente();
        cliente.setNome("Godinilson 1!2>3º");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }

    @Test
    public void naoValidarCPFComMenosDeOnceDigitos() {
        Cliente cliente = new Cliente();
        cliente.setNome("Godinilson");
        cliente.setCpf("123456789");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O CPF não é válido"));
    }
    
    @Test
    public void naoValidarCPFcomMaisDeOnceDigitos() {
        Cliente cliente = new Cliente();
        cliente.setNome("Godinilson");
        cliente.setCpf("12345678910");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O CPF não é válido"));
    }

    @Test
    public void naoValidarCPFBranco() {
        Cliente cliente = new Cliente();
        cliente.setNome("Godinilson");
        cliente.setCpf("");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O CPF não é válido"));
    }

    @Test
    public void naoDeveAceitarEspacoBrancoInicioFim() {
        Cliente cliente = new Cliente();
        cliente.setNome("  Godinilson  ");
        assertThat(cliente.getNome(), is("Godinilson"));
    }

    @Test
    public void primeiraLetraNomeSobrenomeMaiuscula() {

        try {
            Cliente cliente = new Cliente();
            cliente.setNome("godi nilson");

            assertThat(cliente.getNome(), is("Godi Nilson"));

        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void nomeClienteUnico() {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();

        try {
            cliente1.setNome("Godinilson");
            cliente2.setNome("Godinilson");
            Assert.fail();

        } catch (Exception e) {
            Object ExceptionCliente = null;
            Assert.assertSame(ExceptionCliente, e);
        }
    }
}