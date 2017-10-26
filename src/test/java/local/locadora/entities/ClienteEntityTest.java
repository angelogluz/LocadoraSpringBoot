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
import javax.persistence.GenerationType;

import static org.hamcrest.core.Is.is;
import org.hibernate.validator.constraints.br.CPF;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ClienteEntityTest {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void cpfNaoPodeSerValido(){
        String cpf = "123.456.789-10";
        
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        
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
    public void nomeDeveTerMaisQue4MenosQue65caracteres(){
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
     public void naoDeveTerEspacoEmBrancoEmUmNome() {
         String nome = "Math eus";
         Cliente cliente = new Cliente();
         cliente.setNome(nome);
         
         assertThat(cliente.getNome(), is("Math eus"));
     }
     
     
     @Test
     public void nomeNaoDeveTerIniciaisMaiusculas() {
         String nome = "matheus vieira";
         Cliente cliente = new Cliente();
         cliente.setNome(nome);
         
         assertThat(cliente.getNome(),is("matheus vieira"));
     }
     
     
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
}
