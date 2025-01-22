package com.dariom.apt.repository;

import com.dariom.apt.core.domain.PriceTracking;
import com.dariom.apt.repository.jpa.PriceTrackingJpaRepository;
import com.dariom.apt.repository.jpa.entity.PriceTrackingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PriceTrackingRepository {

    private final PriceTrackingJpaRepository jpaRepository;

    public List<PriceTracking> getAllPriceTrackings() {
        return jpaRepository.findAllByOrderByCreatedAtAsc().stream()
                .map(this::toDomain)
                .toList();
    }

    public List<PriceTracking> getActivePriceTrackings() {
        return jpaRepository.findAllByActiveIsTrueOrderByCreatedAtAsc().stream()
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
        return PriceTracking.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .active(entity.getActive())
                .link(entity.getLink())
                .productName(entity.getProductName())
                .desiredPrice(entity.getDesiredPrice())
                .build();
    }

    private PriceTrackingEntity toEntity(PriceTracking domain) {
        return PriceTrackingEntity.builder()
                .id(domain.getId())
                .createdAt(domain.getCreatedAt())
                .active(domain.getActive())
                .link(domain.getLink())
                .productName(domain.getProductName())
                .desiredPrice(domain.getDesiredPrice())
                .build();
    }
}
