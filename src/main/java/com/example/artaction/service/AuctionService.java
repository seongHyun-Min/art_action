package com.example.artaction.service;

import com.example.artaction.contant.ActionStatus;
import com.example.artaction.domain.entity.Auction;
import com.example.artaction.domain.entity.ArtWork;
import com.example.artaction.domain.repository.AuctionRepository;
import com.example.artaction.domain.repository.ArtWorkRepository;
import com.example.artaction.domain.repository.BidRepository;
import com.example.artaction.dto.auction.AuctionResponseDto;
import com.example.artaction.dto.auction.PostAuctionRequestDto;
import com.example.artaction.exception.auction.NotSaveAuctionException;
import com.example.artaction.exception.artwork.NotFoundArtWorkException;
import com.example.artaction.exception.artwork.NotSaveArtWorkException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final ArtWorkRepository artWorkRepository;
    private final BidRepository bidRepository;

    @Transactional
    public Long post(PostAuctionRequestDto requestDto) {
        ArtWork artWork = findArtWorkById(requestDto.getArtWorkId());
        Auction auction = requestDto.toEntity(artWork);
        try {
            return auctionRepository.save(auction).getId();
        } catch (NotSaveArtWorkException e) {
            throw new NotSaveAuctionException("경매 등록에 실패 하였습니다");
        }
    }

    @Transactional(readOnly = true)
    public List<AuctionResponseDto> findByArtWork(Long artWorkId) {
        ArtWork artWork = findByArtWorkId(artWorkId);
        return auctionRepository.findByArtWork(artWork)
                .stream()
                .map(Auction::from)
                .toList();
    }

    @Scheduled(fixedRate = 3600000) // 1시간마다 실행 (3600000ms = 1시간)
    public void updateActionStatus() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<Auction> startAuctions = auctionRepository.findByStatusAndStartTimeAfter(ActionStatus.PREPARE,
                currentTime);
        List<Auction> endAuctions = auctionRepository.findByStatusAndEndTimeAfter(ActionStatus.START, currentTime);

        for (Auction auction : startAuctions) {
            Auction startAuction = getBuild(auction, ActionStatus.START, auction.getCurrentPrice());
            auctionRepository.save(startAuction);
        }

        for (Auction auction : endAuctions) {
            Optional<Integer> maxPrice = bidRepository.findTop1ByAuctionOrderByPriceDesc(auction);
            ActionStatus actionStatus = maxPrice.isPresent() ? ActionStatus.END : ActionStatus.FAIL;
            long endPrice = maxPrice.orElse(0).longValue();

            Auction endAuction = getBuild(auction, actionStatus, endPrice);
            auctionRepository.save(endAuction);
        }
    }

    private static Auction getBuild(Auction auction, ActionStatus status, long price) {
        return Auction.builder()
                .artWork(auction.getArtWork())
                .startTime(auction.getStartTime())
                .endTime(auction.getEndTime())
                .startingPrice(auction.getStartingPrice())
                .currentPrice(price)
                .status(status)
                .build();
    }

    private ArtWork findByArtWorkId(Long artWorkId) {
        return artWorkRepository.findById(artWorkId)
                .orElseThrow(() -> new NotFoundArtWorkException("아이디와 일치하는 물품을 찾을 수 없습니다"));
    }

    private ArtWork findArtWorkById(Long artWorkId) {
        return artWorkRepository.findById(artWorkId)
                .orElseThrow(() -> new NotFoundArtWorkException("아이디와 일치하는 물품을 찾을 수 없습니다"));
    }
}
