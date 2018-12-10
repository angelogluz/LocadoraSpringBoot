
package local.locadora.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.LocadoraException;
import local.locadora.utils.DataUtils;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/**
 *
 * @author Marciel
 */
public class LocacaoEntityTest {
    
    
    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
   

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
    
    @Test
    public void umClienteDeveSerSelecionado() {
        Locacao locacao = new Locacao();
        locacao.setDataLocacao(DataUtils.obterData(17, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(27, 12, 2018));
        locacao.setCliente(null);
        locacao.setValor(2.0);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String msg = x.getMessage();
        
        assertThat(msg, is("Um cliente deve ser selecionado"));
    }


    @Test
    public void deveSelecionarUmFilme() {
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
    

    @Test
    public void naoDeveValidarUmaLocacaoDeFilmeSemEstoque() {
        //Cenário
        Locacao locacao = new Locacao();
        Filme filme = new Filme("Matrix", 0, 4.0);

        //Ação
        try {
            locacao.addFilme(filme);
        } catch (Exception e) {
            Object ExceptionLocacao = null;
            
            Assert.assertSame(ExceptionLocacao, e);
        }
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
    public void dataDeRetornoNaoPodeSerNulo() {
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(20, 12, 2018));
        locacao.setValor(14.50);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data de retorno não deve ser nula"));
    }


    @Test
    public void dataDeRetornoDeveSerFutura() {
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(22, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(20, 12, 2010));
        locacao.setValor(2.00);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data deve retorno deve ser futura"));
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
    public void seLocarNoSabadoEntregaNaSegunda(){
        String data="";
        Date dt = new Date();
        Cliente cliente = new Cliente("Rambo");
        Filme filme = new Filme("BD vs PHP", 5, 2.00);
        Locacao loc = new Locacao();
        loc.addFilme(filme);
        loc.setCliente(cliente);
        loc.setValor(2.00);
        loc.setDataLocacao(new Date());
        
        if(DataUtils.verificarDiaSemana(dt, Calendar.SUNDAY)){
            dt = DataUtils.adicionarDias(dt, 2);
            data = dt.toString();
            loc.setDataRetorno(dt);
            System.out.println(dt+"Sunday");
	}
        
        if(DataUtils.verificarDiaSemana(dt, Calendar.MONDAY)){
            dt = DataUtils.adicionarDias(dt, 1);
            data = dt.toString();
            loc.setDataRetorno(dt);
            System.out.println(dt+"Monday");
	}
        
        if(DataUtils.verificarDiaSemana(dt, Calendar.SUNDAY)){
            fail("Data de Entrega não pode ser domingo!");
	}

        Assert.assertNotSame(loc.getDataLocacao(), loc.getDataRetorno());

		
    }

    @Test
    public void numeroDiasRecebimentoAumentadoPelosFilmes() {
        Locacao loc = new Locacao();
        Filme filme = new Filme("Gladimir vs Angelo", 5, 2.00);
        loc.addFilme(filme);
        Cliente cliente = new Cliente("Rambo");
        loc.setCliente(cliente);
        loc.setDataLocacao(new Date());
        Date date = new Date();
        date.setTime(date.getTime() + 10000);
        loc.setDataRetorno(date);
        loc.setValor(2.00);
        
        // gera uma data representando amanhã
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        // seta data retorno para amanhã
        loc.setDataRetorno(dt);
        
        // gera a data para a quantidade de filmes
        c.add(Calendar.DATE, loc.getFilmes().size());
        dt = c.getTime();
        
        // verifica se a data ficou correta
        if (loc.getDataRetorno() != dt) {
            System.out.println("Teste");
        }
    }
    
}
