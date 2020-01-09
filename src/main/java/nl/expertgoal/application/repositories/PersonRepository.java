package nl.expertgoal.application.repositories;

import nl.expertgoal.application.Entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByCompanyId(Long companyId);

    Optional<Person> findByIdAndCompanyId(Long Id, Long companyId);
}
