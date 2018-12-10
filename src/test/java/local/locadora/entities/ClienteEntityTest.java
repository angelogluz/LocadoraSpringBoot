package local.locadora.entities;

import static java.lang.Math.log;
import java.time.Instant;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolationException;
import local.locadora.exceptions.NomeMenorComTresCaracteres;
import net.bytebuddy.utility.RandomString;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.startsWith;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.internal.matchers.EndsWith;
import org.mockito.internal.matchers.StartsWith;

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
     * Note que <b>validator</b> aplica a validação do bean validation O
     * Iterator é utilizado para pegar as violações ocorridas
     */
    @Test
    public void cpfNaoObrigatorio() {
        Cliente cliente = new Cliente();
        cliente.setNome("abcde");
        cliente.setCpf("");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("O CPF não é válido"));

    }

    @Test
    public void naoValidarCPF() {
        Cliente cliente = new Cliente();
        cliente.setNome("abcdef");
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
    public void naoDeveAceitarNomeCom3Caracteres() {
        Cliente cliente = new Cliente();
        String nome = RandomString.make(3);
        cliente.setNome(nome);

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(cliente.getNome(), is(equalTo(nome)));
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }

    @Test
    public void deveAceitarNomeCom4Caracteres() {

        Cliente cliente = new Cliente();
        String nome = RandomString.make(4);
        cliente.setNome(nome);

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(cliente.getNome(), is(equalTo(nome)));

    }

    @Test
    public void naoDeveAceitarNomeCom51Caracteres() {
        Cliente cliente = new Cliente();
        String nome = RandomString.make(51);
        cliente.setNome(nome);

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(cliente.getNome(), is(equalTo(nome)));
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }

    @Test
    public void deveAceitarNomeCom50Caracteres() {

        Cliente cliente = new Cliente();
        String nome = RandomString.make(50);
        cliente.setNome(nome);

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(cliente.getNome(), is(equalTo(nome)));

    }

    //O nome não deverá possuir símbolo ou número
    @Test
    public void nomeNaoDeveAceitarCaracterEspecial() {
        Cliente cliente = new Cliente();
        cliente.setNome("abdcd@f");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("O nome não deve possuir simbolos ou números"));

    }

    @Test
    public void naoDeveAceitarNomeComNumero() {
        Cliente cliente = new Cliente();
        cliente.setNome("abdcd4f");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("O nome não deve possuir simbolos ou números"));

    }

    @Test
    public void deveAceitarNomeUnico() {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();

        try {
            cliente1.setNome("Abcdef");
            cliente2.setNome("Abcdef");
            Assert.fail();

        } catch (Exception e) {
            Object ExceptionCliente = null;
            Assert.assertSame(ExceptionCliente, e);
        }

    }

    @Test
    public void nomeNaoDeveraAceitarEspacaoEmBrancoInicioFim() {
        Cliente cliente = new Cliente(" abcde ");
        Set< ConstraintViolation< Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(cliente.getNome(), is("abcde"));

    }

    @Test
    public void deveraArmazenadoPrimeiraLetraNomeSobrenomeMaiúscula() {
        Cliente cliente = new Cliente();

        cliente.setNome("rafael garcia");

        String nome = cliente.getNome();

        String partes[] = nome.split(" ");
        String partes1 = partes[0].substring(0, 1).toUpperCase() + partes[1].toLowerCase();
        String partes2 = partes[1].substring(0, 1).toUpperCase() + partes[1].toLowerCase();

        String nomeSobrenome = partes1 + partes2;

        Set< ConstraintViolation< Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is(" "));
    }
}
