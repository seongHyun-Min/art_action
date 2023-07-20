package com.example.artaction.dto.bid;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBidRequestDto {
    @NotNull
    private Long auctionId;
    @NotNull
    private Long userId;
    private long price;
}
