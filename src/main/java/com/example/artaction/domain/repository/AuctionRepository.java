package com.example.artaction.domain.repository;

import com.example.artaction.contant.AuctionStatus;
import com.example.artaction.domain.entity.ArtWork;
import com.example.artaction.domain.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Optional<Auction> findById(Long id);

    List<Auction> findByArtWork(ArtWork artWork);

    List<Auction> findByStatusAndStartTimeAfter(AuctionStatus status, LocalDateTime StartTime);

    List<Auction> findByStatusAndEndTimeAfter(AuctionStatus status, LocalDateTime EndTime);

}
