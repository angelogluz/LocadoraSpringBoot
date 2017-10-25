package local.locadora.entities;

import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.utils.DataUtils;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Tibiriça
 */
public class LocacaoEntityTest {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void naoDeveValidarUmaLocacaoSemCliente() {
        String message = "";
        Cliente c = new Cliente("Angelo");

        Filme f = new Filme("10.000 AC", 2, 4.0);

        Locacao l = new Locacao();

        l.addFilme(f);

        l.setDataLocacao(Date.from(Instant.now()));

        l.setDataRetorno(DataUtils.adicionarDias(Date.from(Instant.now()), 2));

        Set<ConstraintViolation<Locacao>> violations = validator.validate(l);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        message = x.getMessage();
        // }
        assertThat(message, is("Um cliente deve ser selecionado"));
    }

    @Test
    public void naoDeveValidarUmaLocacaoSemFilme() {
        String message = "";
        
        Cliente c = new Cliente("Angelo");

        Filme f = new Filme("10.000 AC", 2, 4.0);

        Locacao l = new Locacao();

        //l.setFilmes((List<Filme>) f);
        
        l.setCliente(c);

        l.setDataLocacao(Date.from(Instant.now()));

        l.setDataRetorno(DataUtils.adicionarDias(Date.from(Instant.now()), 2));

        Set<ConstraintViolation<Locacao>> violations = validator.validate(l);
        Iterator it = violations.iterator();
        while (it.hasNext()) {
            ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
            message = x.getMessage();
        }
        assertThat(message, is("Pelo menos um filme deve ser selecionado"));
    }
//    @Test
//    public void naoDeveValidarUmaLocacaoSemFilme() {
//        Cliente c = new Cliente("Angelo");
//        
//        Filme f = new Filme("10.000 AC", 2, 4.0);
//        
//        Locacao l = new Locacao();
//        
//        //l.setFilmes((List<Filme>) f);
//        
//        l.setCliente(c);
//        
//        l.setDataLocacao(Date.from(Instant.now()));
//        
//        l.setDataRetorno(DataUtils.adicionarDias(Date.from(Instant.now()), 2));
//        
//        Set<ConstraintViolation<Locacao>> violations = validator.validate(l);
//        Iterator it = violations.iterator();
//        //while(it.hasNext()){
//        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
//        String message = x.getMessage();
//        // }
//        assertThat(message, is("Pelo menos um filme deve ser selecionado"));
//    }
}
