package local.locadora.dao;

import java.util.List;
import local.locadora.entities.Filme;
import local.locadora.entities.Usuario;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)

public class FilmeRepositoryTeste {
    @Autowired
    private FilmeDAO clientRepository;
    
    public FilmeRepositoryTeste() {
    }
    
    
    //TESTE QUE NAO DEVE ACEITAR FILME COM ESTOQUE NEGATIVO
    @Test
    public void naoAceitarEstoqueNegativo() {
        Filme filme = new Filme("Teste teste", 2, -2.50);
        Filme f1 = clientRepository.save(filme);
        Filme f2 = clientRepository.findById(f1.getId());
                   
        assertTrue(f2.getEstoque() > 0);
    }
   
    //TESTE QUE NAO DEVE ACEITAR FILME COM PREÇO DE LOCACAO NEGATIVA
    @Test
    public void naoAceitarPrecoLocacaoNegativo() {
        Filme filme = new Filme("Teste teste", 2, -2.50);
        Filme f1 = clientRepository.save(filme);
        Filme f2 = clientRepository.findById(f1.getId());
        assertTrue(f2.getPrecoLocacao() > 0);
    }
    
    //TESTE QUE NAO DEVE ACEITAR NOME DO FILME SÓ COM ESPAÇOS
    @Test
    public void naoAceitarSomenteEspacoNoNome() {
        Filme filme = new Filme(" ", 2, 2.50);
        Filme f1 = clientRepository.save(filme);
        Filme f2 = clientRepository.findById(f1.getId());
        
        assertFalse(f2.getNome().matches("[ ]*"));
    }
   
}
