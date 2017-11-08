package local.locadora.dao;

/**
 *
 * @author Luiz Buchvaitz
 */
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.dao.ClienteDAO;
import local.locadora.entities.Cliente;
import local.locadora.entities.Cliente;
import static org.assertj.core.api.Fail.fail;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ClienteTest {

    @Autowired
    private ClienteDAO clientRepository;
    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test(expected = ConstraintViolationException.class)
    public void testControllerInserindoClienteComMinimoAceitavelDeCaracteresNoNome() {
        Cliente user = new Cliente();
        user.setNome("As");
        clientRepository.save(user);
        Cliente u = clientRepository.findByNome("As");
        fail("Não deveria ter persistido o nome com 3 caracteres");
    }

    @Test
    public void cpfNaoObrigatorioMasPreenchidoExibeMensagemNaoEValido() {
        Cliente cliente = new Cliente();
        cliente.setNome("Luiz");
        cliente.setCpf("123.456.789-07");
        clientRepository.save(cliente);
        fail("O CPF não é valido");

    }

    @Test
    public void naoDeveConterCaracterEspeciais() {
        Cliente cliente = new Cliente("Lu!z Zun!n@");
        Cliente c1 = clientRepository.save(cliente);
        Cliente c2 = clientRepository.findByNome("Lu!z Zun!n@");

        assertTrue(c2.getNome().matches("[a-z A-Z]*"));
    }

    @Test

    public void naoDeveConterNumeroNoNome() {
        Cliente cliente = new Cliente("1212 1212");
        Cliente c1 = clientRepository.save(cliente);
        Cliente c2 = clientRepository.findByNome("1212 1212");

        assertFalse(c2.getNome().matches("([0-9]+[ ]*)+"));
    }

    @Test
    public void testClienteComNomeEmBranco() {
        try {
            Cliente cliente = new Cliente("");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Não é possível cadastrar usuário sem nome!");
        }
    }

    @Test
    public void espacoNoInicioDoNome() {
        Cliente cliente = new Cliente();
        cliente.setNome(" Luiz ");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        while (it.hasNext()) {
            ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
            String message = x.getMessage();
            assertTrue(cliente.getNome().equals("Angelo"));
        }
    }

    @Test
    public void naoDeveraAceitarEspacoEmBrancoNoInicioNemNoFimDoNome() {
        Cliente cliente = new Cliente();
        cliente.setNome(" Luiz ");
        assertEquals("Luiz", cliente.getNome());
    }

    @Test
    public void cpfDeveraPersistirSemSeparadores() {
        //Cenário
        Cliente cliente = new Cliente();
        //Ação
        cliente.setCpf("005.506.790-57");
        //Validação
        assertEquals("00550679057", cliente.getCpf());
    }

}
