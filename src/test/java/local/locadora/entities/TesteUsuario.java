/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Arrays;
import java.util.List;
import local.locadora.dao.UsuarioDAO;
import local.locadora.exceptions.UsuarioException;
import local.locadora.utils.NumberUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Jones
 */
//@RunWith(SpringRunner.class)
@RunWith(Parameterized.class)
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
    public void naoDeveSalvarUsuarioSemNome() {

        //Cenário
        String nome = "";
        String retorno;
        Usuario usuario = new Usuario();

        //Ação
        try {
            retorno = usuario.setNome(nome);
            fail();

        } catch (UsuarioException e) {
            //Validação
            Assert.assertEquals("Usuario sem nome", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarUsuarioComNumeroNoNome() {

        //Cenário
        String nome = "12345";
        String retorno;
        Usuario usuario = new Usuario();

        //Ação
        try {
            retorno = usuario.setNome(nome);
            fail();

        } catch (UsuarioException e) {
            //Validação
            Assert.assertEquals("Usuario com numero no nome", e.getMessage());
        }

    }

    @Test
    public void naoDeveSalvarUsuarioComNomeEmMinusculo() throws UsuarioException {

        //Cenário
        String nome = "foo";
        String retorno;
        Usuario usuario = new Usuario();

        //Ação
        retorno = usuario.setNome(nome);

        //Validação
        Assert.assertEquals("Foo", usuario.getNome());
    }

//Testes efetuados com Mockito
    Usuario user = mock(Usuario.class);

    @Test
    public void testeUsuario() throws UsuarioException {

        when(user.setNome("")).thenReturn("Usuario sem nome");
        when(user.setNome("12345")).thenReturn("Usuario com numero no nome");
        when(user.setNome("foo")).thenReturn("Foo");

        Assert.assertEquals("Usuario sem nome", user.setNome(""));
        Assert.assertEquals("Usuario com numero no nome", user.setNome("12345"));
        Assert.assertEquals("Foo", user.setNome("foo"));

    }

   

}

