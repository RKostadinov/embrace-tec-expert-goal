package nl.expertgoal.application.Controllers;

import javassist.NotFoundException;
import nl.expertgoal.application.Entities.Person;
import nl.expertgoal.application.repositories.CompanyRepository;
import nl.expertgoal.application.repositories.PersonRepository;
import nl.expertgoal.application.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/company/{companyId}/persons")
    public List<Person> getAllPersonsByCompanyId(@PathVariable(value = "companyId") Long companyId) {
        return personRepository.findByCompanyId(companyId);
    }

    @PostMapping("/company/{companyId}/persons")
    public Person createPerson(@PathVariable(value = "companyId") Long companyId, @Valid @RequestBody Person person) throws NotFoundException {
        return companyRepository.findById(companyId).map(company -> {
            person.setCompany(company);
            return personRepository.save(person);
        }).orElseThrow(() -> new NotFoundException("companyId " + companyId + " not found"));
    }

    @PutMapping("/company/{companyId}/persons/{personId}")
    public Person updatePerson(@PathVariable(value = "companyId") Long companyId, @PathVariable(value = "personId") Long personId, @Valid @RequestBody Person personRequest) throws NotFoundException {
        if (!companyRepository.existsById(companyId)) {
            throw new NotFoundException("CompanyId " + companyId + " not found");
        }

        return personRepository.findById(personId).map(person -> {
            person.setName(personRequest.getName());
            person.setAge(personRequest.getAge());
            return personRepository.save(person);
        }).orElseThrow(() -> new NotFoundException("PersonId " + personId + "not found"));
    }

    @DeleteMapping("/company/{companyId}/persons/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable(value = "companyId") Long companyId,
                                          @PathVariable(value = "personId") Long personId) throws NotFoundException {
        return personRepository.findByIdAndCompanyId(personId, companyId).map(person -> {
            personRepository.delete(person);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new NotFoundException("Person not found with id " + personId + " and postId " + companyId));
    }


    @GetMapping("/person/{personId}/role/{roleId}")
    public Person mapPersonRole(@PathVariable(value = "personId") Long personId, @PathVariable(value = "roleId") Long roleId) throws NotFoundException {
        return personRepository.findById(personId).map(person -> {
            person.getRoles().add(roleRepository.getOne(roleId));
            return personRepository.save(person);
        }).orElseThrow(() -> new NotFoundException("personId " + personId + " not found"));
    }

}
