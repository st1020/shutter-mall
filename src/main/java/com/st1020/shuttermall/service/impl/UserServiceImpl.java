package com.st1020.shuttermall.service.impl;

import com.st1020.shuttermall.domain.User;
import com.st1020.shuttermall.domain.vo.LoginRequest;
import com.st1020.shuttermall.domain.vo.LoginResponse;
import com.st1020.shuttermall.enums.UserType;
import com.st1020.shuttermall.exception.BusinessException;
import com.st1020.shuttermall.service.MailService;
import com.st1020.shuttermall.service.UserService;
import com.st1020.shuttermall.utils.JwtUtil;
import com.st1020.shuttermall.repository.UserRepository;
import com.st1020.shuttermall.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private MailService mailService;


    @Override
    @Transactional(readOnly = true)
    public Result<List<User>> findAll() {
        return new Result<>(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Result<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new BusinessException("账户不存在！");
        } else {
            return new Result<>(user.get());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Result<LoginResponse> login(LoginRequest loginRequest) {
        Optional<User> user = this.userRepository.findByName(loginRequest.getName());
        if (user.isEmpty()) {
            throw new BusinessException("账户不存在！");
        } else if (user.get().getPassword().equals(loginRequest.getPassword())) {
            return new Result<>(new LoginResponse(JwtUtil.createToken(user.get())));
        } else {
            throw new BusinessException("密码错误！");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void resetPassword(String name, String email) {
        Optional<User> user = this.userRepository.findByName(name);
        if (user.isEmpty()) {
            throw new BusinessException("账户不存在！");
        } else if (Objects.equals(user.get().getEmail(), email)) {
            String token = JwtUtil.createToken(user.get());
            mailService.sendMail(
                    email,
                    "[快门商城]重置密码",
                    "http://localhost:5174/user/setPassword/" +
                            Base64.getUrlEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8))
            );
        } else {
            throw  new BusinessException("用户名或邮箱错误！");
        }
    }

    @Override
    @Transactional
    public Result<User> register(User user) {
        if (userRepository.findByNameOrEmail(user.getName(), user.getEmail()).isEmpty()) {
            user.setType(UserType.USER);
            return new Result<>(userRepository.saveAndFlush(user));
        } else {
            throw new BusinessException("用户名或邮箱已经存在！");
        }
    }

    @Override
    @Transactional
    public Result<User> setUserType(Long userId, UserType userType) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BusinessException("用户不存在！");
        } else {
            user.get().setType(userType);
            return new Result<>(userRepository.saveAndFlush(user.get()));
        }
    }

    @Override
    public Result<User> setPassword(String name, String password) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isEmpty()) {
            throw new BusinessException("用户不存在！");
        } else {
            user.get().setPassword(password);
            return new Result<>(userRepository.saveAndFlush(user.get()));
        }
    }

    @Override
    @Transactional
    public Result<User> setUserInfo(User user) {
        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new BusinessException("用户不存在！");
        } else {
            return new Result<>(userRepository.saveAndFlush(user));
        }
    }

    @Override
    @Transactional
    public Result<User> deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new BusinessException("用户不存在！");
        } else {
            userRepository.deleteById(id);
            return new Result<>(user.get());
        }
    }
}
