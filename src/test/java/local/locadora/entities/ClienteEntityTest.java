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
    public void naoValidarAbaixoDeQuatroCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("Quatro");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }

    @Test
    public void naoValidarAcimaDeCinquentaCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("testedecinquentacaracteresssssaaaaaaaaaabbbbbbbbbbc");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }

    @Test
    public void naoValidarNomeComNumerosOuSimbolos() {
        Cliente cliente = new Cliente();
        cliente.setNome("Igor Luíz");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }

    @Test
    public void naoValidarCPFComMenosDeOnzeDigitos() {
        Cliente cliente = new Cliente();
        cliente.setNome("Martyr");
        cliente.setCpf("123456789");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O CPF não é válido"));
    }

    @Test
    public void naoValidarCPFVazio() {
        Cliente cliente = new Cliente();
        cliente.setNome("Martyr");
        cliente.setCpf("");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O CPF não é válido"));
    }

    @Test
    public void naoAceitarEspacoBrancoInicioFim() {
        Cliente cliente = new Cliente();
        cliente.setNome("  Igor  ");
        assertThat(cliente.getNome(), is("Igor"));
    }

    @Test
    public void primeiraLetraNomeSobrenomeTemQueSerMaiuscula() {

        try {
            Cliente cliente = new Cliente();
            cliente.setNome("igor silva");

            assertThat(cliente.getNome(), is("Igor Silva"));

        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void nomeClienteUnico() {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();

        try {
            cliente1.setNome("Igor");
            cliente2.setNome("Igor");
             Assert.assertSame(cliente1.getNome(), cliente2.getNome());
             Assert.fail("Sistema não cumpre com os requisitos");

        } catch (Exception e) {
            Object ExceptionCliente = null;
            Assert.assertNotSame(cliente1.getNome(), cliente2.getNome());
        }
    }
}