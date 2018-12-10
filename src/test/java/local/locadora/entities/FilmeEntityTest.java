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
import net.bytebuddy.utility.RandomString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Rafael_Rossales
 */
public class FilmeEntityTest {

    private static Validator validator;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public FilmeEntityTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void nomeNaoDeveraAceitarEspacosEmBrancoInicoFim() {
        Filme filme = new Filme();
        filme.setNome(" abcde ");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String message = x.getMessage();
        assertThat(filme.getNome(), is("abcde"));
    }

    @Test
    public void naoDeveAceitarNomeCom1Caracteres() {
        Filme filme = new Filme();
        String titulo = RandomString.make(1);
        filme.setNome(titulo);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String message = x.getMessage();

        assertThat(filme.getNome(), is(equalTo(titulo)));
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));

    }

    @Test
    public void deveAceitarNomeCom2Caracteres() {
        Filme filme = new Filme();
        String titulo = RandomString.make(2);
        filme.setNome(titulo);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String message = x.getMessage();

        assertThat(filme.getNome(), is(equalTo(titulo)));

    }

    @Test
    public void naoDeveAceitarNomeCom101Caracteres() {
        Filme filme = new Filme();
        String titulo = RandomString.make(101);
        filme.setNome(titulo);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String message = x.getMessage();

        assertThat(filme.getNome(), is(equalTo(titulo)));
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));

    }

    @Test
    public void deveAceitarNomeCom100Caracteres() {
        Filme filme = new Filme();
        String titulo = RandomString.make(100);
        filme.setNome(titulo);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String message = x.getMessage();

        assertThat(filme.getNome(), is(equalTo(titulo)));

    }

    @Test
    public void estoqueNaoPodeSerNegativo() {
        Filme filme = new Filme();
        int estoque = -1;
        filme.setEstoque(estoque);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String message = x.getMessage();

        assertThat(filme.getEstoque(), is(equalTo(estoque)));
        assertThat(message, is("O Estoque deve ser positivo"));

    }

    @Test
    public void estoqueDeveSerPositivo() {
        Filme filme = new Filme();
        int estoque = 1;
        filme.setEstoque(estoque);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String message = x.getMessage();

        assertThat(filme.getEstoque(), is(equalTo(estoque)));

    }

    @Test
    public void valorLocacaoPositivo() {
        Filme filme = new Filme();
        double vLocacao = -10.00;
        filme.setPrecoLocacao(vLocacao);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(filme.getEstoque(), is(equalTo(vLocacao)));
    }

    @Test
    public void oPrecoDeveConter2Digitos() {
        Filme filme = new Filme();
        double preco = 10.00;
        filme.setPrecoLocacao(preco);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(filme.getPrecoLocacao(), is(equalTo(preco)));
    }

    @Test
    public void oPrecoNaoDeveConterMaisDe2Digitos() {
        Filme filme = new Filme();
        double preco = 130.300;
        filme.setPrecoLocacao(preco);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(filme.getPrecoLocacao(), is(equalTo(preco)));
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));

    }

}
