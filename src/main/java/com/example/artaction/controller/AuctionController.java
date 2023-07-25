package com.example.artaction.controller;

import com.example.artaction.dto.auction.AuctionResponseDto;
import com.example.artaction.dto.auction.PostAuctionRequestDto;
import com.example.artaction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auctions")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping()
    public ResponseEntity<Long> postAuction(@Valid @RequestBody PostAuctionRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auctionService.post(requestDto));
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionResponseDto> getAuction(@PathVariable Long auctionId) {
        return ResponseEntity.ok(auctionService.findById(auctionId));
    }

    @GetMapping("/artwork/{artWorkId}")
    public ResponseEntity<List<AuctionResponseDto>> getAuctionByArtWorkId(@PathVariable Long artWorkId) {
        List<AuctionResponseDto> byArtWork = auctionService.findByArtWork(artWorkId);
        return ResponseEntity.ok(byArtWork);
    }
}
