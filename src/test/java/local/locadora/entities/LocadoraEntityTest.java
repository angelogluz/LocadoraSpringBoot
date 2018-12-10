/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dionatanmelo
 */
public class LocadoraEntityTest {
    
    private static Validator validator;

    
    public LocadoraEntityTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Uma locação não deverá ser realizada sem um cliente Mensagem de validação: 
     * "Um cliente deve ser selecionado";
     */
    @Test
    public void mustSelectAOneCustomer() {
        Locacao location = new Locacao();
        
        location.setDataLocacao(DataUtils.obterData(02, 01, 2019));
        location.setDataRetorno(DataUtils.obterData(05, 01, 2019));
        location.setCliente(null);
        location.setValor(3.90);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(location);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();
        assertThat(msg, is("Um cliente deve ser selecionado"));
    }
    
    /**
     * Uma locação deverá possuir pelo menos 1 filme Mensagem de validação: 
     * "Pelo menos um filme deve ser selecionado";
     */
    @Test
    public void mustSelectAOneMovie() {
        Locacao location = new Locacao();
        
        location.setDataLocacao(DataUtils.obterData(02, 01, 2019));
        location.setDataRetorno(DataUtils.obterData(05, 01, 2019));
        location.setFilmes(null);
        
        location.setValor(2.0);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(location);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();
        assertThat(msg, is("Pelo menos um filme deve ser selecionado"));
    }
    
    /**
     * Uma locação de filme sem estoque não poderá ser realizada Mensagem de validação: Sem mensagem. 
     * Uma Exception deverá ser lançada;
     */
    @Test
    public void dateShouldNotBeNull() {
        Locacao location = new Locacao();
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(location);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();
        assertThat(msg, is("A data de retorno não deve ser nula"));
    }

    /**
     * Uma locação não pode ser realizada sem data de locação Mensagem de validação: 
     * "A data de locação não deve ser nula";
     */
    @Test
    public void dateOfLocationShouldNotBeNull() {
        Locacao location = new Locacao();
        
        location.setDataRetorno(DataUtils.obterData(02, 02, 2019));
        location.setValor(3.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(location);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();
        assertThat(msg, is("A data de locação não deve ser nula"));
    }

    /**
     * A data de devolução do filme não pode ser uma data no passado Mensagem de validação: 
     * "A data deve retorno deve ser futura";
     */
    @Test
    public void dataShouldBeFuture() {
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(17, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(12, 10, 2018));
        
        locacao.setValor(3.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();
        assertThat(msg, is("A data deve retorno deve ser futura"));
    }

    /**
     * O valor da locação deve ser sempre positivo Mensagem de validação: 
     * "O valor da locação deve ser positivo";
     */
    @Test
    public void valueSholdBePositive() {
        Locacao location = new Locacao();
        
        location.setDataLocacao(DataUtils.obterData(02, 01, 2019));
        location.setDataRetorno(DataUtils.obterData(05, 01, 2019));
        
        location.setValor(-3.00);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(location);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();
        assertThat(msg, is("O valor da locação deve ser positivo"));
    }
    
    /**
     * O valor da locação deve ser sempre positivo Mensagem de validação: 
     * "O valor da locação deve ser positivo";
     */
    @Test
    public void valueMustHaveTwoDigits() {
        Locacao location = new Locacao();
        
        location.setDataLocacao(DataUtils.obterData(02, 01, 2019));
        location.setDataRetorno(DataUtils.obterData(05, 01, 2019));
        
        location.setValor(1000.999);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(location);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();
        assertThat(msg, is("O Preço deve ter no máximo dois dígitos"));
    } 
}
