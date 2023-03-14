package com.st1020.shuttermall.domain.vo;

import com.st1020.shuttermall.enums.UserType;

public class SetUserTypeRequest {
    private Long userId;
    private UserType userType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
