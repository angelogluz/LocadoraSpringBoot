/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.model;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.entities.Filme;
import local.locadora.entities.Locacao;
import local.locadora.utils.DataUtils;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author grazz
 */
public class LocacaoTeste {
    
     private static Validator validator;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
/*Uma locação não deverá ser realizada sem um cliente Mensagem de validação: "Um cliente deve ser selecionado";*/
    
    @Test
    public void naoDeveTerLocacaoSemUsuario(){
        
    }
    

/*Uma locação deverá possuir pelo menos 1 filme Mensagem de validação: "Pelo menos um filme deve ser selecionado";*/
    
    @Test
    public void locacaoDeveTerPeloMenosUmFilme(){
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(10, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(12, 12, 2018));
        locacao.setFilmes(null);
        
        locacao.setValor(10.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("Pelo menos um filme deve ser selecionado"));
    }
    

/*Uma locação de filme sem estoque não poderá ser realizada Mensagem de validação: Sem mensagem. Uma Exception deverá ser lançada;*/
   @Test
    public void filmeSemEstoqueNaoPodeSerAlugado(){ 
    Locacao locacao = new Locacao();
        
        
    }

/*Uma locação não pode ser realizada sem data de locação Mensagem de validação: "A data de locação não deve ser nula";*/
    @Test
    public void locacaoNaoDeveSerRealizadaSemDataLocacao(){
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(10, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(12, 12, 2018));
        locacao.setFilmes(null);
        
        locacao.setValor(10.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("Pelo menos um filme deve ser selecionado"));
    }

/*Uma locação não pode ser realizada sem data de devolução Mensagem de validação: "A data de retorno não deve ser nula";*/
    @Test
    public void locacaoNaoPodeSerRealizadaSemDataDeDevolucao(){
        Locacao ls = new Locacao();
        Filme filmes = new Filme("It", 1, 1.0);

        ls.setDataLocacao(DataUtils.obterData(10, 12, 2018));
        ls.setDataRetorno(null);
        
        ls.setValor(10.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data de retorno não deve ser nula"));
    }

/*A data de devolução do filme não pode ser uma data no passado Mensagem de validação: "A data deve retorno deve ser futura";*/
    
    @Test
    public void dataDevolucaoNaoDeveSerAnteriorADataDeLocacao(){
        Locacao ls = new Locacao();
        Filme filmes = new Filme("It", 1, 1.0);

        ls.setDataLocacao(DataUtils.obterData(10, 12, 2018));
        ls.setDataRetorno(DataUtils.obterData(8, 12, 2010));
        
        ls.setValor(10.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(ls);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data deve retorno deve ser futura"));

    }
    

/*O valor da locação deve possuir no máximo dois dígitos antes e depois da vírgula Mensagem de validação: "O Preço deve ter no máximo dois dígitos";*/
    @Test
    public void precoDeveTerNoMaximoDoisDigitos() {
        Filme filme = new Filme();
        filme.setPrecoLocacao(123.321);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();

        assertThat(msg, is("O Preço deve ter no máximo dois dígitos"));
    }

/*O valor da locação deve ser sempre positivo Mensagem de validação: "O valor da locação deve ser positivo";*/
    
    @Test
    public void valorDaLocacaoDeveSerSemprePositivo() {
        Filme filme = new Filme();
        filme.setPrecoLocacao(-10.0);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();

        assertThat(msg, is("O Valor da locação deve ser positivo"));
    }

/*Caso um filme tenha sua entrega prevista para domingo, deverá registrada para segunda-feira. Mensagem de validação:
Nenhuma. Uma Exception deverá lançada;*/
    


/*Ao alugar um filme a data de entrega deve ter o número de dias incrementado de forma proporcional ao número de filmes alugados.
Mensagem de validação: Nenhuma. Uma Exception deverá lançada;*/
    
}
