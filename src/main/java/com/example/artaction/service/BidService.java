package com.example.artaction.service;

import com.example.artaction.domain.entity.Action;
import com.example.artaction.domain.entity.Bid;
import com.example.artaction.domain.entity.User;
import com.example.artaction.domain.repository.ActionRepository;
import com.example.artaction.domain.repository.BidRepository;
import com.example.artaction.domain.repository.UserRepository;
import com.example.artaction.dto.bid.CreateBidRequestDto;
import com.example.artaction.exception.action.NotFoundActionException;
import com.example.artaction.exception.bid.InvalidBidPriceException;
import com.example.artaction.exception.bid.NotFoundBidException;
import com.example.artaction.exception.user.NotAuthorizedUserException;
import com.example.artaction.exception.user.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;

    @Transactional
    public Bid create(CreateBidRequestDto requestDto) {
        User user = findUserById(requestDto.getUserId());
        if(!user.getUserType().isSeller()){
            throw new NotAuthorizedUserException("구매자 권한이 없습니다");
        }

        Action action = findActionById(requestDto.getActionId());

        long bidPrice = requestDto.getPrice();
        validateBidPrice(action, bidPrice);

        Bid bid = Bid.builder()
                .action(action)
                .user(user)
                .price(bidPrice)
                .bidTime(LocalDateTime.now())
                .build();

        return bidRepository.save(bid);
    }

    @Transactional(readOnly = true)
    public List<Bid> findByUser(Long userId) {
        User user = findUserById(userId);
        return bidRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundBidException("입찰 내역이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public List<Bid> findTop5ByAction(Long actionId) {
        Action action = findActionById(actionId);
        return bidRepository.findTop5ByActionOrderByBidTimeDesc(action)
                .orElseThrow(() -> new NotFoundBidException("입찰 내역이 존재하지 않습니다"));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("아이디와 일치하는 회원을 찾을 수 없습니다"));
    }

    private Action findActionById(Long actionId) {
        return actionRepository.findById(actionId)
                .orElseThrow(() -> new NotFoundActionException("아이디와 일치하는 경매를 찾을 수 없습니다"));
    }

    private void validateBidPrice(Action action, long bidPrice) {
        if (bidPrice <= action.getStartingPrice()) {
            throw new InvalidBidPriceException("입찰금액이 시작 금액보다 낮습니다.");
        }
        if (bidPrice <= action.getCurrentPrice()) {
            throw new IllegalArgumentException("입찰금액이 현재 가격보다 낮습니다");
        }
    }
}