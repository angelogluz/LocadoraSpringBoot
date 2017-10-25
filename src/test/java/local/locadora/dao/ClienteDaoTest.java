package local.locadora.dao;

import local.locadora.entities.Cliente;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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
    public void naoDeveValidarUmNomeComEspacosNoInicoOuFim() {
        Cliente cliente = new Cliente();
        cliente.setNome(" Vargas");
        clienteRepository.save(cliente);

        Cliente c = clienteRepository.findByNome("Vargas");
        assertThat(c.getNome(), is("Vargas"));
    }

    @Test
    public void naoDeveValidarUmNomeComPrimeiraLetraMaiuscula() {
        Cliente cliente = new Cliente();
        cliente.setNome("anamelia");
        clienteRepository.save(cliente);

        Cliente c = clienteRepository.findByNome("Anamelia");
        assertThat(c.getNome(), is("Anamelia"));
    }



}
