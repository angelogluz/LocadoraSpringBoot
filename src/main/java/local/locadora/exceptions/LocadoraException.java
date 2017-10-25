package local.locadora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Um erro inesperado ocorreu")
public class LocadoraException extends Exception {

    public LocadoraException(String msg) {
        super(msg);
    }
}
