package com.dariom.apt.view.route;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;

import com.dariom.apt.core.service.NotificationSettingsService;
import com.dariom.apt.core.service.PriceTrackingService;
import com.dariom.apt.view.component.BaseContainer;
import com.dariom.apt.view.component.CreatePriceTracking;
import com.dariom.apt.view.component.Headline;
import com.dariom.apt.view.component.NotificationSettings;
import com.dariom.apt.view.component.PriceTrackingGrid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Route("")
@PageTitle("Arket Price Tracker")
@RequiredArgsConstructor
public class Home extends VerticalLayout {

  private final PriceTrackingService priceTrackingService;
  private final NotificationSettingsService notificationSettingsService;

  @PostConstruct
  public void init() {
    setHeightFull();
    setAlignItems(CENTER);
    setPadding(false);

    // main container
    var container = new BaseContainer(
        new Headline(),
        new NotificationSettings(notificationSettingsService),
        new CreatePriceTracking(priceTrackingService),
        new PriceTrackingGrid(priceTrackingService)
    );

    add(container);
  }
}
