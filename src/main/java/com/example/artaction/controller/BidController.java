package com.example.artaction.controller;


import com.example.artaction.domain.entity.Bid;
import com.example.artaction.dto.bid.*;
import com.example.artaction.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping
    public ResponseEntity<Long> CreateBid(@Valid @RequestBody CreateBidRequestDto requestDto) {
        Bid bid = bidService.create(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(bid.getId());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserBidResponseDtoList> getBidByUserId(@PathVariable Long userId) {

        List<Bid> byUser = bidService.findByUser(userId);

        List<UserBidResponseDto> bidResponseDtoList = userConvertResponseDtoList(byUser);

        UserBidResponseDtoList responseDtoList = new UserBidResponseDtoList(bidResponseDtoList);

        return ResponseEntity.ok(responseDtoList);
    }

    @GetMapping("/action/{actionId}")
    public ResponseEntity<ActionBidResponseDtoList> getBidByActionId(@PathVariable Long actionId) {

        List<Bid> byAction = bidService.findTop5ByAction(actionId);

        List<ActionBidResponseDto> bidResponseDtoList = ActionConvertResponseDtoList(byAction);

        ActionBidResponseDtoList responseDtoList = new ActionBidResponseDtoList(bidResponseDtoList);

        return ResponseEntity.ok(responseDtoList);
    }

    private static List<UserBidResponseDto> userConvertResponseDtoList(List<Bid> by) {
        return by.stream()
                .map(bid -> UserBidResponseDto.builder()
                        .price(bid.getPrice())
                        .bidTime(bid.getBidTime())
                        .actionId(bid.getAction().getId())
                        .actionStatus(bid.getAction().getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    private static List<ActionBidResponseDto> ActionConvertResponseDtoList(List<Bid> by) {
        return by.stream()
                .map(bid -> ActionBidResponseDto.builder()
                        .price(bid.getPrice())
                        .bidTime(bid.getBidTime())
                        .userName(bid.getUser().getName())
                        .build())
                .collect(Collectors.toList());
    }
}
