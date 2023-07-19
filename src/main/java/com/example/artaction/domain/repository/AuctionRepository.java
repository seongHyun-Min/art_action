package com.example.artaction.domain.repository;

import com.example.artaction.contant.ActionStatus;
import com.example.artaction.domain.entity.Auction;
import com.example.artaction.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Optional<Auction> findById(Long id);

    Optional<List<Auction>> findByUser(User user);

    List<Auction> findByStatusAndStartTimeAfter(ActionStatus status, LocalDateTime StartTime);

    List<Auction> findByStatusAndEndTimeAfter(ActionStatus status, LocalDateTime EndTime);

}
