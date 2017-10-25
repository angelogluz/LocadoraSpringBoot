/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.controller.rest;

/**
 * @author angelogl
 */


import local.locadora.dao.LocacaoDAO;
import local.locadora.entities.Locacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocacaoControllerAPI {


    @Autowired
    LocacaoDAO locacaoDAO;

    // -------------------Retrieve All Locacaos---------------------------------------------

    @RequestMapping(value = "/locacao", method = RequestMethod.GET)
    public ResponseEntity<List<Locacao>> listAllLocacaos() {
        List<Locacao> locacaos = locacaoDAO.findAll();
        if (locacaos.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(locacaos, HttpStatus.OK);
    }

    // -------------------Retrieve Single Locacao------------------------------------------

    @RequestMapping(value = "/locacao/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLocacao(@PathVariable("id") long id) {

        Locacao locacao = locacaoDAO.findById(id).get();
        if (locacao == null) {

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(locacao, HttpStatus.OK);
    }

    // -------------------Create a Locacao-------------------------------------------

    @RequestMapping(value = "/locacao", method = RequestMethod.POST)
    public ResponseEntity<?> createLocacao(@RequestBody Locacao locacao, UriComponentsBuilder ucBuilder) {
        if (locacaoDAO.existsById(locacao)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        locacaoDAO.save(locacao);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/locacao/{id}").buildAndExpand(locacao.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // ------------------- Update a Locacao ------------------------------------------------

    @RequestMapping(value = "/locacao/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLocacao(@PathVariable("id") long id, @RequestBody Locacao locacao) {

        Locacao currentLocacao = locacaoDAO.findById(id).get();

        if (currentLocacao == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        currentLocacao.setDataLocacao(locacao.getDataLocacao());
        currentLocacao.setDataRetorno(locacao.getDataRetorno());
        currentLocacao.setFilmes(locacao.getFilmes());
        currentLocacao.setCliente(locacao.getCliente());

        locacaoDAO.save(currentLocacao);
        return new ResponseEntity<>(currentLocacao, HttpStatus.OK);
    }

    // ------------------- Delete a Locacao-----------------------------------------

    @RequestMapping(value = "/locacao/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLocacao(@PathVariable("id") long id) {
        Locacao locacao = locacaoDAO.findById(id).get();
        if (locacao == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        locacaoDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Locacaos-----------------------------

    @RequestMapping(value = "/locacao", method = RequestMethod.DELETE)
    public ResponseEntity<Locacao> deleteAllLocacaos() {
        locacaoDAO.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
