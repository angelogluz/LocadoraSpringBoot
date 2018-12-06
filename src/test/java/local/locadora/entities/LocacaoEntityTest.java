
package local.locadora.entities;

import static com.fasterxml.jackson.databind.util.ISO8601Utils.format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import local.locadora.controller.LocacaoController;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.LocadoraException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class LocacaoEntityTest {

    private static Validator validator;
    
    @Mock
    RedirectAttributes flash;

    @Mock
    BindingResult bind;
    
    //Variavel global de controller
    LocacaoController locacaoController;
       
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
   
     // Uma locação não deverá ser realizada sem um cliente Mensagem de validação: "Um cliente deve ser selecionado";
       @Test
    public void NaoDeveEfetuarLocacaoSemCliente() throws FilmeSemEstoqueException {
        
         Filme filme = new Filme("Lagoa Azul",1,2.00);
         Locacao locacao = new Locacao();
         locacao.addFilme(filme);
         locacao.setValor(filme.getPrecoLocacao());
        // locacao.setDataLocacao(Date.from(Instant.EPOCH));
          //Processamento e validação
        try {
            locacaoController.alugarFilme(locacao, bind, flash);
            fail("Locação realizada com cliente null");
        } catch (LocadoraException | FilmeSemEstoqueException ex) {
            assertEquals("Impossivel locar sem um cliente", ex.getMessage());
            assertThat(ex.getMessage(), is(equalTo("Impossivel locar sem um cliente")));
        }
    }
    
    //Uma locação deverá possuir pelo menos 1 filme Mensagem de validação: "Pelo menos um filme deve ser selecionado";
         @Test
    public void NaoDeveEfetuarLocacaoSemFilme() {
        Cliente cliente = new Cliente("Bolsonaro", "85808165088");
        Locacao locacao = new Locacao();
        locacao.setCliente(cliente);
        locacao.setValor(2.00);
        
          try {
            locacaoController.alugarFilme(locacao, bind, flash);
            fail("Locação realizada com filmes null");
        } catch (LocadoraException | FilmeSemEstoqueException ex) {
            assertEquals("Nenhum filme foi selecionado", ex.getMessage());
            assertThat(ex.getMessage(), is(equalTo("Nenhum filme foi selecionado")));
        }  
    }
    
    // Uma locação de filme sem estoque não poderá ser realizada Mensagem de validação: Sem mensagem. Uma Exception deverá ser lançada;
    //TESTAR 0 E TESTAR NEGATIVO PARA VALIDAR COM MAIOR COBERTURA
    //Testar VALOR 1 DO FILME TAMBÉM
         @Test
    public void NaoDeveEfetuarLocacaoDoFilmeSemEstoque() {
        Filme filme = new Filme("A espera de um milagre",0,2.00);
              Cliente cliente = new Cliente("Bolsonaro", "85808165088");
              Locacao locacao = new Locacao();
              locacao.addFilme(filme);
              locacao.setValor(filme.getPrecoLocacao());
         
              try {
            locacaoController.alugarFilme(locacao, bind, flash);
            fail("Locação realizada com quantia de estoque do filme zero!");
        } catch (LocadoraException | FilmeSemEstoqueException ex) {
            assertEquals("Filme sem estoque", ex.getMessage());
            assertThat(ex.getMessage(), is(equalTo("Filme sem estoque")));
        }  
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

