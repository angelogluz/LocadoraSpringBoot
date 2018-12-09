package local.locadora.entities;

import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Validated
public class Locacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn
    @NotNull(message = "Um cliente deve ser selecionado")
    private Cliente cliente;
    @ManyToMany
    @Column
    @NotNull(message = "Pelo menos um filme deve ser selecionado")
    private List<Filme> filmes;
    @NotNull(message = "A data de locação não deve ser nula")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLocacao;
    @NotNull(message = "A data de retorno não deve ser nula")
    @Temporal(TemporalType.TIMESTAMP)
    @Future(message = "A data deve retorno deve ser futura")
    private Date dataRetorno;
    @Digits(integer = 2, fraction = 2, message = "O Preço deve ter no máximo dois dígitos")
    @Positive(message = "O valor da locação deve ser positivo")
    private Double valor;


    public Locacao() {
        cliente = new Cliente();
        filmes = new ArrayList<>();
        valor = 0d;
    }


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }

    public String getListaFilmes() {
        String print = "";
        for (int i = 0; i < filmes.size() - 1; i++) {
            print += (filmes.get(i).getNome() + ", ");
        }
        if (filmes.size() > 0) {
            print += (filmes.get(filmes.size() - 1).getNome());
        }
        return print;
    }

    public void addFilme(Filme filme) {
        filmes.add(filme);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    void setCliente(String joao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
