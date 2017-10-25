package local.locadora.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import local.locadora.controller.LocacaoController;
import local.locadora.controller.LocacaoController;
import local.locadora.controller.LocacaoController;
import local.locadora.dao.ClienteDAO;
import local.locadora.entities.Cliente;
import local.locadora.entities.Filme;
import local.locadora.entities.Locacao;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.LocadoraException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import static local.locadora.utils.DataUtils.adicionarDias;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;

@RunWith(Parameterized.class)
public class LocacaoControlerTest {

    private final LocacaoController controller;

    public LocacaoControlerTest() {
        controller = new LocacaoController();
    }

    @Parameterized.Parameters(name = "{2}")
    public static List<Object[]> parametrosDeExecucao() {
        Filme filme1 = new Filme("Filme 01", 4, 2.0);
        Filme filme2 = new Filme("Filme 02", 4, 2.0);
        Filme filme3 = new Filme("Filme 03", 4, 2.0);
        Filme filme4 = new Filme("Filme 04", 4, 2.0);

        Locacao locacao1 = new Locacao();
        locacao1.addFilme(filme1);
        locacao1.setCliente(new Cliente("Luiz"));

        Locacao locacao2 = new Locacao();
        locacao2.addFilme(filme1);
        locacao2.addFilme(filme2);
        locacao2.setCliente(new Cliente("Luiz"));

        Locacao locacao3 = new Locacao();
        locacao3.addFilme(filme1);
        locacao3.addFilme(filme2);
        locacao3.addFilme(filme3);
        locacao3.setCliente(new Cliente("Luiz"));

        Locacao locacao4 = new Locacao();
        locacao4.addFilme(filme1);
        locacao4.addFilme(filme2);
        locacao4.addFilme(filme3);
        locacao4.addFilme(filme4);
        locacao4.setCliente(new Cliente("Luiz"));

        return Arrays.asList(new Object[][]{
            {locacao1, adicionarDias(new Date(), 1),
                "Entrega em um dia"},
            {locacao2, adicionarDias(new Date(), 2),
                "Entrega em dois dias"},
            {locacao3, adicionarDias(new Date(), 3),
                "Entrega em um tres dias"},
            {locacao4, adicionarDias(new Date(), 4),
                "Entrega em quatro dias"}});
    }
    @Parameter(0)
    public Locacao locacoes;
    @Parameter(1)
    public Date dataentrega;
    @Parameter(2)
    public String descricao;

    @Test
    public void deveVerificarDataDeDevolucao() throws FilmeSemEstoqueException,
            LocadoraException {

        controller.setarDataEntrega(new Date(), locacoes);
        Assert.assertThat()(locacoes.getDataRetorno().getDate(), is(dataentrega.getDate()));
    }
}
