package com.example.artaction.dto.bid;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionBidResponseDtoList {
    List<AuctionBidResponseDto> bids;

}
