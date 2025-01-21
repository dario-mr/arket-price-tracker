package com.dariom.apt.repository.jpa;

import com.dariom.apt.repository.jpa.entity.PriceTrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PriceTrackingJpaRepository extends JpaRepository<PriceTrackingEntity, Long> {

    List<PriceTrackingEntity> findAllByOrderByCreatedAtAsc();

    List<PriceTrackingEntity> findAllByActiveIsTrueOrderByCreatedAtAsc();

    @Modifying
    @Transactional
    @Query("UPDATE PriceTrackingEntity p SET p.active = :active WHERE p.id = :id")
    void updateActiveById(@Param("id") Long id, @Param("active") boolean active);

}
