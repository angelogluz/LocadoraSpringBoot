/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.exceptions.FilmeSemEstoqueException;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Claudio
 */
public class LocacaoEntityTeste {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /*Uma locação não deverá ser realizada sem um cliente Mensagem de validação: 
    "Um cliente deve ser selecionado";
     */
    @Test
    public void naoDeveFazerLocacaoSemCliente() {
        Locacao locacao = new Locacao();
        locacao.setCliente(null);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("Um cliente deve ser selecionado"));
    }
    
     /*
    Uma locação não pode ser realizada sem data de locação Mensagem de validação: 
    "A data de locação não deve ser nula";
    */
     @Test
    public void dataDeLocacaoNaoDeveSerNula(){
        
        Locacao locacao = new Locacao();
        locacao.setDataLocacao(null);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("A data de retorno não deve ser nula"));
        
    }
    
    /*
    Uma locação de filme sem estoque não poderá ser realizada Mensagem de validação:
    Sem mensagem. Uma Exception deverá ser lançada;
    */
    @Test(expected = FilmeSemEstoqueException.class)
    public void naoDeveLocarFilmeSemEstoque() throws FilmeSemEstoqueException{
        
        List<Filme> filmes = new ArrayList<>();
        filmes.add(new Filme("Trinity é o meu nome", 0 ,12.50));
        Locacao locacao = new Locacao();
        locacao.setFilmes(filmes);
        
        
        
    }
        
        
    
    
    
}
