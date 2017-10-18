/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.controller.rest;

/**
 *
 * @author angelogl
 */

 
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
 
import local.locadora.dao.UsuarioDAO;
import local.locadora.entities.Usuario;
import local.locadora.exceptions.UsuarioException;
 
@RestController
@RequestMapping("/api")
public class UsuarioControllerAPI {
 
    
    @Autowired
    UsuarioDAO usuarioDAO; 
    
    // -------------------Retrieve All Usuarios---------------------------------------------
 
    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    public ResponseEntity<List<Usuario>> listAllUsuarios() {
        List<Usuario> usuarios = usuarioDAO.findAll();
        if (usuarios.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
 
    // -------------------Retrieve Single Usuario------------------------------------------
 
    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUsuario(@PathVariable("id") long id) {
        
        Usuario usuario = usuarioDAO.findById(id);
        if (usuario == null) {
            
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
 
    // -------------------Create a Usuario-------------------------------------------
 
    @RequestMapping(value = "/usuario", method = RequestMethod.POST)
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario, UriComponentsBuilder ucBuilder) {
        if (usuarioDAO.existsById(usuario)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        usuarioDAO.save(usuario);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/usuario/{id}").buildAndExpand(usuario.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
 
    // ------------------- Update a Usuario ------------------------------------------------
 
    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUsuario(@PathVariable("id") long id, @RequestBody Usuario usuario) {
        
        Usuario currentUsuario = usuarioDAO.findById(id);
 
        if (currentUsuario == null) {
             return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
 
        try {
            currentUsuario.setNome(usuario.getNome());
        } catch (UsuarioException ex) {
            Logger.getLogger(UsuarioControllerAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        usuarioDAO.save(currentUsuario);
        return new ResponseEntity<>(currentUsuario, HttpStatus.OK);
    }
 
    // ------------------- Delete a Usuario-----------------------------------------
 
    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUsuario(@PathVariable("id") long id) {
        Usuario usuario = usuarioDAO.findById(id);
        if (usuario == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        usuarioDAO.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
 
    // ------------------- Delete All Usuarios-----------------------------
 
    @RequestMapping(value = "/usuario", method = RequestMethod.DELETE)
    public ResponseEntity<Usuario> deleteAllUsuarios() {
        usuarioDAO.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
 
}
