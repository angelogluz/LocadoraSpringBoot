package local.locadora.entities;


import local.locadora.exceptions.FilmeSemPrecoException;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import local.locadora.exceptions.FilmeSemEstoqueException;
import local.locadora.exceptions.FilmeSemNomeException;

@Entity
public class Filme implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String nome; 
    @Column
    private Integer estoque;
    @Column
    private Double precoLocacao;

    public Filme() {
    }

    public Filme(String nome, Integer estoque, Double precoLocacao) {
        this.nome = nome;
        this.estoque = estoque;
        this.precoLocacao = precoLocacao;
    }

    public String getNome() {
        return nome;
    }

    public String setNome(String nome) throws FilmeSemNomeException{
        if(nome.equals("")){
            throw new FilmeSemNomeException("Filme sem nome");
        }
        
        this.nome = nome;
        return "ok";
        
    }

    public Integer getEstoque() {
        return estoque;
    }

    public String setEstoque(Integer estoque) throws FilmeSemEstoqueException {
        if(estoque == 0){
            throw new FilmeSemEstoqueException("Filme sem estoque");
        }
        
        this.estoque = estoque;
        return "OK";
    }

    public Double getPrecoLocacao() {
        return precoLocacao;
    }

    public String setPrecoLocacao(Double precoLocacao) throws FilmeSemPrecoException {
        if(precoLocacao == 0){
            throw new FilmeSemPrecoException("Filme sem preço");
        }
        
        this.precoLocacao = precoLocacao;
        return "ok";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
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
        final Filme other = (Filme) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Filme{" + nome + ", estoque=" + estoque + '}';
    }
    
}
