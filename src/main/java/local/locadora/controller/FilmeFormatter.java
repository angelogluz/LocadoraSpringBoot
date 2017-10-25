package local.locadora.controller;


import local.locadora.dao.FilmeDAO;
import local.locadora.entities.Filme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.Locale;

@Controller
public class FilmeFormatter implements Formatter<Filme> {

    @Autowired
    private FilmeDAO filmeDAO;

    @Override
    public String print(Filme filme, Locale locale) {
        return "" + filme.getId();
    }

    @Override
    public Filme parse(String id, Locale locale) throws ParseException {

        return filmeDAO.findById(Long.parseLong(id)).get();

    }
}
