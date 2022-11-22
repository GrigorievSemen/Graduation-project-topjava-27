package ru.grigoriev.graduationproject.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.grigoriev.graduationproject.exception.NotFoundException;
import ru.grigoriev.graduationproject.model.User;
import ru.grigoriev.graduationproject.repository.UserRepository;
import ru.grigoriev.graduationproject.security.jwt.JwtUser;
import ru.grigoriev.graduationproject.security.jwt.JwtUserFactory;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Optional<User> user = Optional.ofNullable(repository.findByName(name)
                .orElseThrow(() ->
                        new NotFoundException("User does not exist in the database")));

        JwtUser jwtUser = JwtUserFactory.create(user.get());
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", name);
        return jwtUser;
    }
}
