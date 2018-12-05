
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
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class LocacaoEntityTest {

    private static Validator validator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
     // Uma locação não deverá ser realizada sem um cliente Mensagem de validação: "Um cliente deve ser selecionado";
       @Test
    public void NaoDeveEfetuarLocacaoSemCliente() {
        
    }
    
    //Uma locação deverá possuir pelo menos 1 filme Mensagem de validação: "Pelo menos um filme deve ser selecionado";
         @Test
    public void NaoDeveEfetuarLocacaoSemFilme() {
        
    }
    
    // Uma locação de filme sem estoque não poderá ser realizada Mensagem de validação: Sem mensagem. Uma Exception deverá ser lançada;
    //TESTAR 0 E TESTAR NEGATIVO PARA VALIDAR COM MAIOR COBERTURA
         @Test
    public void NaoDeveEfetuarLocacaoDoFilmeSemEstoque() {
        
    }
    
    //Uma locação não pode ser realizada sem data de locação Mensagem de validação: "A data de locação não deve ser nula";
         @Test
    public void NaoDeveEfetuarLocacaoSemDataLocacao() {
        
    }
    
    //Uma locação não pode ser realizada sem data de devolução Mensagem de validação: "A data de retorno não deve ser nula";
        @Test
    public void NaoDeveEfetuarLocacaoSemDataDevolucao() {
        
    }
    //A data de devolução do filme não pode ser uma data no passado Mensagem de validação: "A data deve retorno deve ser futura";v
       @Test
    public void NaoDeveEfetuarLocacaoComDataPassada() {
        
    }
    //O valor da locação deve possuir no máximo dois dígitos antes e depois da vírgula Mensagem de validação: "O Preço deve ter no máximo dois dígitos";
       @Test
    public void ValorLocacaoDeveTerDoisDigitosAntesDoisDepoisDaVirgula() {
        
    }
    //O valor da locação deve ser sempre positivo Mensagem de validação: "O valor da locação deve ser positivo";
       @Test
    public void ValorLocacaoDeveSempreSerPositivo() {
        
    }
    //Caso um filme tenha sua entrega prevista para domingo, deverá registrada para segunda-feira. Mensagem de validação: Nenhuma. Uma Exception deverá lançada;
       @Test
    public void DeveMudarEntregaDeDomingoParaSegunda() {
        
    }
    //Ao alugar um filme a data de entrega deve ter o número de dias incrementado de forma proporcional ao número de filmes alugados. Mensagem de validação: Nenhuma. Uma Exception deverá lançada;
   @Test
    public void DeveAcrescentarNumeroDiasProporcionalNumeroFilmesAlugados() {
        
    }

}

