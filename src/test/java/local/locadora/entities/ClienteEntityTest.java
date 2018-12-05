
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
    
    //O campo nome deve ser um valor entre 4 e 50, inclusive Mensagem de validação: "Um nome deve possuir entre 4 e 50 caracteres";
    //****************************************************************************************************************************
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
    
    //Testando 51 caracteres
     @Test
    public void naoDeveValidarUmNomeComCinquentaUmCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("qwertyuioxqwertyuioxqwertyuioxqwertyuioxqwertyuioxq");
        
          Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    //Testando com quatro caracteres
      @Test
    public void DeveValidarUmNomeComQuatroCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("Rafa");
        
            Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
        
}

