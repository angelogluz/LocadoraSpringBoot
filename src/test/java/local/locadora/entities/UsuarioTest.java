/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.regex.Matcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pedro Cantarelli
 */
public class UsuarioTest {

    @Test
    public void TestarUsuarioComNomeIgual(){
        Usuario u1 = new Usuario("Pedro");
        Usuario u2 = new Usuario("Pedro");

        assertEquals(u1.getNome(), u2.getNome());
    }
    
    @Test
    public void TestarUsuarioPorObjeto(){        
        Usuario u1 = new Usuario("Pedro");
        Usuario u2 = new Usuario("Joaozinho");

        assertEquals(u1.getClass(), u2.getClass());
    }
    
    @Test
    public void TestaSeContemSomenteNumeroNoNome(){
        Usuario u1 = new Usuario("1212 1212");
        assertTrue(u1.getNome().matches("([0-9]+[ ]*)+"));
    }
        
    @Test
    public void TestaSeContemCaracterEspeciais(){
        Usuario u1 = new Usuario("Pedro C@nt$relli");
        assertFalse(u1.getNome().matches("[a-z A-Z]*"));
    }
    
}
