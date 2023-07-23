package com.example.artaction.contant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum AuctionStatus {
    PREPARE(0, "준비"),
    START(1, "시작"),
    END(2, "종료"),
    FAIL(3, "입찰 실패");


    private final Integer value;

    private final String typeName;

    public static AuctionStatus fromValue(Integer value) {
        for (AuctionStatus auctionStatus : AuctionStatus.values()) {
            if (Objects.equals(auctionStatus.value, value)) {
                return auctionStatus;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 값입니다");
    }

    public boolean isStart() {
        return this == START;
    }


}
