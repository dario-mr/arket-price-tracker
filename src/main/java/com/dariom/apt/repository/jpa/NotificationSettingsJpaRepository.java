package com.dariom.apt.repository.jpa;

import com.dariom.apt.repository.jpa.entity.NotificationSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NotificationSettingsJpaRepository extends JpaRepository<NotificationSettingsEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE NotificationSettingsEntity n SET n.email = :email WHERE n.id = :id")
    void updateEmailById(@Param("id") Long id, @Param("email") String email);

}
