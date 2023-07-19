package com.example.artaction.domain.repository;

import com.example.artaction.domain.entity.Action;
import com.example.artaction.domain.entity.Bid;
import com.example.artaction.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    Optional<Bid> findById(Long id);
    Optional<List<Bid>> findByUser(User user);
    @Query("SELECT b FROM Bid b WHERE b.action = :action ORDER BY b.bidTime DESC")
    Optional<List<Bid>> findTop5ByActionOrderByBidTimeDesc(@Param("action") Action action);
}
