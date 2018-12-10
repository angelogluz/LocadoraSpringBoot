/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Matheus Vieira
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
    public void aDataDeLocacaoNaoDeveSerNula(){
        Date data = null;
        Locacao locacao = new Locacao();
        locacao.setDataLocacao(data);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("A data de locação não deve ser nula"));
    }
    
    @Test
    public void aDataDeRetornoNaoDeveSerNula(){
        Date data = null;
        Locacao locacao = new Locacao();
        locacao.setDataLocacao(data);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("A data de retorno não deve ser nula"));
    }
    
    @Test
    public void aLocacaoNaoDeveSerRealizadaSemCliente(){
        Locacao locacao = new Locacao();
        locacao.getCliente();
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um cliente deve ser selecionado"));
    }
    
    @Test
    public void oValorDaLocacaoDeveSerPositivo(){
        double valor = -10.00;
        Locacao locacao = new Locacao();
        locacao.setValor(valor);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O valor da locação deve ser positivo"));
    }
    
    @Test
    public void oPrecoDaLocacaoDeveTerDoisDigitos(){
        double preco = 450.00;
        Locacao locacao = new Locacao();
        locacao.setValor(preco);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    

    
}
