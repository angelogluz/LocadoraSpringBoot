package local.locadora.dao;

import local.locadora.controller.ClienteController;
import local.locadora.entities.Cliente;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Fail.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    public void cpfNaoObrigatorioMasPreenchidoExibeMensagemNaoEValido() {
        Cliente user = new Cliente();
        user.setNome("Luiz");
        user.setCpf("123.456.789-07");
        clienteRepository.save(user);
        fail("O CPF não é valido");

    }

    @Test
    public void naoDeveConterCaracterEspeciais() {
        Cliente user = new Cliente("Lu!z Zun!n@");
        Cliente c1 = clienteRepository.save(user);
        Cliente c2 = clienteRepository.findByNome("Lu!z Zun!n@");

        assertTrue(c2.getNome().matches("[a-z A-Z]*"));
    }

    @Test

    public void naoDeveConterNumeroNoNome() {
        Cliente user = new Cliente("1212 1212");
        Cliente c1 = clienteRepository.save(user);
        Cliente c2 = clienteRepository.findByNome("1212 1212");

        assertFalse(c2.getNome().matches("([0-9]+[ ]*)+"));
    }

    @Test
    public void testClienteComNomeEmBranco() {
        try {
            Cliente user = new Cliente("");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Não é possível cadastrar usuário sem nome!");
        }
    }
}
