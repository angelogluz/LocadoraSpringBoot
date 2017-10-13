/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import local.locadora.dao.UsuarioDAO;
import local.locadora.exceptions.UsuarioException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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
 * @author Jones
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TesteUsuario {

    @Autowired
    private UsuarioDAO clientRepository;

    public TesteUsuario() {
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

    @Test
    public void nãoDeveSalvarUsuarioSemNome() throws UsuarioException {

        //Cenário
        String nome = "";
        String retorno;
        Usuario usuario = new Usuario();

//        //Ação
//        try {
////           clientRepository.save(usuario);
            retorno = usuario.setNome(nome);
            if (!retorno.equals("Ok")) {
                fail();
            }
//        } catch (UsuarioException e) {
//            Assert.assertEquals("Usuario sem nome", e.getMessage());
//        }
    }
    
    @Test
    public void nãoDeveSalvarUsuarioComNumeroNoNome() throws UsuarioException {
        
         //Cenário
        String nome = "1111";
        String retorno;
        Usuario usuario = new Usuario();

//        //Ação
//        try {
////           clientRepository.save(usuario);
            retorno = usuario.setNome(nome);
            if (!retorno.equals("Ok")) {
                fail();
            }
        
    }
}
