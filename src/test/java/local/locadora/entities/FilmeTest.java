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

    public FilmeTest() {
    }

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private static Validator validator;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    private boolean findMsg(String msgErro, Filme filme) {
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //String violationMessages = "";
        ConstraintViolationImpl x = null;
        if (it.hasNext()) {
            x = (ConstraintViolationImpl) it.next();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(x.getMessage());
            //  violationMessages += x.getMessage();
        }
        if (x != null) {
            return x.getMessage().equals(msgErro);
        }
        return false;
    }

    private Filme exemploValido() {
        return new Filme("Senhor dos Aneis", 5, 2.00);
    }

    @Test
    public void valorLocacao2Digitos() {
        Filme filme = exemploValido();
        filme.setPrecoLocacao(11.111);

        String msgErro = "O Preço deve ter no máximo dois dígitos";
        if (!findMsg(msgErro, filme)) {
            fail("11.111");
        }

        filme.setPrecoLocacao(1.222);

        if (!findMsg(msgErro, filme)) {
            fail("1.222");
        }

        filme.setPrecoLocacao(100.00);
        if (!findMsg(msgErro, filme)) {
            fail("100.00");
        }
    }

    @Test
    public void valorLocacaoPositivo() {
        Filme filme = exemploValido();
        filme.setPrecoLocacao(-1d);

        String msgErro = "O Valor da locação deve ser positivo";
        if (!findMsg(msgErro, filme)) {
            fail("");
        }
    }

    @Test
    public void nomeInvalido201Caracter() {
        String nome = "";
        for (int i = 0; i < 201; i++) {
            nome += 'A';
        }
        Filme filme = exemploValido();
        filme.setNome(nome);

        String msgErro = "Um filme deve possuir entre 2 e 100 caracteres";
        if (!findMsg(msgErro, filme)) {
            fail(msgErro);
        }
    }

    @Test
    public void espacosInicioFinal() {
        Filme filme = exemploValido();

        if (filme.getNome().startsWith(" ")) {
            fail("");
        }
        if (filme.getNome().endsWith(" ")) {
            fail("");
        }
    }

    @Test
    public void nomeInvalido1Caracter() {
        Filme filme = exemploValido();
        filme.setNome("A");

        String msgErro = "Um filme deve possuir entre 2 e 100 caracteres";
        if (!findMsg(msgErro, filme)) {
            fail(msgErro);
        }
    }

    @Test
    public void estoquePositivo() {
        Filme filme = exemploValido();
        filme.setEstoque(-1);

        String msgErro = "O Estoque deve ser positivo";
        if (!findMsg(msgErro, filme)) {
            fail("");
        }
    }

}
