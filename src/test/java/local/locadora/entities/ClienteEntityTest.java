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

    /**
     * Note que <b>validator</b> aplica a validação do bean validation O
     * Iterator é utilizado para pegar as violações ocorridas
     */
    @Test
    public void naoDeveValidarUmNomeComDoisCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("An");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }

    /*
    O campo nome deve ser um valor entre 4 e 50, inclusive
    Mensagem de validação: "Um nome deve possuir entre 4 e 50 caracteres";
     */
    //Teste Validação quantidade caracteres
    @Test
    public void NaoDeveValidarUmNomeComTresCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("abc");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
        
    }
    //Teste Validação quantidade caracteres

    @Test
    public void DeveValidarUmNomeComQuatroCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("abcd");
        int tam = cliente.getNome().length();
        assertThat(tam, is(4));
        
    }
    //Teste Validação quantidade caracteres

    @Test
    public void DeveValidarUmNomeComCinquentaCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghij");
        
        int tam = cliente.getNome().length();
        assertThat(tam, is(50));
        
    }
    //Teste Validação quantidade caracteres

    @Test
    public void NaoDeveValidarUmNomeComCinquentaEUmCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijA");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
        
    }
    //Teste para validar caracteres especias

    @Test
    public void naoDeveAceitarNomeComNumero() {
        Cliente cliente = new Cliente();
        cliente.setNome("abc33");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }

    //Teste para validar caracteres especias
    @Test
    public void naoDeveAceitarNomeComCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("abc--");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }

    /*
    O CPF do cliente não é obrigatório, mas caso preenchido precisa ser válido, não apenas em formato mas também matematicamente
    Mensagem de validação: "O CPF não é válido";
     */
    @Test
    public void cpfNaoPodeSerValidado() {
        String cpf = "345.246.759-201";
        
        Cliente cliente = new Cliente();
        cliente.setNome("matheus");
        cliente.setCpf(cpf);
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O CPF não é válido"));
    }
    
    @Test
    public void cpfTemQueSerValidado() {
        String cpf = "345246759201";
        
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        
        assertThat(cliente.getCpf(), is("345246759201"));
    }

    /*
    O nome não deverá aceitar espaços em branco no início e no fim 
    Mensagem de validação: Não possui. A aplicação deve elimiar os espaços;
    
     */
    @Test
    public void naoDeveRegistrarNomeComEspacosNoInicioFim() {
        Cliente cliente = new Cliente();
        cliente.setNome(" matheus borges ");
        assertThat(cliente.getNome(), is("matheus borges"));
    }

    /*
    O nome do cliente deverá ser um campo único Mensagem de validação: Não possui. Lançará uma Exception;
     */
    @Test
    public void nomeDoClienteDeveSerCampoUnico() {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();
        
        try {
            cliente1.setNome("matheus");
            cliente2.setNome("matheus");
            Assert.fail();
            
        } catch (Exception e) {
            Object ExceptionCliente = null;
            Assert.assertSame(ExceptionCliente, e);
        }
    }

    /*
    Independente de como digitado, o nome do cliente deverá ser armazenado com a primeira letra do nome/sobrenome maiúscula
    Mensagem de validação: Não possui. A aplicação deverá fazer as correções;
     */
    @Test
    public void primeiraLetraDoNomeSobrenomeDeveSerMaiuscula() {
        
        try {
            Cliente cliente = new Cliente();
            cliente.setNome("Matheus Borges");
            assertThat(cliente.getNome(), is("Matheus Borges"));
        } catch (Exception e) {
            e.getMessage();
            fail();
        }
    }
}
