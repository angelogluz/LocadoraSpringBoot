package local.locadora.entities;

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
import org.junit.Assert;
import static org.junit.Assert.assertThat;

public class FilmeEntityTest {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void filmeTemQueTerEntreDoisECemCaracteres() {
        Filme filme = new Filme();
        filme.setNome("A");
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }

    public void naoValidarNomeComMaisDeCemCaracteres() {
        Filme filme = new Filme();
        filme.setNome("testedecemcaracteresaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiii");
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }

    @Test
    public void naoAceitarEspacoEmBrancoInicioeFim() {
        Filme filme = new Filme();
        filme.setNome("  NomeDoFilme  ");
        assertThat(filme.getNome(), is("NomeDoFilme"));
    }

    @Test
    public void estoqueTemQueSerPositivo() {
        Filme filme = new Filme();
        filme.setEstoque(-1);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();
        assertThat(msg, is("O Estoque deve ser positivo"));
    }

    @Test
    public void precoTemQueTerNoMaximoDoisDigitos() {
        Filme filme = new Filme();
        filme.setPrecoLocacao(123.321);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();
        assertThat(msg, is("O Preço deve ter no máximo dois dígitos"));
    }

    @Test
    public void valorDaLocacaoTemQueSerPositivo() {
        Filme filme = new Filme();
        filme.setPrecoLocacao(-10.2);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();
        assertThat(msg, is("O Valor da locação deve ser positivo"));
    }

    @Test
    public void naoValidarNomeComCampoUnico() {
        Filme cliente1 = new Filme();
        Filme cliente2 = new Filme();

        try {
            cliente1.setNome("Vingadores");
            cliente2.setNome("Vingadores");
            Assert.assertSame(cliente1.getNome(), cliente2.getNome());

        } catch (Exception e) {
            Object ExceptionFilme = null;
            Assert.assertNotSame(cliente1.getNome(), cliente2.getNome());
        }
    }
}
