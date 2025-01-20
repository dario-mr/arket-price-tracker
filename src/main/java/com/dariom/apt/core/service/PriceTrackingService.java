package com.dariom.apt.core.service;

import com.dariom.apt.core.domain.PriceTracking;
import com.dariom.apt.repository.PriceTrackingRepository;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceTrackingService {

  private final PriceTrackingRepository priceTrackingRepository;

  public List<PriceTracking> getPriceTrackings() {
    return priceTrackingRepository.getPriceTrackings();
  }

  public void delete(long id) {
    priceTrackingRepository.delete(id);
  }

  public void createPriceTracking(String link, BigDecimal desiredPrice) throws IOException {
    var productName = getProductNameFromLink(link);
    var priceTracking = new PriceTracking(null, Instant.now(), true, link, "productName", desiredPrice);

    priceTrackingRepository.save(priceTracking);
  }

  private String getProductNameFromLink(String link) throws IOException {
    var linkPage = Jsoup.connect(link).userAgent("Chrome").get(); // TODO why 403 all of a sudden??
    var productDetails = linkPage.getElementsByClass("producttile-details").getFirst();
    return productDetails.getElementsByClass("productName").getFirst().text();
  }
}
