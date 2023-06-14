package com.st1020.shuttermall.service;

import com.st1020.shuttermall.domain.User;
import com.st1020.shuttermall.domain.vo.LoginRequest;
import com.st1020.shuttermall.domain.vo.LoginResponse;
import com.st1020.shuttermall.enums.UserType;
import com.st1020.shuttermall.utils.Result;

import java.util.List;

public interface UserService {
    Result<List<User>> findAll();

    Result<User> findById(Long id);

    Result<LoginResponse> login(LoginRequest loginRequest);

    void resetPassword(String name, String email);

    Result<User> register(User user);

    Result<User> setUserType(Long userId, UserType userType);

    Result<User> setPassword(String name, String password);

    Result<User> setUserInfo(User user);

    Result<User> deleteUser(Long id);
}
