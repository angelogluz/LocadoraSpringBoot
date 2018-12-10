/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Fail.fail;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FilmeTest {

    public FilmeTest() {}

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private static Validator validator;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    private boolean findMessage(String errorMessage, Filme filme) {
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl nxt = null;
        
        if (it.hasNext()) {
            nxt = (ConstraintViolationImpl) it.next();
            System.out.println(nxt.getMessage());
        }
        if (nxt != null) {
            return nxt.getMessage().equals(errorMessage);
        }
        return false;
    }

    private Filme exemploValido() {
        return new Filme("A lagoa azul", 5, 2.00);
    }

    @Test
    public void nomeDevePossuirNoMaximoDoisDigitos() {
        Filme filme = exemploValido();
        filme.setPrecoLocacao(12.150);

        String errorMessage = "O Preço deve ter no máximo dois dígitos";
        if (!findMessage(errorMessage, filme)) {
            fail("12.150");
        }

        filme.setPrecoLocacao(1.050);

        if (!findMessage(errorMessage, filme)) {
            fail("1.050");
        }

        filme.setPrecoLocacao(100.00);
        if (!findMessage(errorMessage, filme)) {
            fail("100.00");
        }
    }

    @Test
    public void estoqueDeveSerPositivo() {
        Filme filme = exemploValido();
        filme.setPrecoLocacao(-1d);

        String errorMessage = "O Valor da locação deve ser positivo";
        if (!findMessage(errorMessage, filme)) {
            fail("");
        }
    }


    @Test
    public void nomeNaoDeveAceitarEspacoNoInicioENoFinal() {
        Filme filme = exemploValido();

        if (filme.getNome().startsWith(" ")) {
            fail("");
        }
        if (filme.getNome().endsWith(" ")) {
            fail("");
        }
    }

    @Test
    public void nomeDevePossuirNoMinimo2Caracter() {
        Filme filme = exemploValido();
        filme.setNome("A");

        String errorMessage = "Um filme deve possuir entre 2 e 100 caracteres";
        if (!findMessage(errorMessage, filme)) {
            fail(errorMessage);
        }
    }

    @Test
    public void nomeDevePossuirAte100Caracteres() {
        String nome = "";
        for (int i = 0; i <= 100; i++) {
            nome += 'o';
        }
        Filme filme = exemploValido();
        filme.setNome(nome);

        String errorMessage = "Um filme deve possuir entre 2 e 100 caracteres";
        if (!findMessage(errorMessage, filme)) {
            fail(errorMessage);
        }
    }


    @Test
    public void estoqueDeveSerPositivo() {
        Filme filme = exemploValido();
        filme.setEstoque(-1);

        String errorMessage = "O Estoque deve ser positivo";
        if (!findMessage(errorMessage, filme)) {
            fail("");
        }
    }

}
