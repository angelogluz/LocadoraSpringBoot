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
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
    public void deveAceitarCPFSemSeparadores(){
        //cenario
        Cliente user = new Cliente();
        user.setCpf("03068603099");
        
        //ação
        clienteRepository.save(user);
        
        
    }
    
    @Test(expected = Exception.class)
    public void oNomeDoClienteDeveSerUmCampoUnico(){
        //cenario
        Cliente user1 = new Cliente();
        Cliente user2 = new Cliente();
        user1.setNome("Samuel");
        user1.setCpf("03068603099");
        user2.setNome("Samuel");
        user2.setCpf("03068603099");
        
        //ação
        clienteRepository.save(user1);
        clienteRepository.save(user2);        
        fail("deveria gerar exceção");
        
        
    }
    @Test
    public void oNomeDoClienteDeveSerArmazenadoComAPrimeiraLetraDoNomeMaiuscula(){
        //cenario
        Cliente user1 = new Cliente();
        Cliente user2 = new Cliente();
        user1.setNome("joao peil");
        user1.setCpf("03068603099");
        
        //ação
        clienteRepository.save(user1);
        Cliente u = clienteRepository.findByNome("joao peil");
        String nomevalidado = u.getNome();
        //validação
        assertThat(nomevalidado, is("Joao Peil"));
        
        
    }

}
