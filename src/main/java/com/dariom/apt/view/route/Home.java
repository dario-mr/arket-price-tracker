package com.dariom.apt.view.route;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;

import com.dariom.apt.view.component.BaseContainer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Arket Price Tracker")
public class Home extends VerticalLayout {

  public Home() {
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
        title
    );

    add(container);
  }
}
