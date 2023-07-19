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
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id")
    private Long id;

    @Column(name = "actiond_starting_price")
    private long startingPrice;

    @Column(name = "actions_current_price")
    private long CurrentPrice;

    @Column(name = "actions_start_time")
    private LocalDateTime startTime;

    @Column(name = "actions_end_time")
    private LocalDateTime endTime;

    @Column(name = "action_status")
    @Enumerated(EnumType.STRING)
    private ActionStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_work_id")
    private ArtWork artWork;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "action")
    private List<Bid> bids = new ArrayList<>();
}
