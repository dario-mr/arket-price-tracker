package com.dariom.apt.core.service;

import com.dariom.apt.core.domain.PriceTracking;
import com.dariom.apt.repository.PriceTrackingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceTrackingService {

    private final DocumentService documentService;
    private final PriceTrackingRepository priceTrackingRepository;

    public List<PriceTracking> getAllPriceTrackings() {
        return priceTrackingRepository.getAllPriceTrackings();
    }

    public List<PriceTracking> getActivePriceTrackings() {
        return priceTrackingRepository.getActivePriceTrackings();
    }

    public void delete(long id) {
        priceTrackingRepository.delete(id);
    }

    public void createPriceTracking(String link, BigDecimal desiredPrice) {
        var productName = getProductNameFromLink(link);
        var priceTracking = new PriceTracking(null, Instant.now(), true, link, productName, desiredPrice);

        priceTrackingRepository.save(priceTracking);
    }

    public void updateActive(long id, boolean active) {
        priceTrackingRepository.updateActiveById(id, active);
    }

    private String getProductNameFromLink(String link) {
        var page = documentService.getPage(link);
        return documentService.extractValue(page, "productName");
    }
}
