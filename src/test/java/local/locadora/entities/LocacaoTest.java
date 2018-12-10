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

    private boolean findMessage(String errorMessage, Locacao locacao) {
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl nxt = null;
        if (it.hasNext()) {
            nxt = (ConstraintViolationImpl) it.next();
            System.out.println(nxt.getMessage());
        }
        if (x != null) {
            return nxt.getMessage().equals(errorMessage);
        }
        return false;
    }

    private Locacao exemploValido() {
        Locacao locacao = new Locacao();
        Filme filme = new Filme("A volta dos que nao foram", 5, 2.00);
        locacao.addFilme(filme);
        Cliente cliente = new Cliente("Luke Skywalker");
        locacao.setCliente(cliente);
        locacao.setDataLocacao(new Date());
        Date date = new Date();
        date.setTime(date.getTime() + 10000);
        locacao.setDataRetorno(date);
        locacao.setValor(2.00);
        return locacao;
    }

    @Test
    public void aDataDeLocacaoNaoDeveSerNula() {
        Locacao locacao = exemploValido();
        locacao.setDataLocacao(null);

        String errorMessage = "A data de locação não deve ser nula";
        if (!findMessage(errorMessage, locacao)) {
            fail("");
        }
    }

    @Test
    public void ADataDeRetornoNaoDeveSerNula() {
        Locacao locacao = exemploValido();
        locacao.setDataRetorno(null);

        String errorMessage = "A data de retorno não deve ser nula";
        if (!findMessage(errorMessage, locacao)) {
            fail("");
        }
    }

    @Test
    public void aDataDeRetornoDeveSerFutura() {
        Locacao locacao = exemploValido();
        Date antes = new Date();
        antes.setTime(locacao.getDataLocacao().getTime() - 10000);
        locacao.setDataRetorno(antes);

        String errorMessage = "A data deve retorno deve ser futura";
        if (!findMessage(errorMessage, locacao)) {
            fail("");
        }
    }

    @Test
    public void oPrecoDeveTerNoMaximoDoisDigitos() {
        Locacao locacao = exemploValido();
        locacao.setValor(11.111);

        String errorMessage = "O Preço deve ter no máximo dois dígitos";
        if (!findMessage(errorMessage, locacao)) {
            System.out.println(locacao.getValor());
            fail("11.111");
        }

        locacao.setValor(1.222);

        if (!findMessage(errorMessage, locacao)) {
            fail("1.222");
        }

        locacao.setValor(100.00);
        if (!findMessage(errorMessage, locacao)) {
            fail("100.00");
        }
    }

    @Test
    public void oValorDaLocacaoDeveSerPositivo() {
        Locacao locacao = exemploValido();
        locacao.setValor(-1.0);

        String errorMessage = "O valor da locação deve ser positivo";
        if (!findMessage(errorMessage, locacao)) {
            System.out.println(locacao.getValor());
            fail("");
        }
    }

    @Test
    public void locacaoDomingoSegunda() throws LocadoraException {
        Locacao locacao = exemploValido();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(2018, 11, 9);
        date = calendar.getTime();

        locacao.setDataRetorno(date);
      
        calendar.set(2018, 11, 10);
        date = calendar.getTime();
        if (locacao.getDataRetorno() != date) {
            throw new LocadoraException("");
        }
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void locacaoSemEstoque() {
        Locacao locacao = exemploValido();
        Filme filme = new Filme("A lagoa azul", 0, 5.00);
        locacao.addFilme(filme);
        fail("");
    }

    @Test
    public void aumentaDiasBaseadoNaQuantidadeDeFilmes() throws LocadoraException {
        Locacao locacao = exemploValido();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();

        locacao.setDataRetorno(date);

        calendar.add(Calendar.DATE, locacao.getFilmes().size());
        date = calendar.getTime();

        if (locacao.getDataRetorno() != date) {
            throw new LocadoraException("");
        }
    }

    @Test
    public void umClienteDeveSerSelecionado() {
        Locacao locacao = exemploValido();
        locacao.setCliente(null);

        String errorMessage = "Um cliente deve ser selecionado";
        if (!findMessage(errorMessage, locacao)) {
            fail(errorMessage);
        }
    }

    @Test
    public void peloMenosUmFilmeDeveSerSelecionado) {
        Locacao locacao = exemploValido();
        locacao.getFilmes().clear();

        String errorMessage = "Pelo menos um filme deve ser selecionado";
        if (!findMessage(errorMessage, locacao)) {
            fail("");
        }
    }

}
