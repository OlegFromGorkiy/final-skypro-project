package ru.skypro.homework.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean emailCheck(User user) {
        return userRepository.existsByEmail(user.getEmail());
    }


    @Override
    public User getByEmail(String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        return optional.isEmpty() ? null : optional.get();
    }

    @Override
    public User updateUser(User user, UpdateUser update) {
        user.setFirstName(update.getFirstname());
        user.setLastName(update.getLastName());
        user.setPhone(update.getPhone());
        return saveUser(user);
    }


//UserDetailsService implementation
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
        User user = getByEmail(username);
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
