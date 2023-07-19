package com.example.artaction.dto.auction;

import com.example.artaction.contant.ActionStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionResponseDto {

    private String itemName;

    private ActionStatus actionStatus;

    private long price;

}
