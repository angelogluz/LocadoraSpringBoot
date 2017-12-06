package local.locadora.controller;

import local.locadora.dao.ClienteDAO;
import local.locadora.entities.Cliente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ClienteControllerTest {

    @Mock
    Model model;

    @Mock
    RedirectAttributes flash;

    @Mock
    BindingResult bind;

    @Mock
    ClienteController controller;

    @Autowired
    ClienteDAO clienteRepository;

    @Before
    public void setup() {
        controller = new ClienteController(clienteRepository);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testControllerNaoDevePersistirComMenosDeQuatroCaracteres() {
        Cliente user = new Cliente();
        user.setNome("Is");
        user.setCpf("014.035.510-30");
        controller.save(user, bind, flash);
        clienteRepository.findByNome("Is");
        fail();
    }

    @Test(expected = ConstraintViolationException.class)
    public void nomeClienteNaoPodeHaverSimbolos() {
        Cliente user = new Cliente();
        user.setNome("Leandrø");
        user.setCpf("012.345.678-90");
        controller.save(user, bind, flash);
        clienteRepository.findByNome("Leandrø");
        fail();
    }

    @Test(expected = ConstraintViolationException.class)
    public void naoPodeHaverCadastroIgualDeCliente() {
        Cliente user = new Cliente();
        user.setNome("leandro");
        user.setCpf("012.345.678-90");
        controller.save(user, bind, flash);

        Cliente user2 = new Cliente();
        user2.setNome("leandro");
        user2.setCpf("012.345.678-90");
        controller.save(user2, bind, flash);
    }
}
