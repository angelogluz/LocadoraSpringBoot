package local.locadora.entities;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author 781410121
 */
public class FilmeEntityTest {
    
    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void naoDeveValidarNomeComEspacosEmBrancoNoInicioENoFim() {
        //cenario
        Filme filme = new Filme();
        //acao
        filme.setNome(" Batman ");
        //validacao
        assertEquals("Batman", filme.getNome());
    }
    
    @Test
    public void naoDeveValidarUmNomeComMenosDe2OuMaisDe100Caracteres() {
        //cenario
        Filme filme = new Filme();
        //acao
        filme.setNome("O");
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
        //validacao
        assertThat(message,is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
    public void naoDeveValidarFilmeComEstoqueNegativo() {
        //cenario
        Filme filme = new Filme();
        //acao
        filme.setEstoque(-1);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
       //validacao
       assertThat(message,is("O Estoque deve ser positivo"));
    }
    
    @Test
    public void naoDeveValidarFilmeComValorDeLocacaoComMaisDeDoisDigitos() {
        //cenario
        Filme filme = new Filme();
        //acao
        filme.setPrecoLocacao(100.00);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
       //validacao
       assertThat(message,is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void naoDeveValidarFilmeComValorDeLocacaoComMaisDeDuasCasasAposAVirgula() {
        //cenario
        Filme filme = new Filme();
        //acao
        filme.setPrecoLocacao(100.563);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
       //validacao
       assertThat(message,is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void naoDeveValidarFilmeComValorDeLocacaoNegativo() {
        //cenario
        Filme filme = new Filme();
        //acao
        filme.setPrecoLocacao(-8.50);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       // }
       //validacao
       assertThat(message,is("O Valor da locação deve ser positivo"));
    }
    
    
}
