import local.locadora.entities.FilmeTest;
import local.locadora.entities.UsuarioTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *
 * @author Felipe
 * Esta suite serve para rodar todos os metodos de testes das classes espeficicadas na anotacao @SuiteClasses
 */
@RunWith(Suite.class)
@SuiteClasses({FilmeTest.class, UsuarioTest.class})
public class AllTests {
    
}
