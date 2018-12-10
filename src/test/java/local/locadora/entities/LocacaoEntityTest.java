package local.locadora.entities;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import local.locadora.utils.DataUtils;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import local.locadora.exceptions.FilmeSemEstoqueException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

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
                new Filme("A liga da justiça", 5, 4.50),
                new Filme("Brigando pra viver", 3, 7.50),
                new Filme("A fantastica fabrica", 1, 3.50),
                new Filme("Aprendiz", 2, 4.50)
        );
    }

    /* 
    Uma locação não pode ser realizada sem data de locação 
    Mensagem de validação: "A data de locação não deve ser nula";
     */
    @Test
    public void dataDeLocacaoNaoPodeSerNulo() {
        Locacao locacao = new Locacao();

        locacao.setDataRetorno(DataUtils.obterData(19, 12, 2018));
        locacao.setValor(4.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data de locação não deve ser nula"));
    }

    /*
    Uma locação não pode ser realizada sem data de devolução 
    Mensagem de validação: "A data de retorno não deve ser nula";
     */
    @Test
    public void dataDeRetornoNaoPodeSerNulo() {
        Locacao locacao = new Locacao();
        locacao.setDataLocacao(DataUtils.obterData(11, 12, 2018));
        locacao.setValor(32.50);

        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data de retorno não deve ser nula"));
    }

    /*
    A data de devolução do filme não pode ser uma data no passado 
    Mensagem de validação: "A data deve retorno deve ser futura";
     */
    @Test
    public void dataDeRetornoDeveSerFutura() {
        Locacao locacao = new Locacao();

        locacao.setDataLocacao(DataUtils.obterData(19, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(11, 12, 2010));

        locacao.setValor(4.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data deve retorno deve ser futura"));
    }

    /*
    O valor da locação deve ser sempre positivo Mensagem de validação: "O valor da locação deve ser positivo";
     */
    @Test
    public void valorDaLocacaoDeveSerPositivo() {
        Locacao locacao = new Locacao();

        locacao.setDataLocacao(DataUtils.obterData(19, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(11, 12, 2018));

        locacao.setValor(-31.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("O valor da locação deve ser positivo"));
    }

    /*
    O valor da locação deve possuir no máximo dois dígitos antes e depois da vírgula 
    Mensagem de validação: "O Preço deve ter no máximo dois dígitos";
     */
    @Test
    public void valorDeveTerNomaximoDoisDigitos() {
        Locacao locacao = new Locacao();

        locacao.setDataLocacao(DataUtils.obterData(11, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(19, 12, 2018));

        locacao.setValor(321.644);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("O Preço deve ter no máximo dois dígitos"));
    }

    /*
    Uma locação não deverá ser realizada sem um cliente Mensagem de validação: "Um cliente deve ser selecionado";
     */
    @Test
    public void deveSerSelecionadoUmCliente() {
        Locacao locacao = new Locacao();

        locacao.setDataLocacao(DataUtils.obterData(11, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(19, 12, 2018));
        locacao.setCliente(null);

        locacao.setValor(4.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("Um cliente deve ser selecionado"));
    }

    /*
    Uma locação deverá possuir pelo menos 1 filme Mensagem de validação: "Pelo menos um filme deve ser selecionado";
     */
    @Test
    public void deveSelecionarUmFilme() {
        Locacao locacao = new Locacao();

        locacao.setDataLocacao(DataUtils.obterData(11, 9, 2018));
        locacao.setDataRetorno(DataUtils.obterData(19, 12, 2018));
        locacao.setFilmes(null);

        locacao.setValor(4.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("Pelo menos um filme deve ser selecionado"));
    }

    /*
    Uma locação de filme sem estoque não poderá ser realizada 
    Mensagem de validação: Sem mensagem. Uma Exception deverá ser lançada;
     */
    @Test
    public void locacaoFilmeNaoTemEstoque() throws FilmeSemEstoqueException {
        Locacao ls = new Locacao();
        ls.setFilmes(Arrays.asList(filmes.get(0)));

        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
    }

    @Test
    public void seLocarNoSabadoEntregaNaSegunda() {
        Date dataDeLocacao = new Date();
        Date dataDeEntrega = new Date();

        Cliente cliente = new Cliente();
        cliente.setNome("Marcos");
        cliente.setCpf("345.674.713-09");

        Locacao locacao = new Locacao();
        locacao.setCliente(cliente);

        assumeTrue(DataUtils.verificarDiaSemana(dataDeLocacao, Calendar.SATURDAY));
        String data = "";

        if (DataUtils.verificarDiaSemana(dataDeLocacao, Calendar.SATURDAY)) {
            dataDeEntrega = DataUtils.adicionarDias(dataDeLocacao, 2);
            data = dataDeEntrega.toString();
        } else {
            dataDeEntrega = DataUtils.adicionarDias(dataDeLocacao, 1);
            data = dataDeEntrega.toString();
        }
        System.out.println("dia da semana: " + dataDeEntrega);

        if (data.contains("Sun")) {
            fail("Data de Entrega não pode ser domingo!");
        }
        assertTrue(data.contains("Mon"));
        assertFalse(DataUtils.isMesmaData(dataDeLocacao, dataDeEntrega));

    }

}
