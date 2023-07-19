package com.example.artaction.domain.entity;


import com.example.artaction.contant.ActionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private Long id;

    @Column(name = "auction_starting_price")
    private long startingPrice;

    @Column(name = "auction_current_price")
    private long currentPrice;

    @Column(name = "auction_start_time")
    private LocalDateTime startTime;

    @Column(name = "auction_end_time")
    private LocalDateTime endTime;

    @Column(name = "auction_status")
    @Enumerated(EnumType.STRING)
    private ActionStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_work_id")
    private ArtWork artWork;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "auction")
    private List<Bid> bids = new ArrayList<>();
}
