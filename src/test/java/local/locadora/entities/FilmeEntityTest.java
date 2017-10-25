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
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Tibiriça
 */
public class FilmeEntityTest {
    
    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void naoDeveValidarUmNomeComUmCaracter() {
        Filme f = new Filme("A", 2, 4.0);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(f);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
     @Test
    public void naoDeveValidarUmNomeComMaisDeCemCaracteres() {
        Filme f = new Filme("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", 2, 4.0);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(f);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    } 
    
    @Test
    public void naoDeveValidarUmEstoqueNegativo() {
        Filme f = new Filme("Duro de Matar", -2, 4.0);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(f);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("O Estoque deve ser positivo"));
    }
    @Test
    public void naoDeveValidarUmPrecoComCentenas() {
        Filme f = new Filme("Duro de Matar", 2, 154.0);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(f);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
    @Test
    public void naoDeveValidarUmPrecoComMaisDeDoisCaracteresDepoisDaVirgula() {
        Filme f = new Filme("Duro de Matar", 2, 4.006);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(f);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
    @Test
    public void naoDeveValidarUmPrecoNegativo() {
        Filme f = new Filme("Duro de Matar", 2, -4.0);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(f);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("O Valor da locação deve ser positivo"));
    }
    
     @Test
     public void testaSeExcluiEspacosNoInicioDoNome() {
        Filme f = new Filme(" Piratas do Caribe ", 2, 4.0);
        assertTrue(f.getNome().equals("Piratas do Caribe"));
    }
    
}
