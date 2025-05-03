package development.twomicroservices.service;

import development.twomicroservices.dto.UserDto;
import development.twomicroservices.entity.RoleEntity;
import development.twomicroservices.entity.UserEntity;
import development.twomicroservices.exception.InvalidRoleNotFound;
import development.twomicroservices.exception.InvalidUserAlreadyExists;
import development.twomicroservices.exception.InvalidUserNotFound;
import development.twomicroservices.repository.RoleRepository;
import development.twomicroservices.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity registerUser(UserDto user) {
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new InvalidUserAlreadyExists("Пользователь уже существует");
        }

        RoleEntity roleEntity = roleRepository.findByName("ROLE_USER");
        roleSearch(roleEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(user.getLogin());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRole(roleEntity);

        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> getUser(String login) {
        return Optional.ofNullable(userRepository.findByLogin(login));
    }

    public void deleteUser(String login) {
        UserEntity user = userRepository.findByLogin(login);
        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new InvalidUserNotFound("Пользователь не найден");
        }
    }

    public UserEntity changeUserRole(String login, String newRole) {
        UserEntity user = userRepository.findByLogin(login);
        if (user == null) {
            throw new InvalidUserNotFound("Пользователь не найден");
        }
        if (!newRole.equals("ROLE_ADMIN") && !newRole.equals("ROLE_USER")) {
            throw new InvalidRoleNotFound("Введена некорректная роль");
        }

        RoleEntity newRoleEntity = roleRepository.findByName(newRole);
        roleSearch(newRoleEntity);

        user.setRole(newRoleEntity);
        return userRepository.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    private void roleSearch(RoleEntity roleEntity) {
        if (roleEntity == null) {
            throw new InvalidRoleNotFound("Роль не найдена");
        }
    }
}