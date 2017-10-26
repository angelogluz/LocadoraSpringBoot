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
    public void deveValidarCPF (){
        
        
        //cenário
        Cliente user = new Cliente();        
        
        //ação
        user.setNome("João");
        user.setCpf("12121212");
        
        //validação
        Set<ConstraintViolation<Cliente>> violations = validator.validate(user);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O CPF não é válido"));
    }
    
    @Test
    public void naoDeveAceitarCarcteresEspeciaisNemNumerosNoNome(){
         //cenário
        Cliente cliente = new Cliente();        
        
        //ação
        cliente.setNome("P@t0");
        
        //validação
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }
    
    @Test
    public void naoDeveAceitarEspaçosNoInicioENoFimDoNome(){
         //cenário
        Cliente cliente = new Cliente();        
        
        //ação
        cliente.setNome(" João ");
    }
    
        
    
    
}
