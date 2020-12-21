package com.nubari.diary.services;

import com.nubari.diary.exceptions.UserRoleNotFoundException;
import com.nubari.diary.models.Role;
import com.nubari.diary.repositories.RoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo roleRepo;


    @Override
    public Role findByName(String name) throws UserRoleNotFoundException {
        return findRoleByName(name);
    }

    private Role findRoleByName(String name) throws UserRoleNotFoundException {
        Optional<Role> role = roleRepo.findRoleByName(name);
        log.debug(String.valueOf(role.isPresent()));
        if (role.isPresent()) {
            log.debug(role.get().getName());
            return role.get();
        } else {
            throw new UserRoleNotFoundException();
        }
    }

    @Override
    public void createNewRole(Role role) {
        roleRepo.save(role);
    }
}
