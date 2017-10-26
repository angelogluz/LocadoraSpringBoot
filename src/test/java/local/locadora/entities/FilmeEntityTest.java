/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
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
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FilmeEntityTest {

    private static Validator validator;

    public FilmeEntityTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void deveSerUnicoONome() {
        //Cenário
        Filme filme1 = new Filme();
        Filme filme2 = new Filme();

        //Ação
        try {

            filme1.setNome("Matrix");
            filme2.setNome("Matrix");
            fail();
        } catch (Exception e) {
            //Validação
            Object ExceptionFilme = null;
            Assert.assertSame(ExceptionFilme, e);
        }
    }


@Test
        public void naoDeveValidarNomeComEspacosEmBrancoNoInicioENoFim() {
        //cenario
        Filme filme = new Filme();
        //acao
        filme.setNome(" Matrix ");
        //validacao
        assertEquals("Matrix", filme.getNome());
    }
    
    @Test
        public void naoDeveValidarUmNomeComMenosDe2() {
        //Cenário
        Filme filme = new Filme();
        //Ação
        filme.setNome("F");
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
        //Validação
        assertThat(message,is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
        public void naoDeveValidarUmNomeComMaisDe100Caracteres() {
        //Cenário
        Filme filme = new Filme();
        String nome = null;
        for (int i = 0; i < 120; i++) {
            nome = nome + "a";
        }
        //Ação
        filme.setNome("nome");
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
        //Validação
        assertThat(message,is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
        public void naoDeveValidarFilmeComEstoqueNegativo() {
        //cenario
        Filme filme = new Filme();
        //acao
        filme.setEstoque(-1);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
       //validacao
       assertThat(message,is("O Estoque deve ser positivo"));
    }
    
    @Test
        public void naoDeveValidarFilmeComValorNegativo() {
        //cenario
        Filme filme = new Filme();
        //acao
        filme.setPrecoLocacao(-1.50);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
       //validacao
       assertThat(message,is("O Valor da locação deve ser positivo"));
    }
    
    @Test
        public void naoDeveValidarFilmeComValorComDoisDigitosAposAVirgula() {
        //cenario
        Filme filme = new Filme();
        //acao
        filme.setPrecoLocacao(100.000);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
       //validacao
       assertThat(message,is("O Preço deve ter no máximo dois dígitos"));
    }
    
}
