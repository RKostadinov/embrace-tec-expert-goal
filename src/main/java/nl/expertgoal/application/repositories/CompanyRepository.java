package nl.expertgoal.application.repositories;

import nl.expertgoal.application.Entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
