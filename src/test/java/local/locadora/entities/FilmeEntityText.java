package local.locadora.entities;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ftlor
 */
public class FilmeEntityText {
    
    private static Validator validator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void nomeNaoPodePossuirEspacoNoInicioENoFim() {
        
        //Cenário
        Filme filme = new Filme();
        //Configurado Direto na Classe Filme No Setter Nome = nome.trim();
        
        //Ação
        String nome = " Fabricio Mota ";
        filme.setNome(nome);
        
        //Validação
        assertEquals("Fabricio Mota", filme.getNome());
    }
    
    @Test
    public void naoValidarNomeComMenosDeDoisCaracteres() {
        
        //Cenário
        Filme filme = new Filme();
        //Configurado Direto na Classe Filme na Criação do Atributo
        
        //Ação
        filme.setNome("F");
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        
        //Validação
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
    public void naoValidarNomeComMaisDeCemCaracteres() {
        
        //Cenário
        Filme filme = new Filme();
        //Configurado Direto na Classe Filme na Criação do Atributo
        
        //Ação
        String nome = "F";
        for (int i = 0; i < 101; i++) {
            nome+= "a";
        }
        filme.setNome(nome);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        //Validação
        assertThat(message, is("Um filme deve possuir entre 2 e 100 caracteres"));
    }
    
    @Test
    public void naoValidarEstoqueNegativo() {
        
        //Cenário
        Filme filme = new Filme();
        //Configurado Direto na Classe Filme na Criação do Atributo
        
        //Ação
        filme.setNome("Rambo III");
        filme.setEstoque(-1);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        //Validação
        assertThat(message, is("O Estoque deve ser positivo"));
    }
    
    @Test
    public void precoComDoisDigitosAntesDoPonto() {
        
        //Cenário
        Filme filme = new Filme();
        //Configurado Direto na Classe Filme na Criação do Atributo
        
        //Ação
        filme.setNome("Rambo III");
        filme.setEstoque(1);
        filme.setPrecoLocacao(222.22);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void doisDigitosDepoisDoPonto() {
        
        //Cenário
        Filme filme = new Filme();
        //Configurado Direto na Classe Filme na Criação do Atributo
        
        //Ação
        filme.setNome("Rambo III");
        filme.setEstoque(1);
        filme.setPrecoLocacao(22.222);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        //Validação
        assertThat(message, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void valorDeveSerPositivo() {
        
        //Cenário
        Filme filme = new Filme();
        //Configurado Direto na Classe Filme na Criação do Atributo
        
        //Ação
        filme.setNome("Rambo III");
        filme.setEstoque(1);
        filme.setPrecoLocacao(-22.22);
        Set<ConstraintViolation<Filme>> violations = validator.validate(filme);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        //Validação
        assertThat(message, is("O Valor da locação deve ser positivo"));
    }
    
}
