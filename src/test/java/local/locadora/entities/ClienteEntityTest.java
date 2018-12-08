
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
import net.bytebuddy.utility.RandomString;
import static org.hamcrest.CoreMatchers.equalTo;

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
     * Note que <b>validator</b> aplica a validação do bean validation
     * O Iterator é utilizado para pegar as violações ocorridas
     */
    
    /*O campo nome deve ser um valor entre 4 e 50, inclusive Mensagem de validação:
    "Um nome deve possuir entre 4 e 50 caracteres"*/
    @Test
    public void naoDeveValidarUmNomeCom3Caracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("Rex");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void naoDeveValidarUmNomeCom51Caracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeef");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void deveValidarUmNomeCom4Caracteres() {
        Cliente cliente = new Cliente();
        try{
        cliente.setNome("Rexo");
        
        }catch(Exception ex){
            fail("Um nome deve possuir entre 4 e 50 caracteres");
        }

        assertThat(cliente.getNome(), is(equalTo("Rexo")));
    }
    
    @Test
    public void deveValidarUmNomeCom50Caracteres() {
        Cliente cliente = new Cliente();
       String nome = RandomString.make(50);
       try{
        cliente.setNome(nome);
             
       }catch(Exception ex){
           fail("Um nome deve possuir entre 4 e 50 caracteres");
       }
       

        assertThat(cliente.getNome(), is(equalTo(nome)));
    }
    
    /*O nome do cliente não deve aceitar caracteres especiais, nem números 
    Mensagem de validação: "O nome não deve possuir simbolos ou números"*/
    @Test
    public void naoDeveValidarUmNomeComNumeroseSimbolos() {
        Cliente cliente = new Cliente();
        cliente.setNome("Wally -1997-");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }
    
    /*O CPF do cliente não é obrigatório, mas caso preenchido 
    precisa ser válido, não apenas em formato mas também matematicamente 
    Mensagem de validação: "O CPF não é válido"*/
    @Test
    public void deveSerNuloCPF() {
        Cliente cliente = new Cliente();
        cliente.setNome("Wally");
        cliente.setCpf("");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O CPF não é válido"));
    }
     @Test
    public void deveSerValidoCPF() {
        Cliente cliente = new Cliente();
        cliente.setNome("Wally");
        cliente.setCpf("998877665");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O CPF não é válido"));
    }
    /*O nome não deverá aceitar espaços em branco 
    no início e no fim Mensagem de validação: Não possui.
    A aplicação deve elimiar os espaços;*/
    @Test
    public void naoDeveAceitarEspacoEmBrancoNoInicioeNoFim() {
        Cliente cliente = new Cliente();
        cliente.setNome("  Wally  ");
        assertThat(cliente.getNome(), is("Wally"));
    }
    /*Independente de como digitado, o nome do cliente deverá ser 
    armazenado com a primeira letra do nome/sobrenome maiúscula 
    Mensagem de validação: Não possui. A aplicação deverá fazer as correções*/
    
     @Test
    public void primeiraLetraDeveSerMaiuscula() {
        Cliente cliente = new Cliente();

        try {
            cliente.setNome("jaco wally");
            
        } catch (Exception e) {
            fail();
        }
         assertThat(cliente.getNome(), is("Jaco Wally"));
    }
    
    /*O nome do cliente deverá ser um campo único Mensagem de validação: 
    Não possui. Lançará uma Exception;*/
    @Test
    public void nomeDoClienteDeveSerCampoUnico() {
        
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();

        try {
            cliente1.setNome("Jaco");
            cliente2.setNome("Jaco");
            Assert.fail();

        } catch (Exception e) {
            Object ExceptionCliente = null;
            Assert.assertSame(ExceptionCliente, e);
            
        }
    }
    
    @Test
    public void oCPFDeveSerPersistidoNoBancoSemSeparadores() {
        Cliente cliente = new Cliente();

        try {
            cliente.setNome("899.999.899-99");

        } catch (Exception e) {
            fail();
        }
         assertThat(cliente.getNome(), is("89999989999"));
    }
}

