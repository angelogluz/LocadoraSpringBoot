package local.locadora.entities;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ClienteEntityTest {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void naoDeveValidarUmNomeComMenosDe4OuMaisDe50Caracteres() {
        //cenario
        Cliente cliente = new Cliente();
        //acao
        cliente.setNome("An");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
        //validacao
        assertThat(message,is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void naoDeveValidarUmNomeComCaracteresEspeciais() {
        //cenario
        Cliente cliente = new Cliente();
        //acao
        cliente.setNome("!*?/");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
        //validacao
        assertThat(message,is("O nome não deve possuir simbolos ou números"));
    }
    
    @Test
    public void naoDeveValidarUmNomeComNumeros() {
        //cenario
        Cliente cliente = new Cliente();
        //acao
        cliente.setNome("1234");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
        //validacao
        assertThat(message,is("O nome não deve possuir simbolos ou números"));
    }
    
    @Test
    public void devePersistirCPFSemSeparadores() {
        //cenario
        Cliente cliente = new Cliente();
        //acao
        cliente.setCpf("999.999.999-99");
        //validacao
        assertEquals("99999999999", cliente.getCpf());
    }
    
    @Test
    public void naoDeveValidarNomeComEspacosEmBrancoNoInicioENoFim() {
        //cenario
        Cliente cliente = new Cliente();
        //acao
        cliente.setNome(" Felipe ");
        //validacao
        assertEquals("Felipe", cliente.getNome());
    }
}
