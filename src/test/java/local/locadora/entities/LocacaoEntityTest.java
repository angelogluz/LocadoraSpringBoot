/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.dao.FilmeDAO;
import local.locadora.dao.LocacaoDAO;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author dionatan
 */
public class LocacaoEntityTest {
    
    private static Validator validator;

    @Autowired
    private LocacaoDAO locacaoRepository;
    
     @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void testaFilmeValorNaoPodeNegativo() {
        Locacao locacao = new Locacao("Filme 2", 11, -4.0);
        Locacao l1 = locacaoRepository.save(locacao);
        List<Locacao> l = locacaoRepository.findByNome(l1.getNome());
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl teste = (ConstraintViolationImpl) it.next();
        String message = teste.getMessage();
 
        assertThat(message,is("O valor da locação deve ser positivo"));
        
    }
    
     @Test
     public void testaDevePossuirDataDeRetorno() {
 
          Locacao locacao = new Locacao();
          locacao.setDataRetorno(null);
     
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl teste = (ConstraintViolationImpl) it.next();
        String message = teste.getMessage();
 
        assertThat(message,is("A data de retorno não deve ser nula"));
   }
     
   @Test
     public void testaDevePossuirDataDeLocacao() {
 
          Locacao locacao = new Locacao();
          locacao.setDataLocacao(null);
     
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl teste = (ConstraintViolationImpl) it.next();
        String message = teste.getMessage();
 
        assertThat(message,is("A data de locação não deve ser nula"));
   }
     
     @Test
     public void testaPrecoNoMaximoDoisDigitos() {
 
          Locacao locacao = new Locacao();
          locacao.setValor(-4.0);
     
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl teste = (ConstraintViolationImpl) it.next();
        String message = teste.getMessage();
 
        assertThat(message,is("O valor da locação deve ser positivo"));
   }
     
     
}
