package com.example.artaction.dto.auction;

import com.example.artaction.contant.AuctionStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionResponseDto {

    private String itemName;

    private AuctionStatus auctionStatus;

    private long price;

}
