package com.example.artaction.contant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum UserType {
    BUYER(0, "구매자"),
    SELLER(1, "판매자");

    private final Integer value;

    private final String typeName;

    public static UserType fromValue(Integer value) {
        for (UserType userType : UserType.values()) {
            if (Objects.equals(userType.value, value)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 값입니다.");
    }

    public boolean isSeller() {
        return this == SELLER;
    }
}
    

