package local.locadora.entities;

import java.util.Arrays;
import java.util.List;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.LocadoraException;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Felipe
 */
@RunWith(Parameterized.class)
public class ValorLocacaoFilmeParametrizadoTest {

    /**
     *a anotacao @parameters especifica os parametros a serem utilizados 
     * nos testes
     */
    public ValorLocacaoFilmeParametrizadoTest() {
    }
    
    @Parameters (name = "Teste {index} = {2} - {1}")
    public static List<Object[]> parametrosDeExecucao() {
        Filme filme1 = new Filme("Piratas do Vale do Silício", 4, 4.0);
        Filme filme2 = new Filme("Jobs", 3, 4.0);
        Filme filme3 = new Filme("Duro de Matar 4", 11, 4.0);
        Filme filme4 = new Filme("Lagoa Azul", 2, 4.0);
        Filme filme5 = new Filme("A Trança dos Carecas", 2, 4.0);
        Filme filme6 = new Filme("Diomelo e seus dois maridos", 2, 4.0);
        return Arrays.asList(new Object[][]{
            {Arrays.asList(filme1, filme2), 8.0, "Teste locação sem desconto"},
            {Arrays.asList(filme1, filme2, filme3), 11.0, "Teste locação com desconto de 25% no terceiro filme"},
            {Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "Teste locação com desconto de 50% no quarto filme"},
            {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 13.0, "Teste locação com desconto de 100% no quinto filme"},
            {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 17.0, "Teste locação do sexto filme sem desconto"}
        });
    }
    /*
    *a anotacao @parameter instancia uma variavel para o parametro especificado
    */
    @Parameter(0)
    public List<Filme> filmes;
    @Parameter(1)
    public Double valorLocacao;
    @Parameter(2)
    public String descricao;
    
    /**
     *
     *Este teste serve para verificar o valor total da locaçao usando os parametros acima
     */
    @Test
    public void deveVerificarValorDeDevolucao() {
        //Cenário
        Locacao locacao = new Locacao();
        //Ação
        locacao.setFilmes(filmes);
        //Validação
        assertThat(locacao.getValor(), is(valorLocacao));
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
}
