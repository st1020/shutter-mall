package com.st1020.shuttermall.controller;

import com.st1020.shuttermall.annotation.Permission;
import com.st1020.shuttermall.domain.vo.LoginRequest;
import com.st1020.shuttermall.domain.vo.LoginResponse;
import com.st1020.shuttermall.domain.vo.SetUserTypeRequest;
import com.st1020.shuttermall.enums.UserType;
import com.st1020.shuttermall.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;


import com.st1020.shuttermall.service.UserService;
import com.st1020.shuttermall.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    final private UserService userService;
    final private HttpServletRequest request;

    @Autowired
    public UserController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @PostMapping("/getAll")
    @Permission(UserType.ADMIN)
    public Result<List<User>> getAll() {
        return userService.findAll();
    }

    @PostMapping("/getUserInfo")
    @Permission(UserType.ADMIN)
    public Result<User> getUserInfo(@RequestBody User user) {
        return userService.findById(user.getId());
    }

    @PostMapping("/getMyUserInfo")
    public Result<User> getMyUserInfo() {
        User user = (User) request.getAttribute("user");
        return userService.findById(user.getId());
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/setUserType")
    @Permission(UserType.ADMIN)
    public Result<User> setAdmin(@RequestBody SetUserTypeRequest setUserTypeRequest) {
        return userService.setUserType(setUserTypeRequest.getUserId(), setUserTypeRequest.getUserType());
    }

    @PostMapping("/setUserInfo")
    @Permission(UserType.ADMIN)
    public Result<User> updateUserInfo(@RequestBody User userInfo) {
        return userService.setUserInfo(userInfo);
    }

    @PostMapping("/deleteUser")
    @Permission(UserType.ADMIN)
    public Result<User> deleteUser(@RequestBody User userInfo) {
        return userService.deleteUser(userInfo.getId());
    }
}
