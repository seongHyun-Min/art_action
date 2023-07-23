package com.example.artaction.dto.bid;

import com.example.artaction.domain.entity.Auction;
import com.example.artaction.domain.entity.Bid;
import com.example.artaction.domain.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostBidRequestDto {
    @NotNull
    private Long auctionId;
    @NotNull
    private Long userId;
    private long price;

    public Bid toEntity(User user, Auction auction) {
        return Bid.builder()
                .price(price)
                .bidTime(LocalDateTime.now())
                .user(user)
                .auction(auction)
                .build();
    }

    public Bid toUpdateEntity(Long bidId, User user, Auction auction) {
        return Bid.builder()
                .id(bidId)
                .price(price)
                .bidTime(LocalDateTime.now())
                .user(user)
                .auction(auction)
                .build();
    }
}
