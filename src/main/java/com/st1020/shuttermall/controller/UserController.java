package com.st1020.shuttermall.controller;

import com.st1020.shuttermall.domain.vo.LoginRequest;
import com.st1020.shuttermall.domain.vo.LoginResponse;
import com.st1020.shuttermall.domain.vo.SetUserTypeRequest;
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
    public Result<List<User>> getAll() {
        if (((User) request.getAttribute("user")).isAdmin()) {
            return userService.findAll();
        } else {
            return new Result<>("权限不足！");
        }
    }

    @PostMapping("/getUserInfo")
    public Result<User> getUserInfo(@RequestBody User user) {
        if (((User) request.getAttribute("user")).isAdmin()) {
            return userService.findById(user.getId());
        } else {
            return new Result<>("权限不足！");
        }
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
    public Result<User> setAdmin(@RequestBody SetUserTypeRequest setUserTypeRequest) {
        if (((User) request.getAttribute("user")).isAdmin()) {
            return userService.setUserType(setUserTypeRequest.getUserId(), setUserTypeRequest.getUserType());
        } else {
            return new Result<>("权限不足！");
        }
    }

    @PostMapping("/setUserInfo")
    public Result<User> updateUserInfo(@RequestBody User userInfo) {
        User user = (User) request.getAttribute("user");
        if (user.isAdmin()) {
            return userService.setUserInfo(userInfo);
        } else {
            return new Result<>("权限不足！");
        }
    }

    @PostMapping("/deleteUser")
    public Result<User> deleteUser(@RequestBody User userInfo) {
        User user = (User) request.getAttribute("user");
        if (user.isAdmin()) {
            return userService.deleteUser(userInfo.getId());
        } else {
            return new Result<>("权限不足！");
        }
    }
}
