package com.example.artaction.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RedisCacheService {
    @CachePut(key = "#auctionId", value = "bidPrice")
    public long updateBidPriceToRedis(Long auctionId, long price) {
        log.info("경매 ID {}에 대한 입찰 가격을 {}로 업데이트 중", auctionId, price);
        return price;
    }

    @Cacheable(key = "#auctionId", value = "bidPrice")
    public long getCurrentBidPrice(Long auctionId) {
        //캐시에 없으면 0L 리턴 0L 리턴시 옥션 서비스에서 DB조회
        return 0L;
    }
}

