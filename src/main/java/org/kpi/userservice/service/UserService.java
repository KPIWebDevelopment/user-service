package org.kpi.userservice.service;

import jakarta.transaction.Transactional;
import org.kpi.userservice.model.User;
import org.kpi.userservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(String name, String email, String password) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User(name, email, passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        Optional<User> result = userRepository.findUserByEmail(email);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("User with " + email +" email not exists");
        }

        return result.get();
    }

    public User getUserById(UUID id) {
        Optional<User> result = userRepository.findUserById(id);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("User with " + id +" id not exists");
        }

        return result.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).get();
        return user;
    }
}
