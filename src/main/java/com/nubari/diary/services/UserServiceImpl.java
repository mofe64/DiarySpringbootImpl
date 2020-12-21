package com.nubari.diary.services;

import com.nubari.diary.DTOs.UserDto;
import com.nubari.diary.exceptions.UserNotFoundException;
import com.nubari.diary.exceptions.UserRoleNotFoundException;
import com.nubari.diary.models.Role;
import com.nubari.diary.models.User;
import com.nubari.diary.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadAUserByUsername(username);

    }

    private UserDetails loadAUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepo.findByUsername(username);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<User> findAll() {
        return findAllUsers();
    }

    private List<User> findAllUsers() {
        List<User> list = new ArrayList<>();
        userRepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public UserDto findOne(String username) throws UserNotFoundException {
        User user = findOneUser(username);
        UserDto userDto = new UserDto();
        userDto.packUserDto(user.getUsername(), user.getEmail(), user.getId());
        return userDto;
    }

    private User findOneUser(String username) throws UserNotFoundException {
        Optional<User> userOptional = userRepo.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User save(UserDto userDto) throws UserRoleNotFoundException {
        User user = userDto.unpackUserDto();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoles(roleSet);
        return saveUser(user);
    }

    private User saveUser(User user) {
        return userRepo.save(user);
    }
}
