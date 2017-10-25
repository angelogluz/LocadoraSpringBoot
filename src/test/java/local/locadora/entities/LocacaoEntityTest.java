package local.locadora.entities;

import local.locadora.utils.DataUtils;
import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;
import org.hibernate.query.criteria.internal.expression.function.CurrentTimeFunction;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
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
    public void dataDeLocacaoNaoPodeSerNulo() {
        Locacao locacao = new Locacao();
//        locacao.setDataLocacao(DataUtils.obterData(0, 0, 0));
        locacao.setDataRetorno(DataUtils.adicionarDias(DataUtils.obterData(0, 0, 0), 5));
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
        locacao.setDataRetorno(DataUtils.adicionarDias(DataUtils.obterData(25, 10, 2017), -2));
        locacao.setValor(2.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data deve retorno deve ser futura"));
    }

    @Test
    public void dataDeRetornoNaoPodeSerNulo() {
        Locacao locacao = new Locacao();
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data de retorno não deve ser nula"));
    }

    @Test
    public void valorDaLocacaoDeveSerPositivo() {
        Locacao locacao = new Locacao();
        locacao.setDataRetorno(DataUtils.adicionarDias(DataUtils.obterData(25, 10, 2017), 2));
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
        locacao.setDataRetorno(DataUtils.adicionarDias(DataUtils.obterData(25, 10, 2017), 2));
        locacao.setValor(987.654);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("O Preço deve ter no máximo dois dígitos"));
    }
}
