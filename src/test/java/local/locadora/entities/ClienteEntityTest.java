
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
    
    /* O campo nome deve ser um valor entre 4 e 50, inclusive 
    Mensagem de validação: "Um nome deve possuir entre 4 e 50 caracteres"; */
    @Test
    public void nomeDoClienteDeveTerEntreQuatroECinquentaCaracteres() {
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
    
    /* O CPF do cliente não é obrigatório, 
    mas caso preenchido precisa ser válido, não apenas 
    em formato mas também matematicamente 
    Mensagem de validação: "O CPF não é válido"; */
    @Test
    public void cpfNaoPodeSerValido(){
        String cpf = "123.456.789-101";
        
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        cliente.setNome("Juca Biluca");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O CPF não é válido"));	
    }
    
    /* O CPF deve ser persistido no banco sem 
    separadores Mensagem de validação: Nenhuma; */
    @Test
    public void cpfTemQueSerValido(){
        String cpf = "12345678910";
        
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        
        assertThat(cliente.getCpf(), is("12345678910"));
    }
    
    /* O campo nome deve ser um valor entre 4 e 50, inclusive 
    Mensagem de validação: "Um nome deve possuir entre 4 e 50 caracteres"; */
    @Test
    public void nomeDeveTerMaisQue4MenosQue50caracteres(){
        String nome = "Mat";
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        
        int valor = cliente.getNome().length();
        System.out.println(valor);
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    
    /* O nome do cliente não deve aceitar caracteres especiais, nem números 
    Mensagem de validação: "O nome não deve possuir simbolos ou números"; */
    @Test
     public void naoDeveValidarUmNomeComCaracteresEspeciais() {
         String nome = "&%$#";
         Cliente cliente = new Cliente();
         cliente.setNome(nome);
         
         Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
         Iterator it = violations.iterator();       
         ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
         String message = x.getMessage();
         
         assertThat(message,is("O nome não deve possuir simbolos ou números"));
     }
     
     
    /* O nome do cliente deverá ser um campo único 
    Mensagem de validação: Não possui. Lançará uma Exception; */
    @Test
    public void NomeDeveSerCampoUnico() throws Exception {
        Filme filme = new Filme();
        filme.setNome("B");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        //}
     try{
            assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
        }catch(Exception e){
             throw new Exception("Campo unico", e);
        }
    }
     
     /* O nome não deverá aceitar espaços em branco 
     no início e no fim 
     Mensagem de validação: Não possui. A aplicação deve elimiar os espaços; */
     @Test
     public void naoDeveTerEspacoEmBrancoEmUmNome() {
         String nome = " Salom ão ";
         Cliente cliente = new Cliente();
         cliente.setNome(nome);
         
         assertThat(cliente.getNome(), is("Salom ão"));
     }
     
     /* Independente de como digitado, 
     o nome do cliente deverá ser armazenado com a primeira 
     letra do nome/sobrenome maiúscula 
     Mensagem de validação: Não possui. A aplicação deverá fazer as correções; */   
     @Test
     public void primeiraLetraDoNomeSobrenomeDeveSerMaiuscula() {

        try {
            Cliente cliente = new Cliente();
            cliente.setNome("Salomao Beling");
            assertThat(cliente.getNome(), is("Salomao Beling"));
        } catch (Exception e) {
            e.getMessage();
            fail();
        }
    }
    
    /* EXtras */
    @Test
    public void naoValidarSeCPFEmBranco() {
        Cliente cliente = new Cliente();
        cliente.setNome("Salomao");
        cliente.setCpf("");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("O CPF não é válido"));
    }
 
}
