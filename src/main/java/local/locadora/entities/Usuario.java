package local.locadora.entities;

import antlr.StringUtils;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.*;
import local.locadora.exceptions.UsuarioException;
import org.springframework.util.NumberUtils;

@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;

    public Usuario() {
    }

    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String setNome(String nome) throws UsuarioException {
        if (nome.equals("")) {
            throw new UsuarioException("Usuario sem nome");
        }
        
        
        
//        if (StringUtils.isNumericSpace(nome) == true) {
//            throw new UsuarioException("Usuario com numero no nome");
//        }
//        this.nome = nome;
       return "Ok";
    }
    

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Usuario other = (Usuario) obj;
        if (nome == null) {
            if (other.nome != null) {
                return false;
            }
        } else if (!nome.equals(other.nome)) {
            return false;
        }
        return true;
    }
}
