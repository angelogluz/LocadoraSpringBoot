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
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Claudio
 */
public class FilmeEntityTeste {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    /*
    O nome não deverá aceitar espaços em branco no início e no fim Mensagem de 
    validação: Não possui. A aplicação deve elimiar os espaços;
     */
    
    @Test
    public void nomeDoFilmeComEspacoEmBranco() {
        Filme filme = new Filme();
        filme.setNome(" O Batatinha ");
        assertThat("O Batatinha", is(filme.getNome()));
    }
    
    /*
    O nome deve possuir entre 2 e 100 caracteres, inclusive. Mensagem de validação:
    "Um filme deve possuir entre 2 e 100 caracteres";
    */
    @Test
    public void nomeDevePossuirEntre2e100Caracteres() {
        Filme filme = new Filme();
        filme.setNome("Re");
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));

    }
    
    /*O estoque do filme não pode ser negativo Mensagem de validação:
    "O Estoque deve ser positivo";
    */
    @Test
    public void estoqueNaoDeveSernegativo(){
        
        Filme filme = new Filme();
        filme.setEstoque(-10);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O Estoque deve ser positivo"));
        
    }
    
   
    
    
    
    
    
    

}
