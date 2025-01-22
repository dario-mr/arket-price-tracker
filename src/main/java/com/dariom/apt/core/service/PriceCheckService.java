package com.dariom.apt.core.service;

import com.dariom.apt.core.domain.PriceTracking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceCheckService {

    private final EmailService emailService;
    private final DocumentService documentService;
    private final PriceTrackingService priceTrackingService;

    public void checkPrices() {
        try {
            var trackings = priceTrackingService.getActivePriceTrackings();
            trackings.forEach(this::checkPrice);
        } catch (Exception ex) {
            log.error("Error checking prices", ex);
            emailService.send("Error checking prices", ex.getMessage());
        }
    }

    private void checkPrice(PriceTracking tracking) {
        var link = tracking.getLink();
        var desiredPrice = tracking.getDesiredPrice();
        var productName = tracking.getProductName();
        log.info("Checking Arket item {} with desired price {}...", link, desiredPrice);

        var currentPrice = extractPrice(link);
        if (currentPrice.compareTo(desiredPrice) <= 0) {
            log.info("Current price {} is lower than desired price {}!", currentPrice, desiredPrice);
            emailService.send(
                    format("Arket: \"%s\" price dropped!", productName),
                    format("\"%s\" price [%s] fell below desired price [%s].\n\nLink: %s", productName, currentPrice, desiredPrice, link));
        }
    }

    private BigDecimal extractPrice(String link) {
        var page = documentService.getPage(link);
        var price = documentService.extractValue(page, "price");

        return new BigDecimal(price);
    }

}
