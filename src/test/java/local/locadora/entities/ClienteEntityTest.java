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

    private boolean findMsg(String msgErro, Cliente cliente) {
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        String violationMessages = "";
        while (it.hasNext()) {
            ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
            violationMessages += x.getMessage();
        }
        return violationMessages.contains(msgErro);
    }

    /**
     * Note que <b>validator</b> aplica a validação do bean validation O
     * Iterator é utilizado para pegar as violações ocorridas
     */
    @Test
    public void naoDeveValidarUmNomeComDoisCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("An");

        String msgErro = "Um nome deve possuir entre 4 e 50 caracteres";
        if (!findMsg(msgErro, cliente)) {
            fail(msgErro);
        }
    }

    
    @Test
    public void cpfInvalido() {
        Cliente cliente = new Cliente("Juca", "038.856.720-14");

        String msgErro = "O CPF não é válido";
        if (!findMsg(msgErro, cliente)) {
            fail(msgErro);
        }
    }

    

    @Test
    public void espacosEmBranco() {
        Cliente cliente = new Cliente(" Rambo ");

        if (cliente.getNome().startsWith(" ")) {
            fail("");
        }
        if (cliente.getNome().endsWith(" ")) {
            fail("");
        }
    }

    @Test
    public void nomeComecaLetraMaiuscula() {
        Cliente saved = new Cliente("Rambo Soarez da Silva");
        String[] names = saved.getNome().split(" ");
        for (String nome : names) {
            if (!Character.isUpperCase(nome.charAt(0))) {
                fail("");
            }
        }
    }
    
    @Test
    public void naoDevePossuirSimbolos() {
        Cliente cliente = new Cliente("M4rc0$");

        String msgErro = "O nome não deve possuir simbolos ou números";
        if (!findMsg(msgErro, cliente)) {
            fail(msgErro);
        }
    }

    

    @Test
    public void nomeInvalido51Caracters() {
        String nome = "";
        for (int i = 0; i < 51; i++) {
            nome += 'A';
        }
        Cliente cliente = new Cliente(nome);

        String msgErro = "Um nome deve possuir entre 4 e 50 caracteres";
        if (!findMsg(msgErro, cliente)) {
            fail(msgErro);
        }
    }
}
