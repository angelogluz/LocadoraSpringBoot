
package local.locadora.entities;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.utils.DataUtils;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author wally
 */
public class LocacaoEntityTest {
    
    
     private static Validator validator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    /*Uma locação não deverá ser realizada sem um cliente Mensagem de validação:
    "Um cliente deve ser selecionado";*/
    
     @Test
    public void naoDeveSerPossivelLocarSemCliente() {
        Locacao locacao = new Locacao();
        locacao.setCliente(null);
        locacao.setDataLocacao(DataUtils.obterData(10, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(20, 12, 2018));
        locacao.setValor(3.0);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String msg = x.getMessage();
        
        assertThat(msg, is("Um cliente deve ser selecionado"));
    }
    /*Uma locação deverá possuir pelo menos 1 filme Mensagem de validação: 
    "Pelo menos um filme deve ser selecionado";*/
    
      @Test
    public void locacaoDeveTerPeloMenos1Filme() {
        Locacao l = new Locacao();
        l.setFilmes(null);
        l.setDataLocacao(DataUtils.obterData(10, 12, 2018));
        l.setDataRetorno(DataUtils.obterData(20, 12, 2018));
        l.setValor(3.0);

        Set<ConstraintViolation<Locacao>> violations = validator.validate(l);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String msg = x.getMessage();
        
        assertThat(msg, is("Pelo menos um filme deve ser selecionado"));
    }
    
    /*Uma locação de filme sem estoque não poderá ser realizada Mensagem de validação:
    Sem mensagem. Uma Exception deverá ser lançada;*/
    
    /*Uma locação não pode ser realizada sem data de locação Mensagem de validação:
    "A data de locação não deve ser nula"*/
    
    @Test
    public void dataDeLocacaoNaoPodeSerNulo() {
        Locacao l = new Locacao();
        l.setDataRetorno(DataUtils.obterData(25, 12, 2018));
        l.setValor(3.0);

        Set<ConstraintViolation<Locacao>> violations = validator.validate(l);
        
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data de locação não deve ser nula"));
    }
    /*Uma locação não pode ser realizada sem data de devolução Mensagem de validação:
    "A data de retorno não deve ser nula";*/
    
     @Test
    public void naoDeveFazerLocacaoSemDataDeRetorno() {
        Locacao locacao = new Locacao();
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data de locação não deve ser nula"));
    }

    /*A data de devolução do filme não pode ser uma data no passado Mensagem de validação:
    "A data deve retorno deve ser futura";*/
    
     @Test
    public void naoDeveSerNoPassadoADevolucao() {
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(25, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(10, 15, 2015));
        locacao.setValor(3.0);

        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String msg = x.getMessage();
        
        assertThat(msg, is("A data deve retorno deve ser futura"));
    }
    
    /*O valor da locação deve possuir no máximo dois dígitos antes e depois da 
    vírgula Mensagem de validação: "O Preço deve ter no máximo dois dígitos"*/
    
 @Test
    public void valorDeveTerNoMaximo2Digitos() {
        Locacao locacao = new Locacao();
        locacao.setDataLocacao(DataUtils.obterData(25, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(10, 15, 2015));
        locacao.setValor(987.654);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String msg = x.getMessage();
        
        assertThat(msg, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    /*O valor da locação deve ser sempre positivo Mensagem de validação:
    "O valor da locação deve ser positivo";*/
    
     @Test
    public void valorDaLocacaoDeveSerPositivo() {
        Locacao locacao = new Locacao();   
        locacao.setDataLocacao(DataUtils.obterData(25, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(10, 15, 2015));
        locacao.setValor(-1.00);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String msg = x.getMessage();
        
        assertThat(msg, is("O valor da locação deve ser positivo"));
    }
    
    /*Caso um filme tenha sua entrega prevista para domingo, deverá registrada para segunda-feira. 
    Mensagem de validação: Nenhuma. Uma Exception deverá lançada;*/
    
    /*Ao alugar um filme a data de entrega deve ter o número de dias incrementado de forma proporcional 
    ao número de filmes alugados. Mensagem de validação: Nenhuma. Uma Exception deverá lançada;*/
}
