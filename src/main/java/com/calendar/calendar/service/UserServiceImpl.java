package com.calendar.calendar.service;

import com.calendar.calendar.dto.ApiResponse;
import com.calendar.calendar.dto.UserDto;
import com.calendar.calendar.entity.User;
import com.calendar.calendar.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    public ApiResponse<?> createUser(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByEmailAddress(userDto.getEmailAddress());
        if (userOptional.isPresent()) {
            return new ApiResponse<>("Email already exists.");
        } else {
            User user = new User();
            user.setEmailAddress(userDto.getEmailAddress());
            user.setFullName(userDto.getFullName());
            userRepository.save(user);
            userDto.setId(user.getId());
            return new ApiResponse<>("success", userDto);
        }
    }

    public ApiResponse<UserDto> updateUser(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByEmailAddress(userDto.getEmailAddress());
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setFullName(userDto.getFullName());
            userRepository.save(existingUser);
            return new ApiResponse<>("success", userDto);
        } else {
            return new ApiResponse<>("Email does not exists.");
        }

    }


    public ApiResponse<UserDto> getUserDetails(String emailAddress) {
        Optional<User> userOptional = userRepository.findByEmailAddress(emailAddress);
        if (userOptional.isPresent()) {
            User userDetails= userOptional.get();
            UserDto userDto = new UserDto();
            userDto.setEmailAddress(userDetails.getEmailAddress());
            userDto.setFullName(userDetails.getFullName());
            return new ApiResponse<>("success", userDto);
        } else {
            return new ApiResponse<>("Email does not exists.");
        }

    }
}
