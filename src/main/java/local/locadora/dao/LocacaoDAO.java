/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.dao;

import local.locadora.entities.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author angelogl
 */
@Repository
public interface LocacaoDAO extends JpaRepository<Locacao, Long> {

    boolean existsById(Locacao locacao);
}
