package development.twomicroservices.service;

import development.twomicroservices.dto.UserDto;
import development.twomicroservices.entity.RoleEntity;
import development.twomicroservices.entity.UserEntity;
import development.twomicroservices.exception.InvalidUserAlreadyExists;
import development.twomicroservices.exception.InvalidUserNotFound;
import development.twomicroservices.repository.RoleRepository;
import development.twomicroservices.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RegistrationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    private UserDto userDto;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userDto = new UserDto(USERNAME, PASSWORD);

        userEntity = new UserEntity();
        userEntity.setLogin(USERNAME);
        userEntity.setPassword(ENCODED_PASSWORD);
    }

    @Test
    void registerUser_success() {
        when(userRepository.findByLogin(USERNAME)).thenReturn(null);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("ROLE_USER");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(roleEntity); // Возвращаем роль
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity registeredUser = registrationService.registerUser(new UserDto(2, USERNAME, PASSWORD, 3));

        assertNotNull(registeredUser);
        assertEquals(USERNAME, registeredUser.getLogin());
        assertEquals(ENCODED_PASSWORD, registeredUser.getPassword());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void registerUser_userAlreadyExists() {
        when(userRepository.findByLogin(USERNAME)).thenReturn(userEntity);
        assertThrows(InvalidUserAlreadyExists.class, () -> registrationService.registerUser(userDto));
    }

    @Test
    void getUser_userExists() {
        when(userRepository.findByLogin(USERNAME)).thenReturn(userEntity);
        Optional<UserEntity> result = registrationService.getUser(USERNAME);

        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
    }

    @Test
    void getUser_userNotFound() {
        when(userRepository.findByLogin(USERNAME)).thenReturn(null);
        Optional<UserEntity> result = registrationService.getUser(USERNAME);
        assertFalse(result.isPresent());
    }

    @Test
    void deleteUser_userExists() {
        when(userRepository.findByLogin(USERNAME)).thenReturn(userEntity);
        registrationService.deleteUser(USERNAME);
        verify(userRepository).delete(userEntity);
    }

    @Test
    void deleteUser_userNotFound() {
        when(userRepository.findByLogin(USERNAME)).thenReturn(null);
        assertThrows(InvalidUserNotFound.class, () -> registrationService.deleteUser(USERNAME));
    }

    @Test
    public void testChangeUserRole_Success() {
        RoleEntity newRole = new RoleEntity();
        newRole.setName("ROLE_ADMIN");

        UserEntity user = new UserEntity();
        user.setLogin(USERNAME);
        user.setRole(newRole);

        when(userRepository.findByLogin(USERNAME)).thenReturn(user);
        when(roleRepository.findByName(newRole.getName())).thenReturn(newRole);
        when(userRepository.save(user)).thenReturn(user);

        UserEntity updatedUser = registrationService.changeUserRole(USERNAME, newRole.getName());

        assertEquals(newRole, updatedUser.getRole());
        verify(userRepository).findByLogin(USERNAME);
        verify(userRepository).save(user);
    }

    @Test
    public void testChangeUserRole_UserNotFound() {
        when(userRepository.findByLogin(USERNAME)).thenReturn(null);

        InvalidUserNotFound exception = assertThrows(InvalidUserNotFound.class, () -> {
            registrationService.changeUserRole(USERNAME, "ROLE_USER");
        });

        assertEquals("Пользователь не найден", exception.getMessage());
        verify(userRepository).findByLogin(USERNAME);
        verify(userRepository, never()).save(any());
    }
}