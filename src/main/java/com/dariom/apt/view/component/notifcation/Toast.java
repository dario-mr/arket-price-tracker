package com.dariom.apt.view.component.notifcation;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

import static com.vaadin.flow.component.notification.Notification.Position.TOP_CENTER;
import static com.vaadin.flow.component.notification.NotificationVariant.*;

public class Toast extends Notification {

    private Toast(String text, NotificationVariant variant) {
        setText(text);
        addThemeVariants(variant);
        setDuration(3_000);
        setPosition(TOP_CENTER);
    }

    public static void success(String text) {
        var notification = new Toast(text, LUMO_SUCCESS);
        notification.open();
    }

    public static void warn(String text) {
        var notification = new Toast(text, LUMO_WARNING);
        notification.open();
    }

    public static void error(String text) {
        var notification = new Toast(text, LUMO_ERROR);
        notification.open();
    }
}
