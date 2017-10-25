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
import local.locadora.dao.ClienteDAO;
import local.locadora.dao.FilmeDAO;

import static org.hamcrest.core.Is.is;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import org.springframework.beans.factory.annotation.Autowired;

public class ClienteEntityTest {

    private static Validator validator;

     @Autowired
    private ClienteDAO clienteRepository;
    private FilmeDAO filmeRepository;
    
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void naoDeveValidarUmNomeComDoisCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("di");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
       
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
     @Test
    public void DeveValidarCpf() {
        Cliente cli = new Cliente("DioMelo", " ");
  
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cli);
        Iterator it = violations.iterator();
 
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message,is("O CPF não é válido"));
    }
    
      @Test
    public void testaClienteNomeNaoPodeCaracterEspecial() {
        Cliente cliente = new Cliente();
        cliente.setNome("#$%1234");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
 
        assertThat(message,is("O nome não deve possuir simbolos ou números"));

    }
    
    
    @Test
    public void testaClienteMesmoNomeNaoPode() {
        Cliente cliente = new Cliente("DioMelo");
        Cliente cli1 = clienteRepository.save(cliente);
        Cliente c = clienteRepository.findByNome(cli1.getNome());
        Cliente cliente2 = new Cliente("DioMelo");
        Cliente cli2 = clienteRepository.save(cliente2);
        Cliente c1 = clienteRepository.findByNome(cli2.getNome());
        boolean var = false;
        if (c != null && c1 != null) {
            var = true;
        }
        Assert.assertFalse(var);
    }
    
    
    @Test
    public void testaClienteNaoPodeEspacos() {
        Cliente cliente = new Cliente(" Dionatan ");
        Cliente c1 = clienteRepository.save(cliente);
        Cliente c = clienteRepository.findByNome(c1.getNome());
        boolean var = false;
        if(c != null){
            var = true;
        }
        Assert.assertFalse(" Dionatan ", var); 
    }
    
    
 
}
