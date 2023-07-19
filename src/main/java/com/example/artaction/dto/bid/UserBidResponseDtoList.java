package com.example.artaction.dto.bid;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBidResponseDtoList {
    private List<UserBidResponseDto> bids;

}
