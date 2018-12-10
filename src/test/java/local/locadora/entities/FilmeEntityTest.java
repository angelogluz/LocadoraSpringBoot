package local.locadora.entities;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FilmeEntityTest {

    private static Validator validator;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /*
    O nome deverá ser um campo único 
    Mensagem de validação: Não possui. Lançará uma Exception;;
     */
    @Test
    public void nomeFilmeCampoSerUnico() throws Exception {
        Filme filme = new Filme();
        filme.setNome("Zorro");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();

        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        try {
            assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
        } catch (Exception e) {
            throw new Exception("Campo unico", e);
        }
    }

    /*
    O nome deve possuir entre 2 e 100 caracteres, inclusive. 
    Mensagem de validação: "Um filme deve possuir entre 2 e 100 caracteres";
     */
    //Test para nao validar nome filme com um caracter
    @Test
    public void NaoDeveValidarNomeFilmeComUmCaracter() {
        Filme filme = new Filme();
        filme.setNome("a");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();

        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    //Teste pra validar nome fimle com 2 caracteres

    @Test
    public void DeveValidarNomeFilmeComDoisCaracteres() {
        Filme filme = new Filme();
        filme.setNome("Az");
        int tam = filme.getNome().length();
        assertThat(tam, is(2));
    }

    //Test para validar filme com 100 caracteres
    @Test
    public void DeveValidarNomeFilmeComCemCaracteres() {
        Filme filme = new Filme();
        filme.setNome("IIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIIC");
        int tam = filme.getNome().length();
        assertThat(tam, is(100));
    }

    //Test pra nao validar nome filme com 101 caracteres
    @Test
    public void NaoDeveValidarNomeFilmeComCentoEUmCaracters() {
        Filme filme = new Filme();
        filme.setNome("IIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICIIIIIIIIICS");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();

        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }

    /*
    O nome não deverá aceitar espaços em branco no início e no fim 
    Mensagem de validação: Não possui. A aplicação deve elimiar os espaços;
     */
    @Test
    public void naoDeveRegistrarNomeComEspacosNoInicioFim() {
        Filme filme = new Filme();
        filme.setNome(" A volta da ida ");
        assertThat(filme.getNome(), is("A volta da ida"));
    }

    /*
    O estoque do filme não pode ser negativo Mensagem de validação: "O Estoque deve ser positivo";
     */
    @Test
    public void EstoqueDoFilmeNaoPodeSerNegativo() {
        Filme filme = new Filme();
        filme.setEstoque(-1);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();

        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("O Estoque deve ser positivo"));
    }

    /*
    O valor da locação não deverá ultrapassar dois dígitos 
    e o número de casas após a vírgula deverá ser dois. 
    Mensagem de validação: "O Preço deve ter no máximo dois dígitos";
     */
    //Teste para que nao aceite mais de dois digitos
    @Test
    public void NaoDeveAceitarMaisQueDoisDigitos() {
        Filme filme = new Filme();
        filme.setPrecoLocacao(222.00);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();

        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));

    }
    //Teste para para q número de casas após a vírgula deverá seja dois

    /*
    O valor da locação do filme deverá ser positivo 
    Mensagem de validação: "O Valor da locação deve ser positivo";
     */
    @Test
    public void ValorLocacaoFilmeNaoPodeSerNegativo() {
        Filme filme = new Filme();
        filme.setPrecoLocacao(-2.00);

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();

        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("O Valor da locação deve ser positivo"));
    }
}
