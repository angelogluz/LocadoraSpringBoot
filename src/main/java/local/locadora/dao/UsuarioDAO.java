/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.dao;

import local.locadora.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author angelogl
 */
@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    Usuario findById(Long id);
    
    Usuario findByNome(String nome);
    
    boolean deleteById(Long id);

    boolean existsById(Usuario Usuario);
}
