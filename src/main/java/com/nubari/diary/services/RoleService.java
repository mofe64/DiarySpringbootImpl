package com.nubari.diary.services;

import com.nubari.diary.exceptions.UserRoleNotFoundException;
import com.nubari.diary.models.Role;

public interface RoleService {
    Role findByName(String name) throws UserRoleNotFoundException;
    void createNewRole(Role role);
}
