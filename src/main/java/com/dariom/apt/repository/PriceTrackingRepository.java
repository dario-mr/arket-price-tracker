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
    return jpaRepository.findAllByOrderByCreatedAtAsc().stream()
        .map(this::toDomain)
        .toList();
  }

  public void delete(Long id) {
    jpaRepository.deleteById(id);
  }

  public void save(PriceTracking priceTracking) {
    jpaRepository.save(toEntity(priceTracking));
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

  private PriceTrackingEntity toEntity(PriceTracking domain) {
    return PriceTrackingEntity.builder()
        .createdAt(domain.createdAt())
        .active(domain.active())
        .link(domain.link())
        .productName(domain.productName())
        .desiredPrice(domain.desiredPrice())
        .build();
  }
}
