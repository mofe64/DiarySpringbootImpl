package com.nubari.diary.DTOs;

import com.nubari.diary.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String userId;

    public User unpackUserDto() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public UserDto packUserDto(String username, String email, String userId) {
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setEmail(email);
        userDto.setUserId(userId);
        return userDto;
    }

    public UserDto packUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setUserId(user.getId());
        return userDto;
    }


}
