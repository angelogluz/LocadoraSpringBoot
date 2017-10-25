package local.locadora.controller;

import local.locadora.dao.ClienteDAO;
import local.locadora.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.Locale;

@Controller
public class ClienteFormatter implements Formatter<Cliente> {

    @Autowired
    private ClienteDAO userDAO;

    @Override
    public String print(Cliente cliente, Locale locale) {
        return "" + cliente.getId();
    }

    @Override
    public Cliente parse(String id, Locale locale) throws ParseException {
        return userDAO.findById(Long.parseLong(id)).get();
    }
}
