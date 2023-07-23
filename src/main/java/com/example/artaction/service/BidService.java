package com.example.artaction.service;

import com.example.artaction.domain.entity.Auction;
import com.example.artaction.domain.entity.Bid;
import com.example.artaction.domain.entity.User;
import com.example.artaction.domain.repository.AuctionRepository;
import com.example.artaction.domain.repository.BidRepository;
import com.example.artaction.domain.repository.UserRepository;
import com.example.artaction.dto.bid.AuctionBidResponseDto;
import com.example.artaction.dto.bid.PostBidRequestDto;
import com.example.artaction.dto.bid.UserBidResponseDto;
import com.example.artaction.exception.auction.NotFoundAuctionException;
import com.example.artaction.exception.bid.InvalidBidException;
import com.example.artaction.exception.bid.NotSaveBidException;
import com.example.artaction.exception.user.NotAuthorizedUserException;
import com.example.artaction.exception.user.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long Post(PostBidRequestDto requestDto) {
        User user = findUserById(requestDto.getUserId());

        if (!user.getUserType().isBuyer()) {
            throw new NotAuthorizedUserException("구매자 권한이 없습니다");
        }

        Auction auction = findActionById(requestDto.getAuctionId());
        validateBidPrice(auction, requestDto.getPrice());

        Optional<Bid> existedBid = bidRepository.findByAuctionAndUser(auction, user);

        if (existedBid.isPresent()) {
            Bid UpdateBid = requestDto.toUpdateEntity(existedBid.get().getId(), user, auction);
            try {
                return bidRepository.save(UpdateBid).getId();
            } catch (NotSaveBidException e) {
                throw new NotSaveBidException("입찰 갱신에 실패 하였습니다");
            }
        } else {
            Bid bid = requestDto.toEntity(user, auction);
            try {
                return bidRepository.save(bid).getId();
            } catch (NotSaveBidException e) {
                throw new NotSaveBidException("입찰 등록에 실패 하였습니다");
            }
        }
    }

    @Transactional(readOnly = true)
    public List<UserBidResponseDto> findByUser(Long userId) {
        User user = findUserById(userId);
        return bidRepository.findByUser(user)
                .stream()
                .map(Bid::fromUser)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AuctionBidResponseDto> findTop5ByAuction(Long actionId) {
        Auction auction = findActionById(actionId);
        return bidRepository.findTop5ByAuctionOrderByBidTimeDesc(auction)
                .stream()
                .map(Bid::fromAuction)
                .toList();
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("아이디와 일치하는 회원을 찾을 수 없습니다"));
    }

    private Auction findActionById(Long actionId) {
        return auctionRepository.findById(actionId)
                .orElseThrow(() -> new NotFoundAuctionException("아이디와 일치하는 경매를 찾을 수 없습니다"));
    }

    private void validateBidPrice(Auction auction, long bidPrice) {
        if (!auction.getStatus().isStart()) {
            throw new InvalidBidException("경매가 진행중이 아닙니다.");
        }
        if (bidPrice <= auction.getStartingPrice()) {
            throw new InvalidBidException("입찰금액이 시작 금액보다 낮습니다.");
        }
        if (bidPrice <= auction.getCurrentPrice()) {
            throw new InvalidBidException("입찰금액이 현재 가격보다 낮습니다");
        }
    }
}