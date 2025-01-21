package com.dariom.apt.view.component;

import com.dariom.apt.core.service.NotificationSettingsService;
import com.dariom.apt.view.component.notifcation.SuccessNotification;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.extern.slf4j.Slf4j;

import static com.dariom.apt.util.ValidationUtil.validateEmail;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.END;

@Slf4j
public class NotificationSettings extends VerticalLayout {

    private final NotificationSettingsService notificationSettingsService;

    private final TextField emailField = new TextField("Email to notify");

    public NotificationSettings(NotificationSettingsService notificationSettingsService) {
        this.notificationSettingsService = notificationSettingsService;

        setPadding(false);
        setWidthFull();

        var notificationEmail = notificationSettingsService.getNotificationEmail();
        emailField.setValue(notificationEmail);

        var saveButton = new Button("Save");
        saveButton.addClickListener(event -> updateEmail());

        // container
        var container = new HorizontalLayout(emailField, saveButton);
        container.setWidthFull();
        container.setAlignItems(END);
        container.setFlexGrow(2, emailField);
        container.setFlexGrow(1, saveButton);

        add(container);
    }

    private void updateEmail() {
        var notificationEmail = emailField.getValue();
        var emailValidation = validateEmail(notificationEmail);
        if (!emailValidation.isValid()) {
            emailField.setInvalid(true);
            emailField.setErrorMessage(emailValidation.message());
            return;
        }

        notificationSettingsService.updateNotificationEmail(notificationEmail);
        SuccessNotification.show("Email updated");
    }
}
