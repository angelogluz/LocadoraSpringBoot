/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.entities;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *
 * @author belinglima
 */
@RunWith(Suite.class)
@SuiteClasses({ClienteEntityTest.class, FilmeEntityTest.class, LocacaoEntityTest.class})
public class AllTestes {
    
    
    /* Suite para rodar os testes de uma vez só */
    @BeforeClass
    public static void setUp(){	
    }
		
    @AfterClass
    public static void afterAll(){		
    }

}
