/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.controller;
import local.locadora.dao.ClienteDAO;
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
import local.locadora.dao.LocacaoDAO;
import local.locadora.entities.Filme;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LocacaoControllerTest {
    
    @Autowired
    LocacaoDAO locacoaRepository;
    
    @Autowired
    private LocacaoDAO locacaoRepository;

    @Autowired
    private FilmeDAO filmeRepository;

    @Autowired
    private ClienteDAO clienteRepository;
    
    LocacaoController locacaoController;
    
    @Before
    public void setup() {
        locacaoController = new LocacaoController(locacaoRepository, filmeRepository, clienteRepository);
    }
    
    @Test
    public void naoDeveValidarLocacaoSemCliente() {
        Cliente cliente = null;
        Filme filme = new Filme("Dogma", 2, 5.00);
        
        locacaoController.alugarFilme(locacao, bindingResult, flash)
    }
}
