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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Marciel
 */
public class ClienteEntityTest {

    private static Validator validator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


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

    @Test
    public void cpfTemQueSerValido(){
        String cpf = "12345678910";
        
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        
        assertThat(cliente.getCpf(), is("12345678910"));
    }
    

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
     
     @Test
     public void naoDeveTerEspacoEmBrancoEmUmNome() {
         String nome = " Salom ão ";
         Cliente cliente = new Cliente();
         cliente.setNome(nome);
         
         assertThat(cliente.getNome(), is("Salom ão"));
     }
     
   
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
    
 
}
