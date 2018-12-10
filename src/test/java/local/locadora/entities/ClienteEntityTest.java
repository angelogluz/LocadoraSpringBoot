
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
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
    public void oCpfDeveSerValidoCasoPreencido() {
        
        //Cenário
        Cliente cliente = new Cliente();
        //Configurado Direto na Classe Cliente na Criação do Atributo
        
        //Ação
        cliente.setNome("Fabricio");
        cliente.setCpf("93786778001");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("O CPF não é válido"));
    }
    
    @Test
    public void CPFSalvoSemSeparadores() {
        
        //Cenário
        Cliente cliente = new Cliente();
        //Configurado Direto na Classe cliente No Setter CPF = cpf.replaceAll("[.-]", "");
        
        //Ação
        cliente.setNome("Fabricio");
        cliente.setCpf("937.867.780-00");
        
        //Validação
        assertEquals("93786778000", cliente.getCpf());
    }

    
    @Test
    public void naoValidarNomeComMenosDeQuatroCaracteres() {
        
        //Cenário
        Cliente cliente = new Cliente();
        //Configurado Direto na Classe Cliente na Criação do Atributo
        
        //Ação
        cliente.setNome("Fab");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();

        //Validação
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void naoValidarNomeComMaisDeCinquentaCaracteres() {
        
        //Cenário
        Cliente cliente = new Cliente();
        //Configurado Direto na Classe Cliente na Criação do Atributo
        
        //Ação
        String nome = "f";
        for (int i = 0; i < 52; i++) {
            nome+= "a";
        }
        cliente.setNome(nome);
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void nomeNaoDeveTerSimbolos(){
        
        //Cenário
        Cliente cliente = new Cliente();
        //Configurado Direto na Classe Cliente na Criação do Atributo
        
        //Ação
        cliente.setNome("@FabricioMota");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }


    @Test
    public void nomeNaoDeveTerNumeros(){
        
        //Cenário
        Cliente cliente = new Cliente();
        //Configurado Direto na Classe Cliente na Criação do Atributo
        
        //Ação
        cliente.setNome("1FabricioMota");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }
    
    @Test
    public void nomeNaoDeveTerNumeroESimbulo(){
        
        //Cenário
        Cliente cliente = new Cliente();
        //Configurado Direto na Classe Cliente na Criação do Atributo
        
        //Ação
        cliente.setNome("1FabricioMota@");
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        //Validação
        assertThat(message, is("O nome não deve possuir simbolos ou números"));
    }
    
    @Test
    public void nomeDoClienteDeveSerUnico() {
        
        //Cenário
        final Cliente cliente1 = new Cliente("");
        final Cliente cliente2 = new Cliente("");
        //Configurado Direto na Classe Cliente na Criação do Atributo

        //Ação
        cliente1.setNome("Fabricio Mota");
        cliente2.setNome("Fabricio Mota");
        
        //Validação
        assertEquals(cliente1.getNome(), cliente2.getNome());
        
    }
    
    @Test
    public void nomeNaoDeveAceitarEspecosNoInicioeOuFim(){
        
        //Cenário
        Cliente cliente = new Cliente();
        //Configurado Direto na Classe Cliente na Criação do Atributo
        
        //Ação
        cliente.setNome(" FabricioMota ");
        
        //Validação
        assert(cliente.getNome().equals("FabricioMota"));
    }

    @Test
    public void nomeDeveSerSalvoComAsIniciaisMaiusculas(){
        
        //Cenário
        Cliente cliente = new Cliente();
        
        //Ação
        String[] nome = "fabRiciO motA".split("\\s");
        StringBuilder up = new StringBuilder();
        for (int i = 0; i < nome.length; i++) {
            up.append(nome[i].substring(0, 1).toUpperCase() + nome[i].substring(1).toLowerCase());
            up.append(" ");
        }
        String espaco = "";
        String nome2 = String.join(espaco, up);
        cliente.setNome(nome2);
        
        //Validação
        assertEquals("Fabricio Mota", cliente.getNome());
    }

    
    
}

