package local.locadora.entities;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ClienteEntityTest.class, FilmeEntityTest.class, LocacaoEntityTest.class})
public class Testes {

    @BeforeClass
    public static void setUp() {
    }

    @AfterClass
    public static void afterAll() {
    }

}
