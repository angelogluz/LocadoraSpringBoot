/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import local.locadora.dao.FilmeDAO;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author aluno
 */
public class FilmeEntityTest {
    
    @Autowired
    private FilmeDAO filmeRepository;
    
    @Test
    public void testNomeDeveSerUnico() {
        
        try {
            Filme filme1 = new Filme("FilmeQualquer", 10, 4.0);
            Filme filmeSave1 = filmeRepository.save(filme1);
            
            Filme filme2 = new Filme("FilmeQualquer", 10, 4.0);
            Filme filmeSave2 = filmeRepository.save(filme2);
            
            Assert.fail();
        } catch (Exception e) {
        }
    }
    
    @Test
    public void testNomeDevePossuirEntre2E100Caracteres () {
        
        try {
            Filme filme = new Filme("F", 10, 4.00);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Um filme deve possuir entre 2 e 100 caracteres");
        }
    }
    
    @Test
    public void testEstoqueDeveSerPositivo () {
        
        try {
            Filme filme = new Filme("Filme1", -1, 4.00);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "O Estoque deve ser positivo");
        }
    }
    
    @Test
    public void testPrecoDeveSerPositivo () {
        
        try {
            Filme filme = new Filme("Filme1", 10, -1d);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "O Valor da locação deve ser positivo");
        }
    }
    
    @Test
    public void testPrecoCom2Digitos () {
        
        try {
            Filme filme = new Filme("Filme1", 10, 400.00);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "O Preço deve ter no máximo dois dígitos");
        }
    }
}
