package com.example.artaction.dto.bid;

import com.example.artaction.contant.ActionStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBidResponseDto {
    private long price;

    private LocalDateTime bidTime;

    private Long actionId;

    private ActionStatus actionStatus;
}
