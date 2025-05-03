package development.twomicroservices.controller;

import development.twomicroservices.dto.RoleDto;
import development.twomicroservices.entity.RoleEntity;
import development.twomicroservices.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/roles")
    public ResponseEntity<RoleEntity> createRole(@RequestBody RoleDto role) {
        return ResponseEntity.ok(roleService.createRole(role));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleEntity>> getAllRoles() {
        List<RoleEntity> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<RoleEntity> updateRoleName(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        RoleEntity updatedRole = roleService.updateRoleName(id, roleDto.getName());
        return ResponseEntity.ok(updatedRole);
    }
}