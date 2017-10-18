/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.dao;

import local.locadora.entities.Filme;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author william
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FilmeMoc {
    @Autowired
    private FilmeDAO clientRepository;

    @Before
    public void setup() {

    }
    
    @Mock
    Filme filme;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

   

    @Test
    public void testFindById() {
        Filme filme = new Filme("a cartada",4,4.5);
        Filme f1 = clientRepository.save(filme);
        Filme f = clientRepository.findById(f1.getId());
        assertEquals("a cartada", f.getNome());
    }

    @Test
    public void testFindByIdDefaultSpringData() {
        Filme filme = new Filme("a cartada", 4,4.5);
        Filme f1 = clientRepository.save(filme);
        Filme f = clientRepository.findOne(f1.getId());
        assertEquals("a cartada", f.getNome());
    }

    @Test
    public void testFindByNome() {
        Filme filme = new Filme("a cartada", 4, 4.5);
        Filme f1 = clientRepository.save(filme);
        Filme f = (Filme) clientRepository.findByNome(f1.getNome());
        assertEquals(filme.getNome(), f.getNome());
    }
}


