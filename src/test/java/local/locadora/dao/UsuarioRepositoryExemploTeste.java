package local.locadora.dao;

import local.locadora.entities.Usuario;
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
public class UsuarioRepositoryExemploTeste {

    @Autowired
    private UsuarioDAO clientRepository;

    @Before
    public void setup() {
        
    }

    @Test
    public void testFindById() {
        Usuario usuario = new Usuario("Foo");
        Usuario u1 = clientRepository.save(usuario);
        Usuario u = clientRepository.findById(u1.getId()).get();
        assertEquals("Foo", u.getNome());
    }

    @Test
    public void testFindByIdDefaultSpringData() {
        Usuario usuario = new Usuario("Foo");
        Usuario u1 = clientRepository.save(usuario);
        Usuario u = clientRepository.findById(u1.getId()).get();
        assertEquals("Foo", u.getNome());
    }

    @Test
    public void testFindByNome() {
        Usuario usuario = new Usuario("Foo");
        Usuario u1 = clientRepository.save(usuario);
        Usuario u = clientRepository.findByNome(u1.getNome());
        assertEquals(usuario.getNome(), u.getNome());
    }
}
