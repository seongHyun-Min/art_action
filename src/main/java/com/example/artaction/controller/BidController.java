package com.example.artaction.controller;


import com.example.artaction.dto.bid.*;
import com.example.artaction.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping
    public ResponseEntity<Long> postBid(@Valid @RequestBody PostBidRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bidService.Post(requestDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserBidResponseDto>> getBidByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(bidService.findByUser(userId));
    }

    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<List<AuctionBidResponseDto>> getBidByActionId(@PathVariable Long auctionId) {
        return ResponseEntity.ok(bidService.findTop5ByAuction(auctionId));
    }
}
