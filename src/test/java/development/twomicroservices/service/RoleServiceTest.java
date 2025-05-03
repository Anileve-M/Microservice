package development.twomicroservices.service;

import development.twomicroservices.dto.RoleDto;
import development.twomicroservices.entity.RoleEntity;
import development.twomicroservices.exception.HavingRole;
import development.twomicroservices.exception.InvalidRoleNotFound;
import development.twomicroservices.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private RoleDto roleDto;
    private RoleEntity roleEntity;

    @BeforeEach
    public void setUp() {
        roleDto = new RoleDto();
        roleDto.setName("ROLE_USER");

        roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_USER");
    }

    @Test
    public void testCreateRole_Success() {
        when(roleRepository.findByName(roleDto.getName())).thenReturn(null);
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleEntity);

        RoleEntity createdRole = roleService.createRole(roleDto);

        assertNotNull(createdRole);
        assertEquals("ROLE_USER", createdRole.getName());
        verify(roleRepository).save(any(RoleEntity.class));
    }

    @Test
    public void testCreateRole_AlreadyExists() {
        when(roleRepository.findByName(roleDto.getName())).thenReturn(roleEntity);

        Exception exception = assertThrows(HavingRole.class, () -> {
            roleService.createRole(roleDto);
        });

        assertEquals("Роль с таким именем уже существует", exception.getMessage());
    }

    @Test
    public void testGetAllRoles() {
        when(roleRepository.findAll()).thenReturn(Collections.singletonList(roleEntity));

        List<RoleEntity> roles = roleService.getAllRoles();

        assertEquals(1, roles.size());
        assertEquals("ROLE_USER", roles.get(0).getName());
    }

    @Test
    public void testDeleteRole_Success() {
        when(roleRepository.existsById(1L)).thenReturn(true);

        roleService.deleteRole(1L);

        verify(roleRepository).deleteById(1L);
    }

    @Test
    public void testDeleteRole_NotFound() {
        when(roleRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(InvalidRoleNotFound.class, () -> {
            roleService.deleteRole(1L);
        });

        assertEquals("Роль не найдена", exception.getMessage());
    }

    @Test
    public void testUpdateRoleName_Success() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(roleEntity));
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(null);
        when(roleRepository.save(roleEntity)).thenReturn(roleEntity);

        RoleEntity updatedRole = roleService.updateRoleName(1L, "ROLE_ADMIN");

        assertEquals("ROLE_ADMIN", updatedRole.getName());
        verify(roleRepository).save(roleEntity);
    }

    @Test
    public void testUpdateRoleName_NotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidRoleNotFound.class, () -> {
            roleService.updateRoleName(1L, "ROLE_ADMIN");
        });

        assertEquals("Роль не найдена", exception.getMessage());
    }

    @Test
    public void testUpdateRoleName_AlreadyExists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(roleEntity));
        when(roleRepository.findByName("ROLE_USER")).thenReturn(roleEntity);

        Exception exception = assertThrows(HavingRole.class, () -> {
            roleService.updateRoleName(1L, "ROLE_USER");
        });

        assertEquals("Роль с таким именем уже существует", exception.getMessage());
    }
}