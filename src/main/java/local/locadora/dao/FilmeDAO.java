/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.dao;

import java.util.List;
import local.locadora.entities.Filme;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author angelogl
 */
@Repository
public interface FilmeDAO extends JpaRepository<Filme, Long> {

    List<Filme> findByNome(String nome);
    
    boolean deleteById(Long id);

    Filme findById(Long id);

    Slice<Filme> findTop3ByNome(String lastname, Pageable pageable);

    List<Filme> findFirst10ByNome(String lastname, Sort sort);

    List<Filme> findByEstoqueGreaterThan(Integer estoque);
    
    boolean existsById(Filme filme);
}
