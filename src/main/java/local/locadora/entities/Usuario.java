package local.locadora.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.*;
import local.locadora.exceptions.UsuarioException;
import local.locadora.utils.NumberUtils;

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
        if (NumberUtils.isNumeric(nome) == true) {
            throw new UsuarioException("Usuario com numero no nome");
        }

        char ch = nome.charAt(0);

        if (Character.isUpperCase(ch) == false) {
            ch = Character.toUpperCase(ch);
            char subnome[] = nome.toCharArray();
            subnome[0] = ch;
            nome = new String(subnome);
        }
        
        this.nome = nome;
        System.out.println(nome);
        return nome;
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