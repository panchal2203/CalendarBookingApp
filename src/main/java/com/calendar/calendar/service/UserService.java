package com.calendar.calendar.service;

import com.calendar.calendar.dto.ApiResponse;
import com.calendar.calendar.dto.UserDto;

public interface UserService {
    ApiResponse<?> createUser(UserDto userDTO);
    ApiResponse<UserDto> getUserDetails(String emailAddress);
    ApiResponse<UserDto> updateUser(UserDto userDTO);
}
