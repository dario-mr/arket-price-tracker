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

    public void updateActiveById(long id, boolean active) {
        jpaRepository.updateActiveById(id, active);
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
                .id(domain.id())
                .createdAt(domain.createdAt())
                .active(domain.active())
                .link(domain.link())
                .productName(domain.productName())
                .desiredPrice(domain.desiredPrice())
                .build();
    }
}
