package development.twomicroservices.controller;

import development.twomicroservices.dto.RoleDto;
import development.twomicroservices.entity.RoleEntity;
import development.twomicroservices.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoleControllerTest {

    @Mock
    private RoleService roleService;

    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleController = new RoleController(roleService);
    }

    @Test
    void createRole_Success() {
        RoleDto roleDto = new RoleDto();
        roleDto.setName("ROLE_ADMIN");
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setName("ROLE_USER");

        when(roleService.createRole(any(RoleDto.class))).thenReturn(roleEntity);

        ResponseEntity<RoleEntity> response = roleController.createRole(roleDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roleEntity, response.getBody());
        verify(roleService, times(1)).createRole(roleDto);
    }

    @Test
    void getAllRoles_Success() {
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(new RoleEntity(1L, "ROLE_USER"));
        roles.add(new RoleEntity(2L, "ROLE_ADMIN"));

        when(roleService.getAllRoles()).thenReturn(roles);

        ResponseEntity<List<RoleEntity>> response = roleController.getAllRoles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roles, response.getBody());
        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    void deleteRole_Success() {
        Long roleId = 1L;

        ResponseEntity<Void> response = roleController.deleteRole(roleId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(roleService, times(1)).deleteRole(roleId);
    }

    @Test
    void updateRoleName_Success() {
        Long roleId = 1L;
        RoleDto roleDto = new RoleDto();
        roleDto.setName("ROLE_ADMIN");
        RoleEntity updatedRole = new RoleEntity();
        updatedRole.setId(roleId);
        updatedRole.setName("ROLE_ADMIN");

        when(roleService.updateRoleName(roleId, roleDto.getName())).thenReturn(updatedRole);

        ResponseEntity<RoleEntity> response = roleController.updateRoleName(roleId, roleDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRole, response.getBody());
        verify(roleService, times(1)).updateRoleName(roleId, roleDto.getName());
    }
}