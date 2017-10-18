/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.dao;

import java.util.ArrayList;
import java.util.List;
import local.locadora.entities.Filme;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author vinic
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FilmeRepositoryTeste {
    
    @Autowired
    private FilmeDAO filmeRepository;
    
    @Test
    public void testFindById() {
        Filme filme = new Filme("dogma", 2, 6.00);
        Filme u1 = filmeRepository.save(filme);
        Filme u = filmeRepository.findById(u1.getId());
        assertEquals("dogma", u.getNome());
    }
    
    @Test
    public void testFindByNome() {
        Filme filme = new Filme("dogma", 2, 6.00);
        List<Filme> u = new ArrayList();
        Filme u1 = filmeRepository.save(filme);
        u = filmeRepository.findByNome(u1.getNome());
        assertEquals("dogma", u.get(0).getNome());
    }
    
    @Test
    public void testDelete() {
        Filme filme = new Filme("dogma12345679", 2, 6.00);
        Filme gravado = filmeRepository.save(filme);
        filmeRepository.delete(gravado);
        List<Filme> buscaFilme = new ArrayList();
        buscaFilme = filmeRepository.findByNome(gravado.getNome());
        assertTrue(buscaFilme.isEmpty());
    }
    
}
