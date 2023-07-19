package com.example.artaction.dto.auction;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionResponseDtoList {
    List<AuctionResponseDto> auctions;
}
