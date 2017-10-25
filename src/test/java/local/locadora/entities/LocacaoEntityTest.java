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
 * @author aluno
 */
public class LocacaoEntityTest {
    
    @Test
    public void testLocacaoNaoPodeSerRealizadaSemCliente() {
        
        try {
            Locacao locacao = new Locacao();
            locacao.setCliente(null);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Um cliente deve ser selecionado");
        }
    }
    
    @Test
    public void testLocacaoDevePossuirPeloMenos1Filme() {
        
        try {
            Locacao locacao = new Locacao();
            locacao.setFilmes(null);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Pelo menos um filme deve ser selecionado");
        }
    }
    
    @Test
    public void testLocacaoDevePossuirDataDeLocacao() {
        
        try {
            Locacao locacao = new Locacao();
            locacao.setDataLocacao(null);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "A data de locação não deve ser nula");
        }
    }
    
    @Test
    public void testLocacaoDevePossuirDataDeRetorno() {
        
        try {
            Locacao locacao = new Locacao();
            locacao.setDataRetorno(null);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "A data de retorno não deve ser nula");
        }
    }
    
    
}
