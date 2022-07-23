package com.mysite.sbb.util;

import lombok.Getter;

// 열거형으로 생성 (열거형은 변수에 대해 더 정확한 표시를 하는것으로 어디든지 사용 가능하다.)
@Getter
public enum UserRole {
    // UserRole에서 받은값이 ROLE_ADMIN 이면 ADMIN
    ADMIN("ROLE_ADMIN"),
    // UserRole에서 받은값이 ROLE_USER 이면 USER
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
