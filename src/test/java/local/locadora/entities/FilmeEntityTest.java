
package local.locadora.entities;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author belinglima
 */
public class FilmeEntityTest {
    
     private static Validator validator;
        
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    /* O nome deverá ser um campo único Mensagem de validação: 
    Não possui. Lançará uma Exception; */
    @Test
    public void nomeDeveraSerUnico() throws Exception {
       Filme filme = new Filme();
        filme.setNome("B");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();

        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        try{
            assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
        }catch(Exception e){
            throw new Exception("Campo unico", e);
        }
    }
    
    /*O nome não deverá aceitar espaços em branco no início e 
    no fim Mensagem de validação: Não possui. A aplicação deve elimiar os 
    espaços*/
    @Test
    public void naoDeveAceitarEspacoEmBrancoNoInicioeNoFim() {
        Filme filme = new Filme();
        filme.setNome("  Matrix  ");
        assertThat(filme.getNome(), is("Matrix"));
    }    
    
    /* O nome deve possuir entre 2 e 100 caracteres, inclusive. 
    Mensagem de validação: "Um filme deve possuir entre 2 e 100 caracteres"; */
    @Test
    public void filmeDeveConterMenosDe2Caracteres(){
        String nome = "a";
        
        Filme filme = new Filme();
        filme.setNome(nome);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));	
    }
    
    /* O nome deve possuir entre 2 e 100 caracteres, inclusive. 
    Mensagem de validação: "Um filme deve possuir entre 2 e 100 caracteres"; */
    @Test
    public void naoDeveValidarUmNomeComMaisDeCemCaracteres() {
        Filme filme = new Filme("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", 2, 4.0);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    } 
    
    /* O estoque do filme não pode ser negativo 
    Mensagem de validação: "O Estoque deve ser positivo"; */
    @Test
    public void valorDoEstoqueDeveSerPositivo(){
        int estoque = -1;
        
        Filme filme = new Filme();
        filme.setEstoque(estoque);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Estoque deve ser positivo"));	
    }
    
    /* O valor da locação não deverá ultrapassar dois dígitos 
    e o número de casas após a vírgula deverá ser dois. Mensagem de validação: 
    "O Preço deve ter no máximo dois dígitos";  */
    @Test
    public void valorDaLocacaoNaoDeveraUltrapassarDoisDigitos(){
        double valorLocacao = 12.201;
        
        Filme filme = new Filme();
        filme.setPrecoLocacao(valorLocacao);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
       
    /* O valor da locação do filme deverá ser positivo 
    Mensagem de validação: "O Valor da locação deve ser positivo"; */
   @Test
    public void valorDaLocacaoDoFilmeDeveraSerPositivo(){
        double preco = -10.8;
        
        Filme filme = new Filme();
        filme.setPrecoLocacao(preco);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O Valor da locação deve ser positivo"));	
    }  
}
