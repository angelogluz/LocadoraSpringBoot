/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.controller.rest;

/**
 * @author angelogl
 */


import local.locadora.dao.FilmeDAO;
import local.locadora.entities.Filme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FilmeControllerAPI {


    @Autowired
    FilmeDAO filmeDAO;

    // -------------------Retrieve All Filmes---------------------------------------------

    @RequestMapping(value = "/filme", method = RequestMethod.GET)
    public ResponseEntity<List<Filme>> listAllFilmes() {
        List<Filme> filmes = filmeDAO.findAll();
        if (filmes.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(filmes, HttpStatus.OK);
    }

    // -------------------Retrieve Single Filme------------------------------------------

    @RequestMapping(value = "/filme/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getFilme(@PathVariable("id") long id) {

        Filme filme = filmeDAO.findById(id).get();
        if (filme == null) {

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(filme, HttpStatus.OK);
    }

    // -------------------Create a Filme-------------------------------------------

    @RequestMapping(value = "/filme", method = RequestMethod.POST)
    public ResponseEntity<?> createFilme(@RequestBody Filme filme, UriComponentsBuilder ucBuilder) {
        if (filmeDAO.existsById(filme)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        filmeDAO.save(filme);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/filme/{id}").buildAndExpand(filme.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // ------------------- Update a Filme ------------------------------------------------

    @RequestMapping(value = "/filme/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFilme(@PathVariable("id") long id, @RequestBody Filme filme) {

        Filme currentFilme = filmeDAO.findById(id).get();

        if (currentFilme == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        currentFilme.setNome(filme.getNome());
        currentFilme.setEstoque(filme.getEstoque());
        currentFilme.setPrecoLocacao(filme.getPrecoLocacao());

        filmeDAO.save(currentFilme);
        return new ResponseEntity<>(currentFilme, HttpStatus.OK);
    }

    // ------------------- Delete a Filme-----------------------------------------

    @RequestMapping(value = "/filme/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFilme(@PathVariable("id") long id) {
        Filme filme = filmeDAO.findById(id).get();
        if (filme == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        filmeDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Filmes-----------------------------

    @RequestMapping(value = "/filme", method = RequestMethod.DELETE)
    public ResponseEntity<Filme> deleteAllFilmes() {
        filmeDAO.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
