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
        Usuario u = clientRepository.findById(u1.getId());
        assertEquals("Foo", u.getNome());
    }

    @Test
    public void testFindByIdDefaultSpringData() {
        Usuario usuario = new Usuario("Foo");
        Usuario u1 = clientRepository.save(usuario);
        Usuario u = clientRepository.findOne(u1.getId());
        assertEquals("Foo", u.getNome());
    }

    @Test
    public void testFindByNome() {
        Usuario usuario = new Usuario("Foo");
        Usuario u1 = clientRepository.save(usuario);
        Usuario u = clientRepository.findByNome(u1.getNome());
        assertEquals(usuario.getNome(), u.getNome());
    }

    @Test
    public void TestaSeContemCaracterEspeciais() {
        Usuario usuario1 = new Usuario("Pedro C@nt$relli");
        Usuario u1 = clientRepository.save(usuario1);
        Usuario u2 = clientRepository.findByNome("Pedro C@nt$relli");

        assertFalse(u2.getNome().matches("[a-z A-Z]*"));
    }

    @Test
    public void TestaSeContemSomenteNumeroNoNome() {
        Usuario usuario1 = new Usuario("1212 1212");
        Usuario u1 = clientRepository.save(usuario1);
        Usuario u2 = clientRepository.findByNome("1212 1212");

        assertTrue(u2.getNome().matches("([0-9]+[ ]*)+"));
    }

}
