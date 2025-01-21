package com.dariom.apt.core.service;

import com.dariom.apt.repository.NotificationSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSettingsService {

    private final NotificationSettingsRepository notificationSettingsRepository;

    public String getNotificationEmail() {
        return notificationSettingsRepository.getNotificationEmail();
    }

    public void updateNotificationEmail(String notificationEmail) {
        var id = notificationSettingsRepository.getNotificationSettingId();
        notificationSettingsRepository.updateNotificationEmail(id, notificationEmail);
    }
}
