package br.com.erudio.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erudio.model.ERole;
import br.com.erudio.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	  Optional<Role> findByName(ERole name);

}
