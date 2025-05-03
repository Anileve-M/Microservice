package development.twomicroservices.service;

import development.twomicroservices.dto.RoleDto;
import development.twomicroservices.entity.RoleEntity;
import development.twomicroservices.exception.HavingRole;
import development.twomicroservices.exception.InvalidRoleNotFound;
import development.twomicroservices.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleEntity createRole(RoleDto role) {
        if (roleRepository.findByName(role.getName()) != null) {
            throw new HavingRole("Роль с таким именем уже существует");
        }
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(role.getName());

        return roleRepository.save(roleEntity);
    }

    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }

    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new InvalidRoleNotFound("Роль не найдена");
        }
        roleRepository.deleteById(id);
    }

    public RoleEntity updateRoleName(Long id, String newName) {
        RoleEntity roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new InvalidRoleNotFound("Роль не найдена"));

        if (roleRepository.findByName(newName) != null) {
            throw new HavingRole("Роль с таким именем уже существует");
        }

        roleEntity.setName(newName);
        return roleRepository.save(roleEntity);
    }
}