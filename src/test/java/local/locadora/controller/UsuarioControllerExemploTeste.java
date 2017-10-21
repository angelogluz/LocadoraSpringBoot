package local.locadora.controller;

import local.locadora.dao.UsuarioDAO;
import local.locadora.entities.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioControllerExemploTeste {


        @Autowired
        UsuarioDAO usuarioRepository;

        @Mock
        Model model;
    
        UsuarioController controller;

        @Before
        public void setup(){
            controller = new UsuarioController(usuarioRepository);
        }
        @Test
        public void testControllerInserindoUsuario() {
            Usuario user = new Usuario();
            user.setNome("Isa");
            controller.save(user,model);
            Usuario u = usuarioRepository.findByNome("Isa");
            assertThat(u.getNome(),is(user.getNome()));
        }
}
