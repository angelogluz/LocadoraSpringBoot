/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.LocadoraException;

import static org.assertj.core.api.Fail.fail;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LocacaoTest {

    public LocacaoTest() {
    }

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private static Validator validator;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    private boolean encontrou(String msgErro, Locacao locacao) {
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //String violationMessages = "";
        ConstraintViolationImpl x = null;
        if (it.hasNext()) {
            x = (ConstraintViolationImpl) it.next();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(x.getMessage());
            //  violationMessages += x.getMessage();
        }
        if (x != null) {
            return x.getMessage().equals(msgErro);
        }
        return false;
    }

    private Locacao mockLocacao() {
        Locacao loc = new Locacao();
        Filme filme = new Filme("A volta dos que nao foram", 5, 2.00);
        loc.addFilme(filme);
        Cliente cliente = new Cliente("Luke Skywalker");
        loc.setCliente(cliente);
        loc.setDataLocacao(new Date());
        Date date = new Date();
        date.setTime(date.getTime() + 10000);
        loc.setDataRetorno(date);
        loc.setValor(2.00);
        return loc;
    }

    @Test
    public void umClienteDeveSerSelecionado() {
        Locacao locacao = mockLocacao();
        locacao.setCliente(null);

        String msgErro = "Um cliente deve ser selecionado";
        if (!encontrou(msgErro, locacao)) {
            fail(msgErro);
        }
    }

    @Test
    public void peloMenos1Filme() {
        Locacao locacao = mockLocacao();
        locacao.getFilmes().clear();

        String msgErro = "Pelo menos um filme deve ser selecionado";
        if (!encontrou(msgErro, locacao)) {
            fail("");
        }
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void naoDeveLocarSemEstoque() {
        Locacao locacao = mockLocacao();
        Filme filme = new Filme("Filme X", 0, 5.00);
        locacao.addFilme(filme);
        fail("");
    }

    @Test
    public void dataLocacaoNaoNula() {
        Locacao locacao = mockLocacao();
        locacao.setDataLocacao(null);

        String msgErro = "A data de locação não deve ser nula";
        if (!encontrou(msgErro, locacao)) {
            fail("");
        }
    }

    @Test
    public void dataRetornoNaoNula() {
        Locacao locacao = mockLocacao();
        locacao.setDataRetorno(null);

        String msgErro = "A data de retorno não deve ser nula";
        if (!encontrou(msgErro, locacao)) {
            fail("");
        }
    }

    @Test
    public void dataRetornoNaoPodeSerPassado() {
        Locacao locacao = mockLocacao();
        Date antes = new Date();
        antes.setTime(locacao.getDataLocacao().getTime() - 10000);
        locacao.setDataRetorno(antes);

        String msgErro = "A data deve retorno deve ser futura";
        if (!encontrou(msgErro, locacao)) {
            fail("");
        }
    }

    @Test
    public void valorLocacaoMinimo2Digitos() {
        Locacao locacao = mockLocacao();
        locacao.setValor(11.111);

        String msgErro = "O Preço deve ter no máximo dois dígitos";
        if (!encontrou(msgErro, locacao)) {
            System.out.println(locacao.getValor());
            fail("11.111");
        }

        locacao.setValor(1.222);

        if (!encontrou(msgErro, locacao)) {
            fail("1.222");
        }

        locacao.setValor(100.00);
        if (!encontrou(msgErro, locacao)) {
            fail("100.00");
        }
    }

    @Test
    public void valorLocacaoPositivo() {
        Locacao locacao = mockLocacao();
        locacao.setValor(-1.0);

        String msgErro = "O valor da locação deve ser positivo";
        if (!encontrou(msgErro, locacao)) {
            System.out.println(locacao.getValor());
            fail("");
        }
    }

    @Test
    public void registrarDomingoVaiParaSegunda() throws LocadoraException {
        Locacao locacao = mockLocacao();
        // gera uma data específica (em um domingo)
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.set(2018, 12, 9);
        dt = c.getTime();
        
        // seta o dia para domingo
        locacao.setDataRetorno(dt);

        // gera a data de segunda
        c.set(2018, 12, 10);
        dt = c.getTime();
        
        // verifica se a data foi para segunda
        if (locacao.getDataRetorno() != dt) {
            throw new LocadoraException("");
        }
    }

    @Test
    public void numeroDiasRecebimentoAumentadoPelosFilmes() throws LocadoraException {
        Locacao locacao = mockLocacao();
        // gera uma data representando amanhã
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        // seta data retorno para amanhã
        locacao.setDataRetorno(dt);
        
        // gera a data para a quantidade de filmes
        c.add(Calendar.DATE, locacao.getFilmes().size());
        dt = c.getTime();
        
        // verifica se a data ficou correta
        if (locacao.getDataRetorno() != dt) {
            throw new LocadoraException("");
        }
    }

}
