package local.locadora.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.*;

@Entity
public class Locacao implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Usuario usuario;
    @ManyToMany
    private List<Filme> filmes;
    
    @Temporal(TemporalType.DATE)
    private Date dataLocacao;
    @Temporal(TemporalType.DATE)
    private Date dataRetorno;
    private Double valor;
    

    public Locacao() {
        filmes = new ArrayList<>();
        valor = 0d;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(Date dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void addFilme(Filme filme) {
        filmes.add(filme);
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
