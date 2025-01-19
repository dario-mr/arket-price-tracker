package com.dariom.apt.core.service;

import com.dariom.apt.core.domain.PriceTracking;
import com.dariom.apt.repository.PriceTrackingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
}
