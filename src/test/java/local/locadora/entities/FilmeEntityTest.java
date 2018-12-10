
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
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class FilmeEntityTest {

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
    
    //O campo nome deve ser um valor entre 4 e 50, inclusive Mensagem de validação: "Um nome deve possuir entre 4 e 50 caracteres";
    //****************************************************************************************************************************
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
    
   // O nome deverá ser um campo único Mensagem de validação: Não possui. Lançará uma Exception;;
    @Test
    public void naoDeveValidarNomeComCampoUnico() {
  
        Filme filme1 = new Filme();
        Filme filme2 = new Filme();

        try {
            filme1.setNome("Bolsomito");
            filme2.setNome("Bolsomito");
            Assert.fail();

        } catch (Exception e) {
            Object ExceptionFilme = null;
            Assert.assertSame(filme1.getNome(), filme2.getNome());
        }
    }
    //O nome não deverá aceitar espaços em branco no início e no fim Mensagem de validação: Não possui. A aplicação deve elimiar os espaços;
    @Test
    public void naoDeveAceitarEspacosBrancoInicioNomeFilme() {
        Filme filme = new Filme();
        filme.setNome("  Mito");
        assertThat(filme.getNome(), is("Mito"));
    }
    
    @Test
    public void naoDeveAceitarEspacoEmBrancoNoInicioEnoFimFilme() {
        Filme filme = new Filme();
        filme.setNome("  Mito  ");
        assertThat(filme.getNome(), is("Mito"));
    }
    
    @Test
    public void naoDeveAceitarEspacosBrancoFimNomeFilme() {
        Filme filme = new Filme();
        filme.setNome("Mito ");
        assertThat(filme.getNome(), is("Mito"));
    }
    //O nome deve possuir entre 2 e 100 caracteres, inclusive. Mensagem de validação: "Um filme deve possuir entre 2 e 100 caracteres";
    @Test
    public void naoDeveValidarUmNomeComMaisDeCemCaracteres() {
         Filme filme = new Filme();
        filme.setNome("qwertyuioxqwertyuioxqwertyuioxqwertyuioxqwertyuioxqqwertyuioxqwertyuioxqwertyuioxqwertyuioxqwertyuiCI");
        
          Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
   @Test
    public void naoDeveValidarUmNomeComUmCaractere() {
        Filme filme = new Filme();
        filme.setNome("x");
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
//O estoque do filme não pode ser negativo Mensagem de validação: "O Estoque deve ser positivo";
    @Test
    public void naoDeveValidarEstoqueFilmeNegativo() {
        Filme f = new Filme();
        f.setEstoque(-1);
        
          Set<ConstraintViolation<Filme>> violations = validator.validate(f);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O Estoque deve ser positivo"));
    }
    //O valor da locação não deverá ultrapassar dois dígitos e o número de casas após a vírgula deverá ser dois. Mensagem de validação: "O Preço deve ter no máximo dois dígitos";
    @Test
    public void naoDeveValidarValorComDoisDigitos() {
        Filme filme = new Filme();
        filme.setPrecoLocacao(17.177);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();

        assertThat(msg, is("O Preço deve ter no máximo dois dígitos"));
    } 
   
    //O valor da locação do filme deverá ser positivo Mensagem de validação: "O Valor da locação deve ser positivo";
    @Test  
    public void naoDeveValidarValorLocacaoNegativo() {
        Filme filme = new Filme();
        filme.setPrecoLocacao(-5.00);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O Valor da locação deve ser positivo"));
    }
    
}

