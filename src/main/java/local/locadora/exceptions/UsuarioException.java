/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Jones
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Um erro inesperado ocorreu")
public class UsuarioException extends Exception {

    /**
     * Creates a new instance of <code>UsuarioException</code> without detail
     * message.
     */
    public UsuarioException() {
    }

    
    public UsuarioException(String msg) {
        super(msg);
    }
}
