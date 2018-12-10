
package local.locadora.entities;

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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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
    @Test
    public void naoDeveValidarUmNomeComTresCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("Anp");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    @Test
    public void nomeDoClienteNaoDeveAceitarCaracEsp(){
        Cliente cliente = new Cliente();
        cliente.setNome("Rog&r");
        
        Set<ConstraintViolation<Cliente>> violantions = validator.validate(cliente);
        Iterator it = violantions.iterator();
        
        ConstraintViolationImpl x =(ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
            }
   
    @Test
    public void nomeDeveraSerCampoUnico(){
        Cliente cliente = new Cliente();
        cliente.setNome("Roger Ayres");
        try{
            Cliente cliente1 = new Cliente();
            cliente1.setNome("Roger Ayres");
        } catch(Exception e){
            System.out.println(e);
            System.out.println("Nome deve ser um campo unico");
        }
  }
    @Test 
    public void nomeClienteNaoDeveAceitarEspacoBrancoInicioFim(){
        Cliente cliente = new Cliente();
        cliente.setNome(" Roger Ayres ");
        cliente.getNome().trim();
    }
   @Test
   public void nomeESobrenomeComLetraMaiuscula(){
       Cliente cliente = new Cliente();
       cliente.setNome("roger ayres");
       String arrumarNome = "";
     
        try{ //Bloco try-catch utilizado pois leitura de string gera a exceção abaixo
            for(int i = 0; i < cliente.getNome().length(); ++i){
                if( cliente.getNome().substring(i, i+1).equals(" "))
                    arrumarNome += cliente.getNome().substring(i+1, i+2).toUpperCase();
                else
                    arrumarNome += cliente.getNome().substring(i+1, i+2).toLowerCase();

        }
        }catch(IndexOutOfBoundsException indexOutOfBoundsException){
            //não faça nada. só pare tudo e saia do bloco de instrução try-catch
        }
        
        String toString = cliente.getNome().toString();
        toString= arrumarNome;
        System.err.println(arrumarNome);
   }
   
   @Test
   public void nomeDoFilmeDeveSerCampoUnico(){
       Filme filme = new Filme();
       filme.setNome("A bala que virou a esquina");
       
try{
    Filme filme1 = new Filme();
    filme1.setNome("A bala que virou a esquina");
}       catch(Exception e){
    System.out.println(e);
    System.out.println("Nome do filme deve ser campo unico");
}
   }
   @Test 
    public void nomeFilmeNaoDeveAceitarEspacoBrancoInicioFim(){
        Filme filme = new Filme();
        
        filme.setNome(" Bala que dobrou a esquina ");
        filme.getNome().trim();
    }
    @Test
    public void nomeFilmeDeveConterEntre2e100Carac(){
        Filme filme = new Filme();
        filme.setNome("a");
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um nome do filme deve possuir entre 2 e 100 caracteres"));
        }
    @Test
    public void estoqueFilmeNaoPodeSerNegativo(){
        Filme filme = new Filme();
        filme.setEstoque(-1);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O Estoque deve ser positivo"));
    }
    @Test
    public void valorDaLocacaoDeveraSerDeNoMaxDoisDigitos(){
        Filme filme = new Filme();
        filme.setPrecoLocacao(100.00);
        
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
    @Test
    public void valorDaLocacaoDeveraSerPositivo(){
        Filme filme = new Filme();
        filme.setPrecoLocacao(-1.00);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O Valor da locação deve ser positivo"));
    }
    @Test
    public void umaLocacaoNaoDeveSerFeitaSemCliente(){
        Locacao locacao = new Locacao();
       locacao.setDataLocacao(new Date(2018, 12,10));
       Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um cliente deve ser selecionado"));
    }
    @Test
    public void umaLocacaoDeveTerAoMenosUmFilme(){
        Locacao locacao = new Locacao();
        Cliente cliente = new Cliente();
        locacao.setCliente(cliente);
        locacao.setDataLocacao(new Date(2018,12,10));
        locacao.setDataRetorno(new Date(2018,12,13));
        
        locacao.setValor(20.00);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Pelo menos um filme deve ser selecionado"));
    }
        @Test
        public void locacaoDeFilmeSemEstoque(){
            Filme filme = new Filme();
            Locacao locacao = new Locacao();
           
            try{
            filme.setNome("O melhor filme do ano");
            filme.setPrecoLocacao(5.00);
            filme.setEstoque(0);
            
            locacao.setFilmes((List<Filme>) filme);
            locacao.setValor(5.00);
            locacao.setDataLocacao(new Date(2018,12,10));
            locacao.setDataRetorno(new Date(2018,12,13));
            } catch(Exception e){
                System.out.println(e);
            }
            
        }
        @Test 
        public void umaDataDaLocacaoNaoDeveSerNula(){
            Locacao locacao = new Locacao();
            Filme filme = new Filme();
            Cliente cliente = new Cliente();
            
            cliente.setNome("Roger");
            cliente.setCpf("000-000-000-00");
            
            filme.setNome("As panteras");
            filme.setEstoque(1);
            filme.setPrecoLocacao(2.00);
            
            locacao.setCliente(cliente);
            locacao.setFilmes((List<Filme>) filme);
            locacao.setDataRetorno(new Date(2018,12,13));
            locacao.setValor(2.00);
            
            Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("A data de locação não deve ser nula"));
    }
         @Test 
        public void umaDataDeRetornoNaoDeveSerNula(){
            Locacao locacao = new Locacao();
            Filme filme = new Filme();
            Cliente cliente = new Cliente();
            
            cliente.setNome("Roger");
            cliente.setCpf("000-000-000-00");
            
            filme.setNome("As panteras");
            filme.setEstoque(1);
            filme.setPrecoLocacao(2.00);
            
            locacao.setCliente(cliente);
            locacao.setFilmes((List<Filme>) filme);
            locacao.setDataLocacao(new Date(2018,12,13));
            locacao.setValor(2.00);
            
            Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("A data de retorno não deve ser nula"));
    }
        
         @Test 
        public void aDataDeRetornoDeveSerFutura(){
            Locacao locacao = new Locacao();
            Filme filme = new Filme();
            Cliente cliente = new Cliente();
            
            cliente.setNome("Roger");
            cliente.setCpf("000-000-000-00");
            
            filme.setNome("As panteras");
            filme.setEstoque(1);
            filme.setPrecoLocacao(2.00);
            
            locacao.setCliente(cliente);
            locacao.setFilmes((List<Filme>) filme);
            locacao.setDataLocacao(new Date(2018,12,20));
            locacao.setDataRetorno(new Date(2018,12,13));
            locacao.setValor(2.00);
            
            Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("A data de retorno deve ser futura"));
    }
        @Test
        public void oPrecoDeveTerNoMaxDoisDigitos(){
             Locacao locacao = new Locacao();
            Filme filme = new Filme();
            Cliente cliente = new Cliente();
            
            cliente.setNome("Roger");
            cliente.setCpf("000-000-000-00");
            
            filme.setNome("As panteras");
            filme.setEstoque(1);
            filme.setPrecoLocacao(200.00);
            
            locacao.setCliente(cliente);
            locacao.setFilmes((List<Filme>) filme);
            locacao.setDataLocacao(new Date(2018,12,10));
            locacao.setDataRetorno(new Date(2018,12,13));
            locacao.setValor(200.00);
            
            Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
        }
        @Test
        public void oValorDaLocacaoDeveSerPositivo(){
            Locacao locacao = new Locacao();
            Filme filme = new Filme();
            Cliente cliente = new Cliente();
            
            cliente.setNome("Roger");
            cliente.setCpf("000-000-000-00");
            
            filme.setNome("As panteras");
            filme.setEstoque(1);
            filme.setPrecoLocacao(-2.00);
            
            locacao.setCliente(cliente);
            locacao.setFilmes((List<Filme>) filme);
            locacao.setDataLocacao(new Date(2018,12,7));
            locacao.setDataRetorno(new Date(2018,12,9));
            locacao.setValor(-2.00);
            
            Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("O Valor da locação deve ser positivo"));
        }
        @Test
        public void filmeParaEntregarDomFicaParaSeg(){
             Locacao locacao = new Locacao();
            Filme filme = new Filme();
            Cliente cliente = new Cliente();
             locacao.setCliente(cliente);
            locacao.setFilmes((List<Filme>) filme);
            try{
            locacao.setDataLocacao(new Date(2018,12,7));
            locacao.setDataRetorno(new Date(2018,12,9));
            
            }catch(Exception e){
                System.out.println(e);
                System.out.println("Filme deve ser entregado na segunda");
            }
            
            
        }
        }
                



