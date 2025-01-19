package com.dariom.apt.repository;

import com.dariom.apt.core.domain.PriceTracking;
import com.dariom.apt.repository.jpa.PriceTrackingJpaRepository;
import com.dariom.apt.repository.jpa.entity.PriceTrackingEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PriceTrackingRepository {

  private final PriceTrackingJpaRepository jpaRepository;

  public List<PriceTracking> getPriceTrackings() {
    return jpaRepository.findAll().stream()
        .map(this::toDomain)
        .toList();
  }

  public void delete(Long id) {
    jpaRepository.deleteById(id);
  }

  private PriceTracking toDomain(PriceTrackingEntity entity) {
    return new PriceTracking(
        entity.getId(),
        entity.getCreatedAt(),
        entity.getActive(),
        entity.getLink(),
        entity.getProductName(),
        entity.getDesiredPrice()
    );
  }
}
