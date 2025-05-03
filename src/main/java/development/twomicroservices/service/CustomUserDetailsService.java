package development.twomicroservices.service;

import development.twomicroservices.entity.RoleEntity;
import development.twomicroservices.entity.UserEntity;
import development.twomicroservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByLogin(login);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Пользователь с именем пользователя не найден: " + login);
        }
        return new User(userEntity.getLogin(), userEntity.getPassword(), getAuthorities(userEntity.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(RoleEntity role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }
}