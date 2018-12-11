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
    public void naoValidarSeNomeTemNumerosOuSimbolos() {
        Cliente cliente = new Cliente();
        cliente.setNome("Sergioo !123");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }

    @Test
    public void naoValidarCPF() {
        Cliente cliente = new Cliente();
        cliente.setNome("Sergioo");
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
        cliente.setNome("Sergioo");
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
        cliente.setNome("  Sergioo  ");
        assertThat(cliente.getNome(), is("Sergioo"));
    }

    @Test
    public void primeiraLetraDoNomeSobrenomeDeveSerMaiuscula() {

//        Cliente cliente = new Cliente();
//        cliente.setNome("Sergioo santos");
//        
//        assertThat(cliente.getNome(), is("Sergioo Santos"));
//        assertEquals("Sergioo Santos", cliente.getNome());
//        assertTrue(cliente.getNome().equals("Sergioo Santos"));
        try {
            Cliente cliente = new Cliente();
            cliente.setNome("Sergioo santos");

            assertThat(cliente.getNome(), is("Sergioo Santos"));

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void nomeDoClienteDeveSerCampoUnico() {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();

        try {
            cliente1.setNome("Sergioo");
            cliente2.setNome("Sergioo");
            Assert.fail();

        } catch (Exception e) {
            Object ExceptionCliente = null;
            Assert.assertSame(ExceptionCliente, e);
        }
    }
}