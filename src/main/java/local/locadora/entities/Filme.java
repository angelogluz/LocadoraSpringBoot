package local.locadora.entities;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import local.locadora.exceptions.FilmeException;
import org.springframework.format.annotation.NumberFormat;

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
    @NumberFormat(pattern = "#,##0.00")
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

    public void setNome(String nome) throws FilmeException {
        if (nome.equals("")){
            throw new FilmeException("Não é possível cadastrar um filme sem nome.");
        }
        this.nome = nome;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque)throws FilmeException {
        if (estoque == null){
            throw new FilmeException("Não é possível cadastrar um filme sem valor no estoque.");
        }
        this.estoque = estoque;
    }

    public Double getPrecoLocacao() {
        return precoLocacao;
    }

    public void setPrecoLocacao(Double precoLocacao)throws FilmeException {
        if (precoLocacao == null){
            throw new FilmeException("Não é possível cadastrar um filme sem valor de locação.");
        }  
        this.precoLocacao = precoLocacao;
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
        return nome;
    }
    
}