/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author belinglima
 */
public class LocacaoEntityTest {
    
    
    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
   
    /* Uma locação não deverá ser realizada sem um 
    cliente Mensagem de validação: "Um cliente deve ser selecionado"; */
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

//    @Test
//    public void deveSerSelecionadoUmCliente() {
//        Locacao locacao = new Locacao();
//        
//        locacao.setDataLocacao(DataUtils.obterData(20, 12, 2018));
//        locacao.setDataRetorno(DataUtils.obterData(28, 12, 2018));
//        locacao.setValor(2.0);
//        
//        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
//        Iterator it = violations.iterator();
//        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
//
//        String msg = x.getMessage();
//
//        assertThat(msg, is("Um cliente deve ser selecionado"));
//    }
    
     /* Uma locação deverá possuir pelo menos 1 filme 
    Mensagem de validação: "Pelo menos um filme deve ser selecionado"; */
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
    
    /* Uma locação de filme sem estoque não poderá ser realizada 
    Mensagem de validação: Sem mensagem. Uma Exception deverá ser lançada; */
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
    
    /* Uma locação não pode ser realizada sem data de 
    locação Mensagem de validação: "A data de locação não deve ser nula"; */
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
    
    /* Uma locação não pode ser realizada sem data de devolução
    Mensagem de validação: "A data de retorno não deve ser nula"; */ 
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

    /* A data de devolução do filme não pode ser uma data no
    passado Mensagem de validação: "A data deve retorno deve ser futura"; */
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
    
    /* O valor da locação deve possuir no máximo dois dígitos antes e depois da 
    vírgula Mensagem de validação: "O Preço deve ter no máximo dois dígitos"; */
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
    
    
    /* O valor da locação deve ser sempre positivo 
    Mensagem de validação: "O valor da locação deve ser positivo"; */
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

    /* Caso um filme tenha sua entrega prevista para domingo, deverá registrada para 
    segunda-feira. Mensagem de validação: Nenhuma. Uma Exception deverá lançada; */
    @Test
    public void seLocarNoSabadoEntregaNaSegunda(){
        Date dataDeLocacao = new Date();
	Date dataDeEntrega = new Date();
        
        Cliente cliente = new Cliente();
        cliente.setNome("Edecio");
        cliente.setCpf("123.456.789-09");
        
        Locacao locacao = new Locacao();
        locacao.setCliente(cliente);
            	
	assumeTrue(DataUtils.verificarDiaSemana(dataDeLocacao, Calendar.SATURDAY));
	String data="";
        
	if(DataUtils.verificarDiaSemana(dataDeLocacao, Calendar.SATURDAY)){
		dataDeEntrega = DataUtils.adicionarDias(dataDeLocacao, 2);
		data = dataDeEntrega.toString();
	}else{
		dataDeEntrega = DataUtils.adicionarDias(dataDeLocacao, 1);
		data = dataDeEntrega.toString();
	}
	System.out.println("dia da semana: "+dataDeEntrega);
		
        if(data.contains("Sun")){
		fail("Data de Entrega não pode ser domingo!");
	}
	assertTrue(data.contains("Mon"));
	assertFalse(DataUtils.isMesmaData(dataDeLocacao, dataDeEntrega));
		
    }

    /* Ao alugar um filme a data de entrega deve ter o número de dias 
    incrementado de forma proporcional ao número de filmes alugados.
    Mensagem de validação: Nenhuma. Uma Exception deverá lançada; */
    // Faltou este teste
    
}
