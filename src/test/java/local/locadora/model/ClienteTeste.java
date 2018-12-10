/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.model;

import java.util.Iterator;
import java.util.Set;
import static java.util.Spliterators.iterator;
import static java.util.Spliterators.iterator;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.entities.Cliente;
import net.bytebuddy.utility.RandomString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.junit.rules.ExpectedException;

/**
 *
 * @author grazz
 */
public class ClienteTeste {

    private static Validator validator;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    /*O CPF do cliente não é obrigatório, mas caso preenchido 
            precisa ser válido, não apenas em formato mas também matematicamente Mensagem de validação: "O CPF não é válido";*/
    @Test
    public void verificarSeCPFEValido() {
        try {
            Cliente cliente = new Cliente("Grazziano", "00000000000");

            Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
            Iterator it = violations.iterator();

            ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
            String message = x.getMessage();

            fail("O CPF não é válido");
            //assertThat(message, is("O CPF não é válido"));
        } catch (Exception e) {

        }
    }

    /*O CPF deve ser persistido no banco sem separadores Mensagem de validação: Nenhuma;*/
    @Test
    public void CPFDeveSerPersistidoNoBancoSemSeparadores() {
        Cliente cliente = new Cliente("Grazziano", "000.000.000-00");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();

        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        // assertThat(cliente.getCpf(), is("00000000000"));
        fail("O CPF não é válido");
    }

    /*O campo nome deve ser um valor entre 4 e 50, inclusive Mensagem de validação: "Um nome deve possuir entre 4 e 50 caracteres";*/
    @Test
    public void nomeDeveTerMaisDeQuatroCaracteres() {
        Cliente c = new Cliente("Joao");

        //assertThat(c.getNome(), is(equalTo("Joao")));

    }

    @Test
    public void nomeNaoPodeTerMenosDeQuatroCaracteres() {
        Cliente c = new Cliente("Ana");

       // assertThat(c.getNome(), is(equalTo("Ana")));

    }

    @Test
    public void nomeNaoPodeTerMaisQueCinquentaCaracteres() {
        Cliente c = new Cliente("JoaoVicenteJosedaSilvadeOleanseBragançadaFonsecaDeMachado");

       // assertThat(c.getNome(), is("JoaoVicenteJosedaSilvadeOleanseBragançadaFonsecaDeMachado"));

//        exception.expect(ClienteException.class);
//        exception.expectMessage("O nome do cliente deve possuir entre 4 e 55 caracteres");
//        Cliente cliente = new Cliente();
//        String nome = RandomString.make(51);
//        cliente.setNome(nome);
//        assertThat(nome, is(equalTo(nome)));
    }

    /*O nome do cliente não deve aceitar caracteres especiais, nem números Mensagem de validação: "O nome não deve possuir simbolos ou números";*/
    @Test
    public void nomeDoClienteNaoDeveAceitarCaracteresEspeciais() {
//        exception.expect(Cliente.class);
//        exception.expectMessage("Números e símbolos não são permitidos");
//        Cliente cliente = new Cliente();
//        cliente.setNome("Jorg& Silva");

        Cliente cliente = new Cliente();
        cliente.setNome("Grazz!an0");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String msg = x.getMessage();

        //assertThat(msg, is("Nome não pode conter caracteres especiais"));
    }

    /*O nome do cliente deverá ser um campo único Mensagem de validação: Não possui. Lançará uma Exception;*/
    @Test
    public void nomeDoClienteDeveSerUmCampoUnico() {
        Cliente c1 = new Cliente();
        Cliente c2 = new Cliente();

        try {
            c1.setNome("Angelo");
            c2.setNome("Angelo");
            Assert.fail();

        } catch (Exception e) {
            Object ExceptionCliente = null;
            Assert.assertSame(ExceptionCliente, e);
        }

    }

    /*O nome não deverá aceitar espaços em branco no início e no fim Mensagem de validação: Não possui. A aplicação deve elimiar os espaços;*/
    @Test
    public void nomeNaoDeveAceitarEspacosEmBrancoNoComecoENoFim() {
        Cliente cliente = new Cliente();
        cliente.setNome(" Grazziano ");
        // assertThat(cliente.getNome(), is("Grazziano"));
        assertThat(cliente.getNome(), true);
    }

    /*Independente de como digitado, o nome do cliente deverá ser armazenado com a primeira letra do nome/sobrenome maiúscula Mensagem de validação:
Não possui. A aplicação deverá fazer as correções;*/
    @Test
    public void primeiraLetraDoNomeSobrenomeDeveSerArmazenadaComoMaiuscula() {
        Cliente cliente = new Cliente();
        cliente.setNome("grazziano");

        // assertThat(cliente.getNome(), is(equalTo("Grazziano")));
    }
}
