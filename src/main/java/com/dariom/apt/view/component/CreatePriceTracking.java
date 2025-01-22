package com.dariom.apt.view.component;

import com.dariom.apt.core.service.PriceTrackingService;
import com.dariom.apt.event.CreatePriceTrackingEvent;
import com.dariom.apt.view.component.notifcation.Toast;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import static com.dariom.apt.util.ValidationUtil.validateLink;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.END;

@Slf4j
public class CreatePriceTracking extends VerticalLayout {

    private final PriceTrackingService priceTrackingService;

    private final TextField linkField = new TextField("Link");
    private final NumberField priceField = new NumberField("Desired Price");

    public CreatePriceTracking(PriceTrackingService priceTrackingService) {
        this.priceTrackingService = priceTrackingService;

        setPadding(false);
        setSpacing(false);
        setWidthFull();

        // fields
        linkField.setWidthFull();
        var trackButton = new Button("Track");
        trackButton.addClickListener(event -> createTracking(linkField.getValue(), priceField.getValue()));

        // container (price + track)
        var secondRow = new HorizontalLayout(priceField, trackButton);
        secondRow.setWidthFull();
        secondRow.setAlignItems(END);
        secondRow.setFlexGrow(2, priceField);
        secondRow.setFlexGrow(1, trackButton);

        add(linkField, secondRow);
    }

    private void createTracking(String link, Double desiredPrice) {
        var linkValidation = validateLink(link);
        if (!linkValidation.isValid()) {
            linkField.setInvalid(true);
            linkField.setErrorMessage(linkValidation.message());
            return;
        }
        if (desiredPrice == null || desiredPrice < 0) {
            priceField.setInvalid(true);
            priceField.setErrorMessage("Price must be a positive number");
            return;
        }

        var desiredPriceBigDecimal = new BigDecimal(desiredPrice);
        try {
            priceTrackingService.createPriceTracking(link, desiredPriceBigDecimal);
        } catch (Exception e) {
            log.error("Error creating price tracking", e);
            Toast.error("Error creating price tracking: " + e.getMessage());
            return;
        }

        linkField.clear();
        priceField.clear();
        Toast.success("Tracking created!");
        ComponentUtil.fireEvent(UI.getCurrent(), new CreatePriceTrackingEvent());
    }
}
