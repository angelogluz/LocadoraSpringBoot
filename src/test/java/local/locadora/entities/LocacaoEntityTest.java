/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.controller.LocacaoController;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.LocadoraException;
import local.locadora.utils.DataUtils;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Rafael_Rossales
 */
public class LocacaoEntityTest {

    private static Validator validator;

    private List<Filme> filmes;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        filmes = Arrays.asList(
                new Filme("Batman", 0, 3.50),
                new Filme("Brilho Eterno de Uma Mente Sem Lembranças", 2, 3.50),
                new Filme("O Fabuloso Destino de Amelie Poulain", 3, 3.50),
                new Filme("Avatar", 4, 3.50)
        );
    }

    public LocacaoEntityTest() {
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
    public void locacaoDevePossuirCliente() {
        Locacao ls = new Locacao();
        ls.setDataLocacao(DataUtils.obterData(20, 12, 2018));
        ls.setDataRetorno(DataUtils.obterData(28, 12, 2018));
        ls.setCliente(null);

        ls.setValor(2.0);

        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um cliente deve ser selecionado"));
    }

    @Test
    public void LocacaoDevePossuirCliente() {

        Cliente cliente = new Cliente("rafael");
        Locacao ls = new Locacao();

        ls.setCliente(cliente);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

    }

    @Test
    public void locacaoDevePossuirUmFilme() {

        Locacao ls = new Locacao();
        ls.setFilmes(Arrays.asList(filmes.get(0)));
        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

    }

    @Test
    public void LocacaoDevePossuirUmFilme() {

        Locacao ls = new Locacao();
        ls.setDataLocacao(DataUtils.obterData(20, 12, 2018));
        ls.setDataRetorno(DataUtils.obterData(28, 12, 2018));
        ls.setFilmes(null);

        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Pelo menos um filme deve ser selecionado"));

    }

    @Test
    public void filmeNaoTemEstoque() throws FilmeSemEstoqueException {
        Locacao ls = new Locacao();
        ls.setFilmes(Arrays.asList(filmes.get(0)));

        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
    }

    @Test
    public void dataLocacaoNula() {
        Locacao ls = new Locacao();
        ls.setDataLocacao(null);

        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("A data de locação não deve ser nula"));
    }

    @Test
    public void locacaoSemDataDeDevolucao() {
        Locacao ls = new Locacao();
        ls.setDataLocacao(null);

        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("A data de locação não deve ser nula"));

    }

    @Test
    public void dataRetornoDeveSerFutura() {
        Locacao ls = new Locacao();

        ls.setDataLocacao(DataUtils.obterData(20, 12, 2018));
        ls.setDataRetorno(DataUtils.obterData(28, 12, 2010));
        ls.setValor(2.00);

        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("A data deve retorno deve ser futura"));

    }

    @Test
    public void oPrecoDeveConter2Digitos() {
        Locacao ls = new Locacao();
        double preco = 10.00;
        ls.setValor(preco);

        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(ls.getValor(), is(equalTo(preco)));
    }

    @Test
    public void oPrecoNaoDeveConterMaisDe2Digitos() {
        Locacao ls = new Locacao();

        ls.setDataLocacao(DataUtils.obterData(20, 12, 2018));
        ls.setDataRetorno(DataUtils.obterData(28, 12, 2018));
        ls.setValor(987.654);

        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));

    }

    @Test
    public void entregaPrevistaParaDomigoRegistrarParaSegunda() throws LocadoraException {
        Date dataEntrega = new Date();
        Date dataLocacao = new Date();

        String data = "";
        if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
            dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);

            data = dataEntrega.toString();
            assertTrue(data.contains("Mon"));
        } else {
            dataEntrega = DataUtils.obterDataComDiferencaDias(0);

        }
        System.out.println("Data de Entrega " + dataEntrega);

        Assume.assumeFalse(DataUtils.isMesmaData(dataLocacao, dataEntrega));

    }

}
