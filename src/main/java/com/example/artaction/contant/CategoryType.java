package com.example.artaction.contant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum CategoryType {
    PAINTING(0, "회화"),
    SCULPTURE(1, "조각"),
    PHOTOGRAPHY(2, "사진");

    private final Integer value;

    private final String typeName;

    public static CategoryType fromValue(Integer value) {
        for (CategoryType categoryType : CategoryType.values()) {
            if (Objects.equals(categoryType.value, value)) {
                return categoryType;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 값입니다.");
    }
}
