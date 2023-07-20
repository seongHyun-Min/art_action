package com.example.artaction.dto.bid;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionBidResponseDto {
    private String userName;

    private long price;

    private LocalDateTime bidTime;
}
