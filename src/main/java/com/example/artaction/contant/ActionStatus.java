package com.example.artaction.contant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum ActionStatus {
    PREPARE(0, "준비"),
    START(1, "시작"),
    EXIT(2, "종료");

    private final Integer value;

    private final String typeName;

    public static ActionStatus fromValue(Integer value) {
        for (ActionStatus actionStatus : ActionStatus.values()) {
            if (Objects.equals(actionStatus.value, value)) {
                return actionStatus;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 값입니다");
    }


}
