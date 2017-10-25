package local.locadora.entities;

import java.util.Date;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.utils.DataUtils;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author 781410121
 */
public class LocacaoEntityTest {
    
    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void naoDeveValidarLocacaoSemDataDeLocacao(){
        //cenario
        Locacao locacao = new Locacao();
        Cliente cliente = new Cliente("Felipe");
        Filme filme = new Filme("Batman", 4, 4.0);
        //acao
        locacao.setCliente(cliente);
        locacao.addFilme(filme);
        locacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(1));
        locacao.setValor(10.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
        //validacao
        assertThat(message,is("A data de locação não deve ser nula"));
    }
    
    @Test
    public void naoDeveValidarLocacaoSemDataDeRetorno(){
        //cenario
        Locacao locacao = new Locacao();
        Cliente cliente = new Cliente("Felipe");
        Filme filme = new Filme("Batman", 4, 4.0);
        //acao
        locacao.setCliente(cliente);
        locacao.addFilme(filme);
        locacao.setDataLocacao(new Date());
        locacao.setValor(10.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
        //validacao
        assertThat(message,is("A data de retorno não deve ser nula"));
    }
    
    @Test
    public void naoDeveValidarLocacaoComDataDeRetornoPassada(){
        //cenario
        Locacao locacao = new Locacao();
        Cliente cliente = new Cliente("Felipe");
        Filme filme = new Filme("Batman", 4, 4.0);
        //acao
        locacao.setCliente(cliente);
        locacao.addFilme(filme);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(-1));
        locacao.setValor(10.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
        //validacao
        assertThat(message,is("A data deve retorno deve ser futura"));
    }
    
    @Test
    public void naoDeveValidarLocacaoComValorComMaisDeDoisDigitos() {
        //cenario
        Locacao locacao = new Locacao();
        Cliente cliente = new Cliente("Felipe");
        Filme filme = new Filme("Batman", 4, 4.0);
        //acao
        locacao.setCliente(cliente);
        locacao.addFilme(filme);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(1));
        locacao.setValor(100.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
       //validacao
       assertThat(message,is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void naoDeveValidarLocacaoComValorComMaisDeDuasCasasAposAVirgula() {
        //cenario
        Locacao locacao = new Locacao();
        Cliente cliente = new Cliente("Felipe");
        Filme filme = new Filme("Batman", 4, 4.0);
        //acao
        locacao.setCliente(cliente);
        locacao.addFilme(filme);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(1));
        locacao.setValor(100.563);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
       //validacao
       assertThat(message,is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void naoDeveValidarLocacaoComValorDeLocacaoNegativo() {
        //cenario
        Locacao locacao = new Locacao();
        Cliente cliente = new Cliente("Felipe");
        Filme filme = new Filme("Batman", 4, 4.0);
        //acao
        locacao.setCliente(cliente);
        locacao.addFilme(filme);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(1));
        locacao.setValor(-8.50);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
       //validacao
       assertThat(message,is("O valor da locação deve ser positivo"));
    }
}
