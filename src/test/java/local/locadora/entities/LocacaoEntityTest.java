package local.locadora.entities;

import local.locadora.utils.DataUtils;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LocacaoEntityTest {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void dataDeRetornoNaoPodeSerNulo() {
        Locacao locacao = new Locacao();
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data de locação não deve ser nula"));
    }

    @Test
    public void dataDeLocacaoNaoPodeSerNulo() {
        Locacao locacao = new Locacao();
        locacao.setDataRetorno(DataUtils.obterData(28, 12, 2018));
        locacao.setValor(5.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data de locação não deve ser nula"));
    }

    @Test
    public void dataDeRetornoDeveSerFutura() {
        Locacao locacao = new Locacao();
        locacao.setDataLocacao(DataUtils.obterData(20, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(28, 12, 2010));
        locacao.setValor(2.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();
        assertThat(msg, is("A data deve retorno deve ser futura"));
    }

    @Test
    public void valorDaLocacaoDeveSerPositivo() {
        Locacao locacao = new Locacao();   
        locacao.setDataLocacao(DataUtils.obterData(20, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(28, 12, 2018));
        locacao.setValor(-10.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();
        assertThat(msg, is("O valor da locação deve ser positivo"));
    }

    @Test
    public void valorDeveTerNomaximoDoisDigitos() {
        Locacao locacao = new Locacao();
        locacao.setDataLocacao(DataUtils.obterData(20, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(28, 12, 2018));
        locacao.setValor(987.654);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();
        assertThat(msg, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void umClienteDeveSerSelecionado() {
        Locacao locacao = new Locacao();
        locacao.setDataLocacao(DataUtils.obterData(20, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(28, 12, 2018));
        locacao.setCliente(null);
        locacao.setValor(2.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();
        assertThat(msg, is("Um cliente deve ser selecionado"));
    }
    
    @Test
    public void UmFilmeDeveSerSelecionado() {
        Locacao locacao = new Locacao();
        locacao.setDataLocacao(DataUtils.obterData(20, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(28, 12, 2018));
        locacao.setFilmes(null);
        locacao.setValor(2.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();
        assertThat(msg, is("Pelo menos um filme deve ser selecionado"));
    }
}