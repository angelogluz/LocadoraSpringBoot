package local.locadora.entities;

import java.util.Arrays;
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
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@RunWith(Parameterized.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteEntityTest {

    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void deveValidarCalculoDeCPF() {
        //Cenário
        Cliente cliente = new Cliente();
        //Ação
        cliente.setCpf("000.000.000-00");
        fail();
        //Validação
        assertEquals("O CPF não é válido", cliente.getCpf());
    }

    @Test
    public void devePersistirCPFNoBancoSemSeparadores() {
        //Cenário
        Cliente cliente = new Cliente();
        //Ação
        cliente.setCpf("005.506.790-57");
        //Validação
        assertEquals("00550679057", cliente.getCpf());
    }


    @Parameterized.Parameters(name = "Teste - {index} = {2} - {1}")
    public static List<Object[]> parametrosDeExecucao() {
        String nome1 = "Jon";
        String nome2 = "Jones Bunilha Radtke Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String nome3 = "@o&%";
        String nome4 = "jones";
        String nome5 = " Jones ";

        String retorno1 = "Um nome deve possuir entre 4 e 50 caracteres";
        String retorno2 = "Um nome deve possuir entre 4 e 50 caracteres";
        String retorno3 = "O nome não deve possuir simbolos ou números";
        String retorno4 = "Jones";
        String retorno5 = "Jones";

        return Arrays.asList(new Object[][]{
            {Arrays.asList(nome1), (retorno1), "Nome com menos de 4 caracteres"},
            {Arrays.asList(nome2), (retorno2), "Nome com mais de 40 caracteres"},
            {Arrays.asList(nome3), (retorno3), "Nome com caracteres especiais"},
            {Arrays.asList(nome4), (retorno4), "Nome com primeira letra em minusculo"},
            {Arrays.asList(nome5), (retorno5), "Nome com espaço no início e fim"},});
    }

    @Parameterized.Parameter(0)
    public List<String> nomes;

    @Parameterized.Parameter(1)
    public String retorno;

    @Parameterized.Parameter(2)
    public String descricao;

    @Test
    public void deveVerificarNomeUsuario() {

        for (String nome : nomes) {

            //Cenário
            Cliente cliente = new Cliente(nome);

            //Ação
            Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
            Iterator it = violations.iterator();
//            while (it.hasNext()) {
            ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
            String message = x.getMessage();
//            }

            assertThat(message, is(retorno));
        }

    }

    @Test
    public void deveSerUnicoONome() {
        //Cenário
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();
        
        //Ação
        try {

            cliente1.setNome("Pedro");
            cliente2.setNome("Pedro");
            fail();

        } catch (Exception e) {
            //Validação
            Object ExceptionCliente = null;
            Assert.assertSame(ExceptionCliente, e);
        }
    }

    

}
