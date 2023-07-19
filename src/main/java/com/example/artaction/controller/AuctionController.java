package com.example.artaction.controller;

import com.example.artaction.domain.entity.Auction;
import com.example.artaction.dto.auction.AuctionResponseDto;
import com.example.artaction.dto.auction.AuctionResponseDtoList;
import com.example.artaction.dto.auction.PostAuctionRequestDto;
import com.example.artaction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auctions")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping()
    public ResponseEntity<Long> postAuction(@Valid @RequestBody PostAuctionRequestDto requestDto) {
        Auction auction = auctionService.post(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(auction.getId());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<AuctionResponseDtoList> getAuctionByUserId(@PathVariable Long userId) {
        List<Auction> byUser = auctionService.findByUser(userId);

        List<AuctionResponseDto> responseDtoList = convertResponseDtoList(byUser);

        AuctionResponseDtoList auctionResponseDtoList = new AuctionResponseDtoList(responseDtoList);

        return ResponseEntity.ok(auctionResponseDtoList);
    }

    private static List<AuctionResponseDto> convertResponseDtoList(List<Auction> byUser) {
        return byUser.stream()
                .map(auction -> AuctionResponseDto.builder()
                        .actionStatus(auction.getStatus())
                        .price(auction.getCurrentPrice())
                        .itemName(auction.getArtWork().getName())
                        .build())
                .collect(Collectors.toList());
    }
}
