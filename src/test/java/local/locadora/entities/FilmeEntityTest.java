
package local.locadora.entities;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import net.bytebuddy.utility.RandomString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author wally
 */
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
    O nome deverá ser um campo único Mensagem de validação:
    Não possui. Lançará uma Exception*/
    
    
    @Test
    public void nomeDoFilmeDeveSerCampoUnico() {
        
        Filme filme1 = new Filme();
        Filme filme2 = new Filme();

        try {
            filme1.setNome("Deadpool");
            filme2.setNome("Deadpool");
            Assert.fail();

        } catch (Exception e) {
            Object ExceptionFilme = null;
            Assert.assertSame(ExceptionFilme, e);
        }
    }
    /*O nome não deverá aceitar espaços em branco no início e 
    no fim Mensagem de validação: Não possui. A aplicação deve elimiar os 
    espaços*/
    
    @Test
    public void naoDeveAceitarEspacoEmBrancoNoInicioeNoFim() {
        Filme f = new Filme();
        f.setNome("  Deadpool  ");
        assertThat(f.getNome(), is("Deadpool"));
    }
    
    /*O nome deve possuir entre 2 e 100 caracteres, inclusive.
    Mensagem de validação: "Um filme deve possuir entre 2 e 100 caracteres"*/
    
     @Test
    public void naoDeveValidarUmNomeCom1Caracter() {
        Filme f = new Filme();
        f.setNome("A");

        Set<ConstraintViolation<Filme>> violations = validator.validate(f);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
    public void naoDeveValidarUmNomeCom101Caracteres() {
        Filme f = new Filme();
        String nome = RandomString.make(101);
        f.setNome(nome);

        Set<ConstraintViolation<Filme>> violations = validator.validate(f);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
    public void deveValidarUmNomeCom2Caracteres() {
        Filme f = new Filme();
        try{
        f.setNome("PW");
        
        }catch(Exception ex){
            fail("Um filme deve possuir entre 2 e 100 caracteres");
        }

        assertThat(f.getNome(), is(equalTo("PW")));
    }
    
    @Test
    public void deveValidarUmNomeCom100Caracteres() {
        Filme f = new Filme();
       String nome = RandomString.make(100);
       try{
        f.setNome(nome);
             
       }catch(Exception ex){
              fail("Um filme deve possuir entre 2 e 100 caracteres");
       }
       

        assertThat(f.getNome(), is(equalTo(nome)));
    }
    
    /*O estoque do filme não pode ser negativo Mensagem de validação:
    "O Estoque deve ser positivo"*/
    
      @Test
    public void estoqueNaoPodeSerNegativo() {
        Filme f = new Filme();
        f.setEstoque(-5);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(f);
        Iterator it = violations.iterator();
        
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();
        
        assertThat(msg, is("O Estoque deve ser positivo"));
    }
    
    /*O valor da locação não deverá ultrapassar dois dígitos e o número de casas após a vírgula 
    deverá ser dois. Mensagem de validação: "O Preço deve ter no máximo dois dígitos"*/
    
    @Test
    public void precoTemQueTerMaximo2Digitos() {
        Filme f = new Filme();
        f.setPrecoLocacao(222.777);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(f);
        
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String msg = x.getMessage();
        
        assertThat(msg, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    /*O valor da locação do filme deverá ser positivo Mensagem de validação:
    "O Valor da locação deve ser positivo";*/
    
    
    @Test
    public void valorDaLocacaoTemQueSerPositivo() {
        Filme f = new Filme();
        f.setPrecoLocacao(-5.1);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(f);
        
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        
        String msg = x.getMessage();
        
        assertThat(msg, is("O Valor da locação deve ser positivo"));
    }


}
