package com.dariom.apt.job;

import com.dariom.apt.core.service.PriceCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
@ConditionalOnProperty(name = "price-checker.job.active", havingValue = "true")
public class PriceCheckerJob {

    private final PriceCheckService priceCheckService;

    @Scheduled(fixedDelayString = "${price-checker.job.refresh-interval-ms}")
    public void run() {
        priceCheckService.checkPrices();
    }
}
