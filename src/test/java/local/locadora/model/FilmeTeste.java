/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.model;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.AssertTrue;
import local.locadora.entities.Filme;
import org.assertj.core.api.Fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author grazz
 */
public class FilmeTeste {

    private static Validator validator;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
    }

    /*O nome deverá ser um campo único Mensagem de validação: Não possui. Lançará uma Exception;;*/
    @Test
    public void nomeDeveSerCampoUnico() {

    }

    /*O nome não deverá aceitar espaços em branco no início e no fim Mensagem de validação: Não possui. A aplicação deve elimiar os espaços;*/
    @Test
    public void nomeNaoDeveAceitarEspacosBrancosNoInicioENoFim() {

        Filme f = new Filme(" Cavaleiro das Trevas ", 3, 4.0);

        //  assertThat(f.getNome(), is("Cavaleiro das Trevas"));
    }

    /*O nome deve possuir entre 2 e 100 caracteres, inclusive. Mensagem de validação: "Um filme deve possuir entre 2 e 100 caracteres";*/
    @Test
    @Ignore
    public void nomeDevePossuirAPartirDeDoisCaracteres() {

        try {
            Filme filme = new Filme();
            filme.setNome("It");

            Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
            Iterator it = violations.iterator();
            ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
            String mesg = x.getMessage();
        } catch (Exception msg) {

           // assertThat(mesg, is("Nome do filme deve ter entre 2 e 100 caracteres"));
            msg.getMessage();
        }

    }

    @Test
    @Ignore
    public void nomeDeveTerAteCemCaracteres() {

        Filme filme = new Filme();
        filme.setNome("blablablablablablablablablablablablablablablablabl");

        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String mesg = x.getMessage();

        // assertThat(mesg, is("Nome do filme deve ter entre 2 e 100 caracteres"));
    }

    @Test
    public void nomeNaoPodeTerMenosDeDoisCaracteres() {

        try {
            Filme filme = new Filme();
            filme.setNome("a");
            // assertThat(filme.getNome(), is("a"));
            fail("Nome não pode ter menos de dois caracteres");
        } catch (ValidationException e) {
            assertTrue("Cadastrado", true);
        }

    }

    @Before
    @Test
    public void nomeNaoPodeTerMaisDeCemCaracteres() {
//        Filme f = new Filme("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
//                5, 4.0);
//         f.setNome("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//        assertThat(f.getNome(), is("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));

        try {
            Filme filme = new Filme();
            filme.setNome("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            // assertThat(filme.getNome(), is("a"));
            fail("Nome não pode ter mais de 100 caracteres");
        } catch (ValidationException e) {
            assertTrue("Passou", true);
        }

    }

    /*O estoque do filme não pode ser negativo Mensagem de validação: "O Estoque deve ser positivo";*/
    @Test
    public void estoqueNaoPodeSerNegativo() {
        try {
            Filme f = new Filme();
            f.setEstoque(-10);
            fail("O estoque deve ser positivo");
        } catch (ValidationException e) {
            assertTrue("Aceito!", true);
        }

    }

    /*O valor da locação não deverá ultrapassar dois dígitos e o número de casas após a vírgula deverá ser dois.
    Mensagem de validação: "O Preço deve ter no máximo dois dígitos";*/
    @Test
    public void valorDaLocacaoNaoDeveUltrapassarDoisDigitos() {

    }

    /*O valor da locação do filme deverá ser positivo Mensagem de validação: "O Valor da locação deve ser positivo";*/
    @Test
    public void valorDaLocacaoDeveSerPositivo() {

//        Filme f = new Filme();
//        f.setPrecoLocacao(10.0);
//
//        Set<ConstrainViolation<Filme>> violation = validator.validate(f);
//        Iterator it = violation.iterator();
//
//        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
//        String msg = x.getMessage();
//
//        assertThat(msg, is("O Valor da locação deve ser positivo"));
    }

    @Test
    public void valorLocacaoNaoDeveSerNegativo() {

//        Filme f = new Filme();
//        f.setPrecoLocacao(-10.0);
//        
//        assertThat(f.getPrecoLocacao(), is(-10.0));
        try {
            Filme f = new Filme();
            f.setPrecoLocacao(-10.0);
            fail("Valor da locação não pode ser negativo");
        } catch (ValidationException e) {
            assertTrue("Aceito!", true);
        }
//        
    }
}
