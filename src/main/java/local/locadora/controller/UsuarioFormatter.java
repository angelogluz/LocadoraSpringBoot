package local.locadora.controller;

import java.text.ParseException;
import java.util.Locale;
import local.locadora.dao.UsuarioDAO;
import local.locadora.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;

@Controller
public class UsuarioFormatter implements Formatter<Usuario> {

    @Autowired
    private UsuarioDAO userDAO;

    @Override
    public String print(Usuario usuario, Locale locale) {
        return "" + usuario.getId();
    }

    @Override
    public Usuario parse(String id, Locale locale) throws ParseException {
        return userDAO.findById(Long.parseLong(id)).get();
    }
}
