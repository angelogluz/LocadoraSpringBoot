/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.controller.rest;

/**
 * @author angelogl
 */


import local.locadora.dao.ClienteDAO;
import local.locadora.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioControllerAPI {


    @Autowired
    ClienteDAO clienteDAO;

    // -------------------Retrieve All Usuarios---------------------------------------------

    @RequestMapping(value = "/cliente", method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> listAllUsuarios() {
        List<Cliente> clientes = clienteDAO.findAll();
        if (clientes.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // -------------------Retrieve Single Usuario------------------------------------------

    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUsuario(@PathVariable("id") long id) {

        Cliente cliente = clienteDAO.findById(id).get();
        if (cliente == null) {

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    // -------------------Create a Usuario-------------------------------------------

    @RequestMapping(value = "/cliente", method = RequestMethod.POST)
    public ResponseEntity<?> createUsuario(@RequestBody Cliente cliente, UriComponentsBuilder ucBuilder) {
        if (clienteDAO.existsById(cliente)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        clienteDAO.save(cliente);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/cliente/{id}").buildAndExpand(cliente.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // ------------------- Update a Usuario ------------------------------------------------

    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUsuario(@PathVariable("id") long id, @RequestBody Cliente cliente) {

        Cliente currentCliente = clienteDAO.findById(id).get();

        if (currentCliente == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        currentCliente.setNome(cliente.getNome());

        clienteDAO.save(currentCliente);
        return new ResponseEntity<>(currentCliente, HttpStatus.OK);
    }

    // ------------------- Delete a Cliente-----------------------------------------

    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCliente(@PathVariable("id") long id) {
        Cliente cliente = clienteDAO.findById(id).get();
        if (cliente == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        clienteDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Clientes-----------------------------

    @RequestMapping(value = "/cliente", method = RequestMethod.DELETE)
    public ResponseEntity<Cliente> deleteAllClientes() {
        clienteDAO.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
