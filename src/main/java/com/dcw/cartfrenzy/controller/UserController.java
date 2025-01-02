package com.dcw.cartfrenzy.controller;

import com.dcw.cartfrenzy.dto.UserDto;
import com.dcw.cartfrenzy.request.CreateUserRequest;
import com.dcw.cartfrenzy.request.UserUpdateRequest;
import com.dcw.cartfrenzy.response.ApiResponse;
import com.dcw.cartfrenzy.service.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {

        UserDto user = userService.getUserById(userId);

        return ResponseEntity.ok(new ApiResponse("Found!", user));
    }

    @PostMapping("/register-user")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {

        UserDto user = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User Created", user));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {

        UserDto user = userService.updateUser(request, userId);

        return ResponseEntity.ok(new ApiResponse("User updated!", user));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok(new ApiResponse("User deleted!", null));
    }
}
