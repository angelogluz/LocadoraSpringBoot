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
import static org.junit.Assert.*;
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
    public void naoDeveValidarUmNomeComMaisDeCinquentaCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("Angelo da Luz Pereira de Souza Santos Da Roza Porto");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }

    @Test
    public void testaSeOCPFEValido() {
        Cliente cliente = new Cliente();
        cliente.setNome("Angelo");
        cliente.setCpf("656546588");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("O CPF não é válido"));
    }

    @Test
    public void testaSeONomeDoClienteTemNumeros() {
        Cliente cliente = new Cliente();
        cliente.setNome("Angelo 4548");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    } 
    @Test
    public void testaSeExcluiEspacosNoInicioDoNome() {
        Cliente cliente = new Cliente();
        cliente.setNome(" Angelo ");
//        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
//        Iterator it = violations.iterator();
////        while(it.hasNext()){
//        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
//        String message = x.getMessage();
        // }
        assertTrue(cliente.getNome().equals("Angelo"));
    }
    
    @Test
    public void testaSeAPrimeiraLetraDoNomeFicaMaiuscula() {
        Cliente cliente = new Cliente();
        cliente.setNome("angelo");
//        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
//        Iterator it = violations.iterator();
//        while(it.hasNext()){
//        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
//        String message = x.getMessage();
//         }
        assertTrue(cliente.getNome().equals("Angelo"));
    }
    
    @Test
    public void testaSeOCampoCPFPodeFicarEmBranco() {
        Cliente cliente = new Cliente();
        cliente.setNome("Angelo");
        cliente.setCpf("");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is(""));
    }
}
