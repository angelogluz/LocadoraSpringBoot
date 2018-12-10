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

//Uma locação não deverá ser realizada sem um cliente Mensagem de validação: "Um cliente deve ser selecionado";
     @Test
    public void nãoDeveLocarSemCliente() {
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(12, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(20, 12, 2018));
        locacao.setCliente(null);
        
        locacao.setValor(5.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();

        assertThat(msg, is("Um cliente deve ser selecionado"));
    }

//Uma locação deverá possuir pelo menos 1 filme Mensagem de validação: "Pelo menos um filme deve ser selecionado";
      @Test
    public void nãoDeveLocarSemFilme() {
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(12, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(29, 12, 2018));
        locacao.setFilmes(null);
        
        locacao.setValor(5.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();

        assertThat(msg, is("Pelo menos um filme deve ser selecionado"));
    }

//Uma locação de filme sem estoque não poderá ser realizada Mensagem de validação: Sem mensagem. Uma Exception deverá ser lançada;

//Uma locação não pode ser realizada sem data de locação Mensagem de validação: "A data de locação não deve ser nula";
      @Test
    public void nãoDeveLocarSemDataLocacao() {
        Locacao locacao = new Locacao();
        
        locacao.setDataRetorno(DataUtils.obterData(12, 12, 2018));
        locacao.setValor(5.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data de locação não deve ser nula"));
    }
    

//Uma locação não pode ser realizada sem data de devolução Mensagem de validação: "A data de retorno não deve ser nula";
      @Test
    public void nãoDeveLocarSemDataRetorno() {
        Locacao locacao = new Locacao();
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();

        assertThat(msg, is("A data de retorno não deve ser nula"));
    }

//A data de devolução do filme não pode ser uma data no passado Mensagem de validação: "A data deve retorno deve ser futura";
    @Test
    public void dataRetornoDeveSerMaiorTestaDia() {
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(18, 10, 2018));
        locacao.setDataRetorno(DataUtils.obterData(04, 10, 2018));
        
        locacao.setValor(2.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data deve retorno deve ser futura"));
    }
    
     @Test
    public void dataRetornoDeveSerMaiorTestaAno() {
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(18, 10, 2018));
        locacao.setDataRetorno(DataUtils.obterData(18, 10, 2017));
        
        locacao.setValor(2.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data deve retorno deve ser futura"));
    }

//O valor da locação deve possuir no máximo dois dígitos antes e depois da vírgula Mensagem de validação: "O Preço deve ter no máximo dois dígitos";
       @Test
    public void valorDeveTerAteDoisDigitos() {
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(12, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(29, 12, 2018));
        
        locacao.setValor(500.500);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("O Preço deve ter no máximo dois dígitos"));
    }

//O valor da locação deve ser sempre positivo Mensagem de validação: "O valor da locação deve ser positivo";
    
    @Test
    public void valorDaLocacaoDeveSerPositivo() {
        Locacao locacao = new Locacao();

        locacao.setDataLocacao(DataUtils.obterData(18, 10, 2018));
        locacao.setDataRetorno(DataUtils.obterData(28, 10, 2018));

        locacao.setValor(-10.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("O valor da locação deve ser positivo"));
    }

//Caso um filme tenha sua entrega prevista para domingo, deverá registrada para segunda-feira. Mensagem de validação: Nenhuma. Uma Exception deverá lançada;

//Ao alugar um filme a data de entrega deve ter o número de dias incrementado de forma proporcional ao número de filmes alugados. Mensagem de validação: Nenhuma. Uma Exception deverá lançada;
  
}