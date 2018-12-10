/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
 * @author ftlor
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
    
    @Test
    public void locacaoPrecisaTerClienteSelecionado() {
        
        //Cenário
        List<Filme> filmes = new ArrayList();
        filmes.add(new Filme("Rambo 1", 5, 3.0));
        filmes.add(new Filme("Rambo 2", 0, 2.5));
        Locacao locacao = new Locacao();
        
        //Ação
        locacao.setCliente(null);
        locacao.setFilmes(filmes);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(new Date("2019/01/01"));
        locacao.setValor(12.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("Um cliente deve ser selecionado"));
    }
    
    @Test
    public void locacaoPrecisaTerFilmeSelecionado() {
        
        //Cenário
        Cliente cliente = new Cliente("Fabricio", "93786778000");
        Locacao locacao = new Locacao();
        
        //Ação
        locacao.setCliente(cliente);
        locacao.setFilmes(null);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(new Date("2019/01/01"));
        locacao.setValor(12.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("Pelo menos um filme deve ser selecionado"));
    }
    
    @Test
    public void naoDeveLocarFilmeSemEstoque() {
        
        //Cenário
        List<Filme> filmes = new ArrayList<>();
        filmes.add(new Filme("Rambo III", 0 ,12.50));
        
        //Ação
        Locacao locacao = new Locacao();
        locacao.setFilmes(filmes);
        
        //Validação
    }
    
    @Test
    public void dataDeLocacaoNaoDeveSerNula(){
        
        //Cenário
        Locacao locacao = new Locacao();
        
        //Ação
        locacao.setValor(5.50);
        locacao.setDataLocacao(null);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("A data de retorno não deve ser nula"));
    }
    
    @Test
    public void dataDeDevolucaoNaoDeveSerNula(){
        
        //Cenário
        Cliente cliente = new Cliente("Fabricio", "93786778000");
        List<Filme> filmes = new ArrayList();
        filmes.add(new Filme("Rambo 1", 5, 3.0));
        filmes.add(new Filme("Rambo 2", 0, 2.5));
        Locacao locacao = new Locacao();
        
        //Ação
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(null);
        locacao.setValor(5.5);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("A data de retorno não deve ser nula"));
    }
    
    @Test
    public void dataDeDevolucaoDeveSerFutura(){
        
        //Cenário
        Cliente cliente = new Cliente("Fabricio", "93786778000");
        List<Filme> filmes = new ArrayList();
        filmes.add(new Filme("Rambo 1", 5, 3.0));
        filmes.add(new Filme("Rambo 2", 0, 2.5));
        Locacao locacao = new Locacao();
        
        //Ação
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(new Date());
        locacao.setValor(5.5);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("A data de retorno deve ser futura"));
    }
    
    @Test
    public void precoDeveTerNoMaximoDoisDigitosAntesDaVirgula(){
        
        //Cenário
        Cliente cliente = new Cliente("Fabricio", "93786778000");
        List<Filme> filmes = new ArrayList();
        filmes.add(new Filme("Rambo 1", 5, 3.0));
        filmes.add(new Filme("Rambo 2", 0, 2.5));
        Locacao locacao = new Locacao();
        
        //Ação
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(new Date("2019/01/01"));
        locacao.setValor(105.5);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void precoDeveTerNoMaximoDoisDigitosDepoisDaVirgula(){
        
        //Cenário
        Cliente cliente = new Cliente("Fabricio", "93786778000");
        List<Filme> filmes = new ArrayList();
        filmes.add(new Filme("Rambo 1", 5, 3.0));
        filmes.add(new Filme("Rambo 2", 0, 2.5));
        Locacao locacao = new Locacao();
        
        //Ação
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(new Date("2019/01/01"));
        locacao.setValor(5.555);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void precoDeveSerPositivo(){
        
        //Cenário
        Cliente cliente = new Cliente("Fabricio", "93786778000");
        List<Filme> filmes = new ArrayList();
        filmes.add(new Filme("Rambo 1", 5, 3.0));
        filmes.add(new Filme("Rambo 2", 0, 2.5));
        Locacao locacao = new Locacao();
        
        //Ação
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(new Date("2019/01/01"));
        locacao.setValor(-5.55);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("O valor da locação deve ser positivo"));
    }
    
    @Test
    public void locacaoSabadoEntregaSegunda(){
        
        //Cenário
        Cliente cliente = new Cliente("Fabricio", "93786778000");
        List<Filme> filmes = new ArrayList();
        filmes.add(new Filme("Rambo 1", 5, 3.55));
        Locacao locacao = new Locacao();
        Date dt;
        
        //Ação
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setValor(3.55);
        locacao.setDataLocacao(new Date("2018/12/08"));
        dt = locacao.getDataLocacao();
        if(DataUtils.verificarDiaSemana(dt, Calendar.SATURDAY)){
            dt = DataUtils.adicionarDias(dt, 2);
            locacao.setDataRetorno(dt);
            /*System.out.println("LOCACAO "+locacao.getDataLocacao());
            System.out.println("RETORNO "+locacao.getDataRetorno());
            System.out.println(dt+"Segunda");*/
	} else {
            dt = DataUtils.adicionarDias(dt, 1);
            locacao.setDataRetorno(dt);
            /*System.out.println("LOCACAO "+locacao.getDataLocacao());
            System.out.println("RETORNO "+locacao.getDataRetorno());
            System.out.println(dt+"Resto");*/
        }
        
        //Validação
        /*Não possui Validação
        assertSame(locacao.getDataLocacao(), locacao.getDataRetorno());
        */
    }
    
}
