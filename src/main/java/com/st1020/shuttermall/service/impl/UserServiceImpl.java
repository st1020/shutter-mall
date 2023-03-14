package com.st1020.shuttermall.service.impl;

import com.st1020.shuttermall.domain.User;
import com.st1020.shuttermall.domain.vo.LoginRequest;
import com.st1020.shuttermall.domain.vo.LoginResponse;
import com.st1020.shuttermall.enums.UserType;
import com.st1020.shuttermall.service.UserService;
import com.st1020.shuttermall.utils.JwtUtil;
import com.st1020.shuttermall.repository.UserRepository;
import com.st1020.shuttermall.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public Result<List<User>> findAll() {
        return new Result<>(userRepository.findAll());
    }

    @Override
    public Result<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(Result::new).orElseGet(() -> new Result<>("账户不存在！"));
    }

    @Override
    public Result<LoginResponse> login(LoginRequest loginRequest) {
        User user = this.userRepository.findByName(loginRequest.getName());
        if (user == null) {
            return new Result<>("账户不存在！");
        }
        if (user.getPassword().equals(loginRequest.getPassword())) {
            return new Result<>(new LoginResponse(JwtUtil.createToken(user)));
        } else {
            return new Result<>("密码错误！");
        }
    }

    @Override
    public Result<User> register(User user) {
        user.setType(UserType.USER);
        if (userRepository.findByNameOrEmail(user.getName(), user.getEmail()) == null) {
            return new Result<>(userRepository.saveAndFlush(user));
        } else {
            return new Result<>("用户名或邮箱已经存在！");
        }
    }

    @Override
    public Result<User> setUserType(Long userId, UserType userType) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Result<>("用户不存在！");
        } else {
            user.get().setType(userType);
            return new Result<>(userRepository.saveAndFlush(user.get()));
        }
    }

    @Override
    public Result<User> setUserInfo(User user) {
        if (userRepository.findById(user.getId()).isEmpty()) {
            return new Result<>("用户不存在！");
        } else {
            return new Result<>(userRepository.saveAndFlush(user));
        }
    }

    @Override
    public Result<User> deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new Result<>("用户不存在！");
        } else {
            userRepository.deleteById(id);
            return new Result<>(user.get());
        }
    }
}
