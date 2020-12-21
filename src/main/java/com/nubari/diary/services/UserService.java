package com.nubari.diary.services;

import com.nubari.diary.DTOs.UserDto;
import com.nubari.diary.exceptions.UserNotFoundException;
import com.nubari.diary.exceptions.UserRoleNotFoundException;
import com.nubari.diary.models.User;

import java.util.List;

public interface UserService {
    User save(UserDto userDto) throws UserRoleNotFoundException;
    List<User> findAll();
    UserDto findOne(String username) throws UserNotFoundException;
}
