package development.twomicroservices.controller;

import development.twomicroservices.dto.RoleChangeRequest;
import development.twomicroservices.dto.UserDto;
import development.twomicroservices.entity.RoleEntity;
import development.twomicroservices.entity.UserEntity;
import development.twomicroservices.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegistrationControllerTest {
    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    private final String LOGIN = "Nikola";
    private final String PASSWORD = "user3";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
        UserDto userDto = new UserDto(LOGIN, PASSWORD);
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(LOGIN);
        userEntity.setPassword(PASSWORD);

        when(registrationService.registerUser(any(UserDto.class))).thenReturn(userEntity);
        ResponseEntity<UserEntity> response = registrationController.register(userDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getUser_Success() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(LOGIN);
        userEntity.setPassword(PASSWORD);

        when(registrationService.getUser(LOGIN)).thenReturn(Optional.of(userEntity));
        ResponseEntity<Optional<UserEntity>> response = registrationController.getUser(LOGIN);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteUser_Success() {
        ResponseEntity<String> response = registrationController.deleteUser(LOGIN);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void changeUserRole_Success() {
        RoleEntity newRole = new RoleEntity();
        newRole.setName("ROLE_ADMIN");

        UserEntity user = new UserEntity();
        user.setLogin(LOGIN);
        user.setRole(newRole);

        when(registrationService.changeUserRole(LOGIN, newRole.getName())).thenReturn(user);
        ResponseEntity<UserEntity> response = registrationController.changeUserRole(LOGIN,
                new RoleChangeRequest(newRole.getName()));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllUsers_Success() {
        UserEntity user1 = new UserEntity();
        user1.setLogin("user1");
        UserEntity user2 = new UserEntity();
        user2.setLogin("user2");

        List<UserEntity> userList = Arrays.asList(user1, user2);

        when(registrationService.getAllUsers()).thenReturn(userList);
        ResponseEntity<List<UserEntity>> response = registrationController.getAllUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}