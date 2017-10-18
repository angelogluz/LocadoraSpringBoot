package local.locadora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Um erro inesperado ocorreu")
public class FilmeException extends Exception {

    public FilmeException(String msg) {
        super(msg);
    }
}