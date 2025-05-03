package development.twomicroservices.controller;

import development.twomicroservices.dto.RoleChangeRequest;
import development.twomicroservices.dto.UserDto;
import development.twomicroservices.entity.UserEntity;
import development.twomicroservices.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/user/register")
    public ResponseEntity<UserEntity> register(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(registrationService.registerUser(userDto), HttpStatus.OK);
    }

    @GetMapping("/admin/{login}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Optional<UserEntity>> getUser(@PathVariable String login) {
        return new ResponseEntity<>(registrationService.getUser(login), HttpStatus.OK);
    }

    @DeleteMapping("/admin/{login}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String login) {
        registrationService.deleteUser(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/admin/{login}/role")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserEntity> changeUserRole(@PathVariable String login,
                                                     @RequestBody RoleChangeRequest newRole) {
        UserEntity updatedUser = registrationService.changeUserRole(login, newRole.getRole());
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/api/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = registrationService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}