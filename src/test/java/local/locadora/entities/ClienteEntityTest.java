
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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import org.junit.Assert;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class ClienteEntityTest {

    private static Validator validator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    //O campo nome deve ser um valor entre 4 e 50, inclusive Mensagem de validação: "Um nome deve possuir entre 4 e 50 caracteres";
    //****************************************************************************************************************************
@Test
    public void naoDeveValidarUmNomeComDoisCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("An");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    //Testando 51 caracteres
@Test
    public void naoDeveValidarUmNomeComCinquentaUmCaracteres() {
        Cliente cliente = new Cliente();
        //51 caracteres
        cliente.setNome("usususususususususususususususususususususususususu");
        
          Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
@Test
    public void deveValidarUmNomeComQuatroCaracteres() {
        Cliente cliente = new Cliente();
        try{
        cliente.setNome("Mito");
        }catch(Exception ex){
            fail("Um nome deve possuir entre 4 e 50 caracteres");
        }

        assertThat(cliente.getNome(), is(equalTo("Mito")));
    }
      
//O CPF do cliente não é obrigatório, mas caso preenchido precisa ser válido, não apenas em formato mas também matematicamente Mensagem de validação: "O CPF não é válido";
@Test
    public void deveValidarCPF() {
        Cliente cliente = new Cliente();
        cliente.setNome("Bolsonaro");
        cliente.setCpf("171717177");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O CPF não é válido"));
    }
    
    //Testando com o CPF vazio!
@Test
    public void nãoDeveValidarCPFNulo() {
        Cliente cliente = new Cliente();
        cliente.setNome("Bolsonaro");
        cliente.setCpf("");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
       
        assertThat(message, is("O CPF não é válido"));
    }
    
//O CPF deve ser persistido no banco sem separadores Mensagem de validação: Nenhuma;
@Test
    public void PersistirCPFBancoSemSeparadores() {
        Cliente cliente = new Cliente();

        try {
            cliente.setNome("858.081.640-55");
        } catch (Exception e) { 
            fail();
        }
        assertThat(cliente.getNome(), is("85808164055"));
    }

//O nome do cliente não deve aceitar caracteres especiais, nem números Mensagem de validação: "O nome não deve possuir simbolos ou números";
@Test
    public void deveValidarSeNomeTemNumerosOuSimbolos() {
        Cliente cliente = new Cliente();
        cliente.setNome("Bolsominion@17");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }
    
//O nome do cliente deverá ser um campo único Mensagem de validação: Não possui. Lançará uma Exception;
@Test
    public void nomeClienteDeveSerUnico() {
        Cliente c1 = new Cliente();
        Cliente c2 = new Cliente();

        try {
            c1.setNome("Bolsominion");
            c2.setNome("Bolsominion");
             Assert.assertSame(c1.getNome(), c2.getNome());
             Assert.fail("Sistema não cumpre com os requisitos");

        } catch (Exception e) {
            Object ExceptionCliente = null;
           //Assert.assertSame(cliente1.getNome(), cliente2.getNome());
            Assert.assertNotSame(c1.getNome(), c2.getNome());
        }
    }
//O nome não deverá aceitar espaços em branco no início e no fim Mensagem de validação: Não possui. A aplicação deve elimiar os espaços;
@Test
    public void naoDeveAceitarEspacoEmBrancoNoInicioEnoFim() {
        Cliente cliente = new Cliente();
        cliente.setNome("  Bolsonaro  ");
        assertThat(cliente.getNome(), is("Bolsonaro"));
    }
    
@Test
    public void naoDeveAceitarEspacoEmBrancoNoInicio() {
        Cliente cliente = new Cliente();
        cliente.setNome("  Bolsonaro");
        assertThat(cliente.getNome(), is("Bolsonaro"));
    }
    
@Test
    public void naoDeveAceitarEspacoEmBrancoNoFimo() {
        Cliente cliente = new Cliente();
        cliente.setNome("Bolsonaro ");
        assertThat(cliente.getNome(), is("Bolsonaro"));
    }

//Independente de como digitado, o nome do cliente deverá ser armazenado com a primeira letra do nome/sobrenome maiúscula Mensagem de validação: Não possui. A aplicação deverá fazer as correções;
@Test
    public void deveEditarPrimeiraLetraNomeESobrenomeMaiuscula() {

        try {
            Cliente cliente = new Cliente();
            cliente.setNome("bolsonaro mito");
            assertThat(cliente.getNome(), is("Bolsonaro Mito"));
            
        } catch (Exception e) {
            fail();
        }
    }
}

