package com.dailycodework.buynowdotcom.controller;

import com.dailycodework.buynowdotcom.dtos.UserDto;
import com.dailycodework.buynowdotcom.model.User;
import com.dailycodework.buynowdotcom.request.CreateUserRequest;
import com.dailycodework.buynowdotcom.request.UserUpdateRequest;
import com.dailycodework.buynowdotcom.response.ApiResponse;
import com.dailycodework.buynowdotcom.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/user/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(new ApiResponse("Found!",user));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        UserDto user = userService.createUser(request);
        return ResponseEntity.ok(new ApiResponse("User Created: ",user));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId){
        UserDto user = userService.updateUser(request,userId);
        return ResponseEntity.ok(new ApiResponse("User Updated: ",user));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> createUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse("User Deleted Successfully!",null));
    }
}
