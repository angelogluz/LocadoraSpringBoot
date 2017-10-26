/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import static local.locadora.utils.DataUtils.obterData;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
/**
 *
 * @author aluno
 */
public class LocacaoEntityTest {
    
    private static Validator validator;
    
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void naoDeveValidarLocacaoSemCliente() {
        Cliente cliente = null;
        List<Filme> filmes = new ArrayList<Filme>();
        Filme filme = new Filme("Dogma", 8, 5.00);
        filmes.add(filme);
        Locacao locacao = new Locacao();
        Date dataLocacao = obterData(25, 10, 2017);
        Date dataEntrega = obterData(26, 10, 2017);
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setDataLocacao(dataLocacao);
        locacao.setDataRetorno(dataEntrega);
        locacao.setValor(4.00);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um cliente deve ser selecionado"));
    }
    
    @Test
    public void naoDeveValidarLocacaoSemFilme() {
        Cliente cliente = new Cliente("Foo");
        List<Filme> filmes = new ArrayList<Filme>();
        Filme filme = null;
        filmes.add(filme);
        Date dataLocacao = obterData(25, 10, 2017);
        Date dataEntrega = obterData(26, 10, 2017);
        Locacao locacao = new Locacao();
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setDataLocacao(dataLocacao);
        locacao.setDataRetorno(dataEntrega);
        locacao.setValor(4.00);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Pelo menos um filme deve ser selecionado"));
    }
    
    @Test
    public void naoDeveValidarLocacaoComFilmeSemEstoque() {
        Cliente cliente = new Cliente("Foo");
        List<Filme> filmes = new ArrayList<Filme>();
        Filme filme = new Filme("Dogma", 0, 5.00);
        Filme filme2 = new Filme("Dogma2", 0, 5.00);
        filmes.add(filme);
        filmes.add(filme2);
        Date dataLocacao = obterData(25, 10, 2017);
        Date dataEntrega = obterData(26, 10, 2017);
        Locacao locacao = new Locacao();
        locacao.setCliente(cliente);
        locacao.setFilmes(filmes);
        locacao.setDataLocacao(dataLocacao);
        locacao.setDataRetorno(dataEntrega);
        locacao.setValor(4.00);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Pelo menos um filme deve ser selecionado"));
    }
    
    @Test
    public void naoDeveValidarLocacaoSemDataDeLocacao() {
        Cliente cliente = new Cliente("Foo");
        List<Filme> filmes = new ArrayList<Filme>();
        Filme filme = new Filme("Dogma", 0, 5.00);
        Filme filme2 = new Filme("Dogma2", 0, 5.00);
        filmes.add(filme);
        filmes.add(filme2);
        Date dataLocacao = null;
        Date dataEntrega = obterData(26, 10, 2017);
        Locacao locacao = new Locacao();
        locacao.setCliente(cliente);
        //locacao.setFilmes(filmes);
        locacao.setDataLocacao(dataLocacao);
        locacao.setDataRetorno(dataEntrega);
        locacao.setValor(4.00);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("A data de locação não deve ser nula"));
    }
    
    @Test
    public void naoDeveValidarLocacaoSemDataDeRetorno() {
        Cliente cliente = new Cliente("Foo");
        List<Filme> filmes = new ArrayList<Filme>();
        Filme filme = new Filme("Dogma", 0, 5.00);
        Filme filme2 = new Filme("Dogma2", 0, 5.00);
        filmes.add(filme);
        filmes.add(filme2);
        Date dataLocacao = obterData(25, 10, 2017);
        Date dataEntrega = null;
        Locacao locacao = new Locacao();
        locacao.setCliente(cliente);
        //locacao.setFilmes(filmes);
        locacao.setDataLocacao(dataLocacao);
        locacao.setDataRetorno(dataEntrega);
        locacao.setValor(4.00);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("A data de retorno não deve ser nula"));
    }
    
    @Test
    public void naoDeveValidarLocacaoSemDataDeRetornoNoPassado() {
        Cliente cliente = new Cliente("Foo");
        List<Filme> filmes = new ArrayList<Filme>();
        Filme filme = new Filme("Dogma", 0, 5.00);
        Filme filme2 = new Filme("Dogma2", 0, 5.00);
        filmes.add(filme);
        filmes.add(filme2);
        Date dataLocacao = obterData(25, 10, 2017);
        Date dataEntrega = obterData(20, 10, 2017);
        Locacao locacao = new Locacao();
        locacao.setCliente(cliente);
        //locacao.setFilmes(filmes);
        locacao.setDataLocacao(dataLocacao);
        locacao.setDataRetorno(dataEntrega);
        locacao.setValor(4.00);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("A data de retorno não deve ser nula"));
    }
    
    
}
