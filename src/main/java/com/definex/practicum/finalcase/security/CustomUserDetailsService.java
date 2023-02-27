package com.definex.practicum.finalcase.security;

import com.definex.practicum.finalcase.model.Role;
import com.definex.practicum.finalcase.model.User;
import com.definex.practicum.finalcase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // in this context username = tckn
    @Override
    public UserDetails loadUserByUsername(String tckn) throws UsernameNotFoundException {
        User user = userRepository.findByTckn(tckn).orElseThrow(() -> new UsernameNotFoundException("User wiwth TCKN not found"));
        return new User(user.getTckn(), user.getPassword(), user.getRoles());
    }

    private Collection<GrantedAuthority> authoritiesMapper(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }
}
