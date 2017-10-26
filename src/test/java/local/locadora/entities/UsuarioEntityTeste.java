/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Claudio
 */
public class UsuarioEntityTeste {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /*O CPF deve ser persistido no banco sem separadores Mensagem de validação: Nenhuma;*/
    @Test
    public void cpfSemSeparadores() {
        Cliente cliente = new Cliente();
        cliente.setCpf("999.999.999-99");
        assertThat("99999999999", is(cliente.getCpf()));
    }

    /*O nome do cliente não deve aceitar caracteres especiais, nem números Mensagem de validação: 
    "O nome não deve possuir simbolos ou números";
    */
    @Test
    public void naoDeveValidarUmNomeComNumeroseSimbolos() {
        Cliente cliente = new Cliente();
        cliente.setNome("1234567890!@#$%¨&*()");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        assertThat(message, is("O nome não deve possuir simbolos ou números"));

    }
    
    /*
    O nome não deverá aceitar espaços em branco no início e no fim Mensagem de validação:
    Não possui. A aplicação deve elimiar os espaços;
    */
    @Test
    public void nomeComEspacoEmBranco() {
         Cliente cliente = new Cliente();
         cliente.setNome(" Renato ");
         assertThat("Renato", is(cliente.getNome()));
    }
    
    /*
    Independente de como digitado, o nome do cliente deverá ser armazenado com a primeira letra do nome/sobrenome maiúscula Mensagem de validação: 
    Não possui. A aplicação deverá fazer as correções;
    */
    @Test
    public void primeiraLetraDoNomeMaiuscula(){
        Cliente cliente = new Cliente();
         cliente.setNome("renato");
         assertThat("Renato", is(cliente.getNome()));
    }
    
    
}
