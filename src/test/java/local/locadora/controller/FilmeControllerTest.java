package local.locadora.controller;

import javax.validation.ConstraintViolationException;
import local.locadora.dao.FilmeDAO;
import local.locadora.entities.Filme;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author adsn
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FilmeControllerTest {
     @Mock
    Model model;

    @Mock
    RedirectAttributes flash;

    @Mock
    BindingResult bind;

    @Mock
    FilmeController controller;

    @Autowired
    FilmeDAO filmeRepository;

    @Before
    public void setup() {
        controller = new FilmeController(filmeRepository);
    }

    // O nome deverá ser um campo único Mensagem de validação: Não possui. Lançará uma Exception;;
    @Test(expected = ConstraintViolationException.class)
    public void testCPFNaoPodeSerInvalido() {
        Filme filme = new Filme();
        filme.setNome("carros");
        filme.setPrecoLocacao(2d);
        filme.setEstoque(2);
        controller.save(filme, bind, flash);
        controller.save(filme, bind, flash);
        
        fail();
    }
}
