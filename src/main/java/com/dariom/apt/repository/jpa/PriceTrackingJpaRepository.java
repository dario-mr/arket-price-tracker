package com.dariom.apt.repository.jpa;

import com.dariom.apt.repository.jpa.entity.PriceTrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceTrackingJpaRepository extends JpaRepository<PriceTrackingEntity, Long> {

    List<PriceTrackingEntity> findAllByOrderByCreatedAtAsc();

    List<PriceTrackingEntity> findAllByActiveIsTrueOrderByCreatedAtAsc();

}
