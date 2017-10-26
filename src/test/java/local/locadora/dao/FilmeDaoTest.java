/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.dao;

import local.locadora.entities.Filme;
import static org.assertj.core.api.Fail.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author aluno
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FilmeDaoTest {
    
      @Autowired
    FilmeDAO filmeRepository;
    


      @Test(expected = Exception.class)
    public void campoNomeDeveSerUnico() {
      
           Filme filme1 = new Filme();
        filme1.setNome("Bruna Nobre");
        
        filmeRepository.save(filme1);
        
        Filme filme2 = new Filme();
        filme2.setNome("Bruna Nobre");
        
        filmeRepository.save(filme2);
        fail("Não deveria ter salvo dois nomes iguais");
    }
}
