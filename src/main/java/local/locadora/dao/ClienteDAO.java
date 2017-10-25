/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.dao;

import local.locadora.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author angelogl
 */
@Repository
public interface ClienteDAO extends JpaRepository<Cliente, Long> {

    Cliente findByNome(String nome);

    Cliente findByCpf(String cpf);

    boolean existsById(Cliente Usuario);
}
