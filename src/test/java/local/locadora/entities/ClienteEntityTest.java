package local.locadora.entities;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.dao.ClienteDAO;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)

public class ClienteEntityTest {

    private static Validator validator;
    
    @Autowired
    private ClienteDAO clienteRepository;

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

        assertThat(message,is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void testNomeDeveSerUnico() {
        
        try {
            Cliente cliente1 = new Cliente("Mussum");
            Cliente clienteSave1 = clienteRepository.save(cliente1);
            
            Cliente cliente2 = new Cliente("Mussum");
            Cliente clienteSave2 = clienteRepository.save(cliente2);
            
            Assert.fail();
        } catch (Exception e) {
        }
    }
    
    @Test
    public void testNomeNaoDeveAceitarEspacosEmBrancoNoInicioENoFim() {
        
        try {
            Cliente cliente = new Cliente(" Mussum ");
            Assert.fail();
        } catch (Exception e) {
        }
    }
}
