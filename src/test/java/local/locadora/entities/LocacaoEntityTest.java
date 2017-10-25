package local.locadora.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
 * @author lucas
 */
public class LocacaoEntityTest {
    
    private static Validator validator;
    
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void locacaoPrecisaTerClienteSelecionado() {
        List<Filme> filmes = new ArrayList();
        filmes.add(new Filme("Filme 1", 5, 3.0));
        filmes.add(new Filme("Filme 2", 0, 2.5));
        
        Locacao locacao = new Locacao();
        locacao.setCliente(null);
        locacao.setFilmes(filmes);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(new Date());
        locacao.setValor(12.0);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um cliente deve ser selecionado"));
    }
    
    @Test
    public void locacaoPrecisaTerFilmeSelecionado() {
        Cliente cliente = new Cliente("Lucas", "000.000.000-96");
        
        Locacao locacao = new Locacao();
        locacao.setCliente(cliente);
        locacao.setFilmes(null);
        locacao.setDataLocacao(new Date());
        locacao.setDataRetorno(new Date());
        locacao.setValor(12.0);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Pelo menos um filme deve ser selecionado"));
    }
}