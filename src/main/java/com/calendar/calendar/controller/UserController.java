package com.calendar.calendar.controller;

import com.calendar.calendar.dto.ApiResponse;
import com.calendar.calendar.dto.UserDto;
import com.calendar.calendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody UserDto userDTO) {
        ApiResponse<?> response;
        try {
            response = userService.createUser(userDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response = new ApiResponse<>( "some error occurred.");
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<?>> updateUser(@RequestBody UserDto userDTO) {
        ApiResponse<?> response;
        try {
            response = userService.updateUser(userDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response = new ApiResponse<>("some error occurred.");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{emailAddress}")
    public ResponseEntity<?> getUserById(@PathVariable String emailAddress) {
        ApiResponse<?> response;
        try {
            response = userService.getUserDetails(emailAddress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response = new ApiResponse<>("some error occurred.");
        }
        return ResponseEntity.ok(response);
    }

}
