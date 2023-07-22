package ru.skypro.homework.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.UserService;

import java.util.Collection;
import java.util.LinkedList;
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email address %s, not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), mapRoleToAuthorities(user.getRole()));
    }
    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Role role) {
        Collection<SimpleGrantedAuthority> result = new LinkedList<>();
        result.add(new SimpleGrantedAuthority("USER"));
        if (role == Role.ADMIN) {
            result.add(new SimpleGrantedAuthority("ADMIN"));
        }
        return result;
    }
}
