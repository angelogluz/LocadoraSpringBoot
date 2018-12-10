
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
    public void doNotValidadeMoreTwoCaracters() {
        Cliente cliente = new Cliente();
        cliente.setNome("An");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String message = x.getMessage();
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void doNotValidadeMoreFiftyCaracters() {
        Cliente client = new Cliente();
        
        client.setNome("nao deve ter mais que cinquenta caracteres por favor");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(client);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String message = x.getMessage();
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }

    @Test
    public void doNotValidadeNumbersAndSimbols() {
        Cliente client = new Cliente();
        
        client.setNome("Angelo%#$$@67");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(client);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String message = x.getMessage();
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }

    
    @Test
    public void doNotValidateDocumentNumberOnBlank() {
        Cliente client = new Cliente();
        client.setNome("Gladimir");
        client.setCpf("");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(client);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O CPF não é válido"));
    }

    @Test
    public void noSpacesBeginningAndEnd() {
        Cliente client = new Cliente();
        client.setNome("  Miguelis  ");
        assertThat(client.getNome(), is("Miguelis"));
    }

    @Test
    public void nameMustBeUnique() {
        Cliente clientOne = new Cliente();
        Cliente clientTwo = new Cliente();

        try {
            clientOne.setNome("Miguel");
            clientTwo.setNome("Miguel");
            Assert.fail();

        } catch (Exception e) {
            Object ExceptionCliente = null;
            Assert.assertSame(ExceptionCliente, e);
        }
    }
}

