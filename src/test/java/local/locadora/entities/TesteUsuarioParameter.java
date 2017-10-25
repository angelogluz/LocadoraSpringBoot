/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 *
 * @author Jones
 */
@RunWith(Parameterized.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TesteUsuarioParameter {
    
    public TesteUsuarioParameter() {
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

     @Parameterized.Parameters(name = "Teste - {index} = {2} - {1}")
    public static List<Object[]> parametrosDeExecucao() {
        String nome1 = "";
        String nome2 = "12345";
        String nome3 = "foo";

        String retorno1 = "Usuario sem nome";
        String retorno2 = "Usuario com numero no nome";
        String retorno3 = "Foo";

        return Arrays.asList(new Object[][]{
            {Arrays.asList(nome1), (retorno1), "naoDeveSalvarUsuarioSemNome"},
            {Arrays.asList(nome2), (retorno2), "naoDeveSalvarUsuarioComNumeroNoNome"},
            {Arrays.asList(nome3), (retorno3), "naoDeveSalvarUsuarioComNomeEmMinusculo"},});
    }

    @Parameterized.Parameter(0)
    public List<String> nomes;

    @Parameterized.Parameter(1)
    public String retorno;

    @Parameterized.Parameter(2)
    public String descricao;

    @Test
    public void deveVerificarNomeUsuario() {

            for (String nome : nomes) {

            //Cenario
            Usuario usuario = new Usuario(nome);
            
            try{

            String retornoMetodo = usuario.setNome(nome);
            if(retornoMetodo != null){
                Assert.assertEquals(retorno, retornoMetodo);
            }
            }catch(Exception e){
                //Validação
                Assert.assertEquals(retorno, e.getMessage());
            }
        }

    }
}
