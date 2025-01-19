package com.dariom.apt.view.route;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;

import com.dariom.apt.core.service.PriceTrackingService;
import com.dariom.apt.view.component.BaseContainer;
import com.dariom.apt.view.component.PriceTrackingGrid;
import com.vaadin.flow.component.html.H1;
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

  @PostConstruct
  public void init() {
    setHeightFull();
    setAlignItems(CENTER);
    setPadding(false);

    // title
    var title = new H1("Arket Price Tracker");
    title.getStyle()
        .set("font-size", "var(--lumo-font-size-l)")
        .set("margin", "var(--lumo-space-m) var(--lumo-space-l)");
    title.addClassNames("headline");

    // main container
    var container = new BaseContainer(
        title,
        // TODO create tracking component
        new PriceTrackingGrid(priceTrackingService)
    );

    add(container);
  }
}
