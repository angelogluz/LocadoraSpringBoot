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
import static org.assertj.core.api.Fail.fail;


public class ClienteEntityTest {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private boolean findMessage (String errorMessage, Cliente cliente) {
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        String violationMessages = "";
        while (it.hasNext()) {
            ConstraintViolationImpl nxt = (ConstraintViolationImpl) it.next();
            violationMessages += nxt.getMessage();
        }
        return violationMessages.contains(errorMessage);
    }

    /**
     * Note que <b>validator</b> aplica a validação do bean validation O
     * Iterator é utilizado para pegar as violações ocorridas
     */
    @Test
    public void naoDeveValidarUmNomeComDoisCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("An");

        String errorMessage = "Um nome deve possuir entre 4 e 50 caracteres";
        if (!findMessage(errorMessage, cliente)) {
            fail(errorMessage);
        }
    }  

    @Test
    public void naoDeveAceitarEspacosEmBranco() {
        Cliente cliente = new Cliente(" Sniper ");

        if (cliente.getNome().startsWith(" ")) {
            fail("");
        }
        if (cliente.getNome().endsWith(" ")) {
            fail("");
        }
    }

    @Test
    public void cpfNaoPodeSerInvalido() {
        Cliente cliente = new Cliente("Javangelo", "351.555.934-94");

        String errorMessage = "O CPF não é válido";
        if (!findMessage(errorMessage, cliente)) {
            fail(errorMessage);
        }
    }


    @Test
    public void nomeDeveComecarComAPrimeiraLetraMaiscula() {
        Cliente saved = new Cliente("javangelo goncanvez da light");
        String[] names = saved.getNome().split(" ");
        for (String nome : names) {
            if (!Character.isUpperCase(nome.charAt(0))) {
                fail("");
            }
        }
    }
    
    @Test
    public void nomeDeveSerMaiorQueCinquentaCaracteres() {
        String nome = "";
        for (int i = 0; i <= 50; i++) {
            nome += 'A';
        }
        
        Cliente cliente = new Cliente(nome);

        String errorMessage = "Um nome deve possuir entre 4 e 50 caracteres";
        if (!findMessage(errorMessage, cliente)) {
            fail(errorMessage);
        }
    }

    @Test
    public void naoDevePossuirSimbolosOuNumeros() {
        Cliente cliente = new Cliente("J@v@ng&l0");

        String errorMessage = "O nome não deve possuir simbolos ou números";
        if (!findMessage(errorMessage, cliente)) {
            fail(errorMessage);
        }
    }
}
