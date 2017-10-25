/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.dao.ClienteDAO;
import local.locadora.dao.FilmeDAO;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author dionatan
 */
public class FilmeEntityTest {
    
    
    private static Validator validator;

     @Autowired
    private FilmeDAO filmeRepository;
    
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void naoDeveValidarUmNomeComUmCaracteres() {
        Filme filme = new Filme();
        filme.setNome("a");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
       
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       

        assertThat(message, is("Um nome deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
    public void naoDeveValidarUmNomeComMaisDeCemCaracteres() {
        Filme filme = new Filme();
        filme.setNome("asdddddddddddddddddddddddddddddddddddddddddddddassssjakçdhjlasdhjjadjdasdjkhdaskhjdhkadkhjdaskhdakhhkdahkdahkjdhk");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
       
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       

        assertThat(message, is("Um nome deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
    public void testaFilmeMesmoNomeNaoPode() {
        Filme filme = new Filme("Avatar");
        Filme film1 = filmeRepository.save(filme);
        Filme c = filmeRepository.findByNome(film1.getNome());
        Filme filme2 = new Filme("Avatar");
        Filme film2 = filmeRepository.save(filme2);
        Filme c1 = filmeRepository.findByNome(film2.getNome());
        boolean var = false;
        if (c != null && c1 != null) {
            var = true;
        }
        Assert.assertFalse(var);
    }
    
    @Test
    public void testaFilmeEstoqueNaoPodeNegativo() {
        Filme filme = new Filme("Filme 1", -10, 4.0);
        filme.setEstoque(-10);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl test = (ConstraintViolationImpl) it.next();
        String message = test.getMessage();
 
        assertThat(message,is("O Estoque deve ser positivo"));
        
    }
  
  
}
