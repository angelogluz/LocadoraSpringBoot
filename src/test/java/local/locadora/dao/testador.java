/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.dao;

import local.locadora.controller.UsuarioController;
import local.locadora.entities.Usuario;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

/**
 *
 * @author Alberto
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class testador {
    
    @Autowired
    private UsuarioDAO usuarioRepository;
    
    @Mock
    Model model;
    
    UsuarioController controller;
    

    @Before
    public void setup() {
     controller = new UsuarioController(usuarioRepository);
        
    }

    @Test
    public void insere() {
        Usuario usuario = new Usuario("dalii");
        controller.save(usuario, model);
        Usuario u = usuarioRepository.findByNome("daliei");
        assertThat(u.getNome(),is(usuario.getNome()));
        
    }
    
    
    @Test
    public void insere2() {
        Usuario usuario = new Usuario();
        usuario.setNome("maria");
        controller.save(usuario, model);
        Usuario u = usuarioRepository.findByNome("maria");
        assertThat(u.getNome(),is(usuario.getNome()));
        
    }

    
    
}
