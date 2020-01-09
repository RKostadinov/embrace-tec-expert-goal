package nl.expertgoal.application.repositories;

import nl.expertgoal.application.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
