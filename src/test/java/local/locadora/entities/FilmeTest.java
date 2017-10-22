/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Luiz Buchvaitz
 */
public class FilmeTest {
    
    /**
     * 
     * @throws Exception Exceção que ocorre se o nome passado na criação do filme for vazio ("")
     */
    @Test
    public void testFilmeComNomeEmBranco() throws Exception {
        try {
            Filme filme = new Filme("", 10, 4d);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Não é possível cadastrar filme sem nome!");
        }
    }
    
    /**
     * 
     * @throws Exception Exceção que ocorre se o estoque passado na criação do filme for negativo
     */
    @Test
    public void testEstoqueDeFilmeNegativo() throws Exception {
        try {
            Filme filme = new Filme("FilmeQualquer", -10, 4d);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Não é possível o estoque ser menor que zero!");
        }
    }
    
    /**
     * 
     * @throws Exception Exceção que ocorre se o preço de locação passado na criação do filme for negativo
     */
    @Test
    public void testValorDeFilmeNegativo() throws Exception {
        try {
            Filme filme = new Filme("FilmeQualquer", 10, -4d);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Não é possível o preco ser menor que zero!");
        }
    }
}
