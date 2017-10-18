/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;


import local.locadora.exceptions.UsuarioException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Felipe
 */
public class UsuarioTest {
    
    public UsuarioTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     *
     * @throws UsuarioException
     * Este parametro serve para fazer o teste de quando o usuario tenta fazer
     * o cadastro de um novo usuari com nome vazio
     */
    @Test
    public void usuarioNomeVazio() {
        //Cenário
        Usuario usuario = new Usuario();
        
        //Ação
        try {
            usuario.setNome("");
            Assert.fail();
// faz com que falhe a asserçao de dados sem verificar as condiçoes e
// exibe a mensagem formatada abaixo
           
        } catch (Exception e) {
            //certifica que a msg exibida é a mesma detalhada no metodo que esta lancando a execeçao
            assertEquals("Não é possível cadastrar um usuário sem nome.", e.getMessage());
        }
    }
    
}
