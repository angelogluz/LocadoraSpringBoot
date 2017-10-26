package local.entities;


import java.util.regex.Pattern;
import local.locadora.entities.Usuario;
import local.locadora.exceptions.UsuarioException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kelvin Ferreira
 */
public class UsuarioTest {

//    @Test
//    public void cpfDoClienteValido() throws Exception {
//        Usuario usuario = new Usuario("Kelvin");
//
//        String cpf = "03766219073";
//        
//        usuario.setCpf(cpf);
//
//        assertThat(usuario.getCpf(), is("cpf"));
//
//    }

    @Test
    public void nomeDeveSerEntre4e50() throws Exception {

        Usuario usuario = new Usuario("Kelvin");

        if (usuario.getNome().length() < 4 || usuario.getNome().length() > 50) {

            assertTrue("Um nome deve possuir entre 4 e 50 caracteres", false);

        } else {
            assertTrue(true);
        }
    }

    @Test
    public void nomeClienteNaoPodeConterCaracteresEspeciais() throws UsuarioException {
        
        Usuario usuario = new Usuario("kelvin");
        String nome = "kelvin0";
        
        if(!Pattern.compile("[^a-z0-9._@]").matcher(nome).find()){
            assertTrue("", true);
        } else {
           assertTrue("O nome não deve possuir simbolos ou númeross", false);
        }

    }

}
