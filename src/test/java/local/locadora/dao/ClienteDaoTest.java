package local.locadora.dao;

import local.locadora.entities.Cliente;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Fail.fail;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ClienteDaoTest {


    @Autowired
    ClienteDAO clienteRepository;


    @Test(expected = ConstraintViolationException.class)
    public void testControllerInserindoClienteComMinimoAceitavelDeCaracteresNoNome() {
        Cliente user = new Cliente();
        user.setNome("As");
        clienteRepository.save(user);
        Cliente u = clienteRepository.findByNome("As");
        fail("Não deveria ter persistido o nome com 3 caracteres");
    }
    
    @Test
    public void cpfDevePersistirSemSeparadores() {
        Cliente user = new Cliente();
        user.setNome("Bruna Almeida");
        user.setCpf("033.151.101-07");
        clienteRepository.save(user);
        
        Cliente u = clienteRepository.findByNome("Bruna Almeida");
        String cpf = u.getCpf();
        
        assertTrue(cpf.equalsIgnoreCase("03315110107"));
        
        
    }
    
    @Test(expected = Exception.class)
    public void campoNomeDeveSerUnico() {
        Cliente user1 = new Cliente();
        user1.setNome("Bruna Nobre");
        
        clienteRepository.save(user1);
        
        Cliente user2 = new Cliente();
        user2.setNome("Bruna Nobre");
        
        clienteRepository.save(user2);
        fail("Não deveria ter salvo dois nomes iguais");
        
    }
    
    

}
