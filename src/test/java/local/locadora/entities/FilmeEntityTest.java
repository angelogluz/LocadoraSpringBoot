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
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author dionatanmelo
 */
public class FilmeEntityTest {
    
    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    public FilmeEntityTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * O nome deve possuir entre 2 e 100 caracteres, inclusive. 
     * Mensagem de validação: "Um filme deve possuir entre 2 e 100 caracteres";
     */
    @Test
    public void notBeLessThanTwoCharacters() {
        //Instance of Filme Class
        Filme movie = new Filme();
        movie.setNome("T");
        Set<ConstraintViolation<Filme>> violations = validator.validate(movie);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    /**
     * O nome deve possuir entre 2 e 100 caracteres, inclusive. 
     * Mensagem de validação: "Um filme deve possuir entre 2 e 100 caracteres";
     */
    @Test
    public void canNotBeLongerThanOneHundredCharacters() {
        //Instance of Filme Class
        Filme movie = new Filme();
        movie.setNome("O tamanho do nome do filme não pode ser maior que cem, senão deve ser apresentado uma mensagem de retorno.");
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(movie);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String message = x.getMessage();
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    /**
     * O estoque do filme não pode ser negativo Mensagem de validação: 
     * "O Estoque deve ser positivo";
     */
    @Test
    public void stockMustBePositive() {
        //Instance of Filme Class
        Filme movie = new Filme();
        //change stock to -1
        movie.setEstoque(-1);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(movie);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String msg = x.getMessage();

        assertThat(msg, is("O Estoque deve ser positivo"));
    }
    
    /**
     * O valor da locação não deverá ultrapassar dois dígitos e o número de 
     * casas após a vírgula deverá ser dois. Mensagem de validação: "O Preço deve 
     * ter no máximo dois dígitos";
     */
    @Test
    public void mustHaveMaximumOfTwoDigits() {
        Filme movie = new Filme();
        movie.setPrecoLocacao(200.999);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(movie);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String msg = x.getMessage();
        assertThat(msg, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    /**
     * O valor da locação do filme deverá ser positivo Mensagem de validação: 
     * "O Valor da locação deve ser positivo";
     */
    @Test
    public void notAcceptNegativeValue() {
        Filme movie = new Filme();
        movie.setPrecoLocacao(-9.0);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(movie);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String msg = x.getMessage();
        assertThat(msg, is("O Valor da locação deve ser positivo"));
    }
}
