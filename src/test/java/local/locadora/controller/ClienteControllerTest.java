package local.locadora.controller;

import local.locadora.dao.ClienteDAO;
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
    public void testCPFNaoPodeSerInvalido() {
        Cliente user = new Cliente();
        user.setNome("Issis");
        user.setCpf("014.035.510");
        controller.save(user, bind, flash);
//        Cliente userRetorno = clienteRepository.findByNome("Issis");      
//        Assert.assertNotNull(userRetorno);
        fail();
    }

//    O CPF deve ser persistido no banco sem separadores Mensagem de validação: Nenhuma;
    @Test()
    public void testCPFNaoPodeSalvarComSeparadores() {
        Cliente user = new Cliente();
        user.setNome("maria");
        user.setCpf("014.035.510-30");
        controller.save(user, bind, flash);
        Cliente userRetorno = clienteRepository.findByNome("maria");
        Assert.assertNull(userRetorno.getCpf().contains("."));

    }

//    O campo nome deve ser um valor entre 4 e 50, inclusive Mensagem de validação: "Um nome deve possuir entre 4 e 50 caracteres";
    @Test()
    public void testNomeNaoDeveTerMais4Caracter() {
        Cliente user = new Cliente();
        user.setNome("Is");
        user.setCpf("01403551030");
        controller.save(user, bind, flash);
        Cliente userRetorno = clienteRepository.findByNome("Is");
//      Assert.assertNull(userRetorno);
        fail();
    }

    @Test()
    public void testNomeNaoDeveTerMenos50Caracter() {
        Cliente user = new Cliente();
        user.setNome("IsisisisisisIsisisisisisIsisisisisisIsisisisisisIsisisisisisIsisisisisisIsisisisisis");
        user.setCpf("01403551030");
        controller.save(user, bind, flash);
        Cliente userRetorno = clienteRepository.findByNome("IsisisisisisIsisisisisisIsisisisisisIsisisisisisIsisisisisisIsisisisisisIsisisisisis");
        Assert.assertNull(userRetorno);
        fail();
    }

//    O nome do cliente não deve aceitar caracteres especiais, nem números Mensagem de validação: "O nome não deve possuir simbolos ou números";
    @Test()
    public void testNomeNaoDeveTerCaracterEspeciais() {
        Cliente user = new Cliente();
        user.setNome("joao@1990");
        user.setCpf("01403551030");
        controller.save(user, bind, flash);
        Cliente userRetorno = clienteRepository.findByNome("joao@1990");
        Assert.assertNull(userRetorno);
    }

//    O nome do cliente deverá ser um campo único Mensagem de validação: Não possui. Lançará uma Exception;
    @Test(expected = ConstraintViolationException.class)
    public void testClienteNaoPodeDuplicar() {
        Cliente user = new Cliente();
        user.setNome("joaoo");
        user.setCpf("01403551030");
        controller.save(user, bind, flash);

        Cliente user2 = new Cliente();
        user2.setNome("joaoo");
        user2.setCpf("01403551030");
        controller.save(user2, bind, flash);
    }
}
