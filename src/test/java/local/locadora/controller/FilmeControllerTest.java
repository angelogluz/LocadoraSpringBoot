/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import local.locadora.dao.FilmeDAO;
import local.locadora.entities.Cliente;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;
import local.locadora.dao.FilmeDAO;

import static org.junit.Assert.fail;

/**
 *
 * @author Matheus Vieira
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FilmeControllerTest {
    
    @Mock
    Model model;

    @Mock
    RedirectAttributes flash;

    @Mock
    BindingResult bind;

    @Mock
    FilmeController controller;

    @Autowired
    FilmeDAO filmeRepository;

    @Before
    public void setup() {
        controller = new FilmeController(filmeRepository);
    }

    
}
