package com.dariom.apt.repository;

import com.dariom.apt.repository.jpa.NotificationSettingsJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationSettingsRepository {

    private final NotificationSettingsJpaRepository jpaRepository;

    public String getNotificationEmail() {
        return jpaRepository.findAll().getFirst().getEmail();
    }

    public long getNotificationSettingId() {
        return jpaRepository.findAll().getFirst().getId();
    }

    public void updateNotificationEmail(long id, String email) {
        jpaRepository.updateEmailById(id, email);
    }
}
