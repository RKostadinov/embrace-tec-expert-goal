package nl.expertgoal.application.Controllers;

import javassist.NotFoundException;
import nl.expertgoal.application.Entities.Role;
import nl.expertgoal.application.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/role")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @PostMapping("/role")
    public Role createRole(@Valid @RequestBody Role role) {
        return roleRepository.save(role);
    }

    @PutMapping("/role/{roleId}")
    public Role updateRole(@PathVariable Long roleId, @Valid @RequestBody Role roleRequest) throws NotFoundException {
        return roleRepository.findById(roleId).map(role -> {
            role.setName(roleRequest.getName());
            return roleRepository.save(role);
        }).orElseThrow(() -> new NotFoundException("RoleId " + roleId + " not found"));
    }

    @DeleteMapping("/role/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId) throws NotFoundException {
        return roleRepository.findById(roleId).map(role -> {
            roleRepository.delete(role);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new NotFoundException("RoleId " + roleId + " not found"));
    }
}
