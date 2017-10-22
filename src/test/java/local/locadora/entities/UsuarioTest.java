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
public class UsuarioTest {
    
    @Test
    public void testUsuarioComNomeEmBranco() {
        try {
            Usuario usuario = new Usuario("");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Não é possível cadastrar usuário sem nome!");
        }
    }
}
