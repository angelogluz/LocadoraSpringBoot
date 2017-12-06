package local.locadora.controller;

import local.locadora.dao.FilmeDAO;
import local.locadora.entities.Filme;
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
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
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

    @Test(expected = ConstraintViolationException.class)
    public void filmeJaCadastrado() {
        Filme film = new Filme();

        film.setNome("filmeTal");
        controller.save(film, bind, flash);

        filmeRepository.findByNome("filmeTal");

        fail("Filme já cadastrado");
    }
}
