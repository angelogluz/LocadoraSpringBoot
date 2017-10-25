package local.locadora.entities;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FilmeTest {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void valorNaoDeveraUltrapassarDoisDigitos() {
        Filme filme = new Filme();
        filme.setPrecoLocacao(123.321);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();

        assertThat(msg, is("O preço deve ter no máximo dois dígitos!"));
    }

    @Test
    public void naoDeveValidarUmFilmeComDoisCaracteres() {
        Filme filme1 = new Filme();
        filme1.setNome("DJ");
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme1);
        Iterator it = violations.iterator();
        while (it.hasNext()) {
            ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
            String message = x.getMessage();

            assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
        }
    }

    /**
     *
     * @throws Exception Exceção que ocorre se o nome passado na criação do
     * filme for vazio ("")
     */
    @Test
    public void testFilmeComNomeEmBranco() throws Exception {
        try {
            Filme filme = new Filme("", 10, 4d);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Não é possível cadastrar filme sem nome!");
        }
    }
    // Não deve aceitar um valor de estoque negativo!

    @Test
    public void naoAceitarEstoqueNegativo() {
        Filme filme = new Filme("Teste teste", 2, 2.50);

        assertTrue(filme.getEstoque() > 0);
    }

    // Valor da locação deverá ser positivo!
    @Test
    public void naoAceitarPrecoLocacaoNegativo() {
        Filme filme = new Filme("FilmeTeste", 2, -2.50);

        assertTrue(filme.getPrecoLocacao() > 0);
    }

    // Não deve aceitar somente espaço no nome!
    @Test
    public void naoAceitarSomenteEspacoNoNome() {
        Filme filme = new Filme(" ", 2, 2.50);

        assertFalse(filme.getNome().matches("[ ]*"));
    }
}
