package nl.expertgoal.application.Controllers;

import javassist.NotFoundException;
import nl.expertgoal.application.Entities.Company;
import nl.expertgoal.application.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/company")
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @PostMapping("/company")
    public Company createCompany(@Valid @RequestBody Company Company) {
        return companyRepository.save(Company);
    }

    @PutMapping("/company/{companyId}")
    public Company updateCompany(@PathVariable Long companyId, @Valid @RequestBody Company companyRequest) throws NotFoundException {
        return companyRepository.findById(companyId).map(company -> {
            company.setName(companyRequest.getName());
            return companyRepository.save(company);
        }).orElseThrow(() -> new NotFoundException("CompanyId " + companyId + " not found"));
    }

    @DeleteMapping("/company/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long companyId) throws NotFoundException {
        return companyRepository.findById(companyId).map(company -> {
            companyRepository.delete(company);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new NotFoundException("CompanyId " + companyId + " not found"));
    }
}
