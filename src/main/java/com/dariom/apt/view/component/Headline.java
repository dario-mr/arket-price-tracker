package com.dariom.apt.view.component;

import com.vaadin.flow.component.html.H1;

public class Headline extends H1 {

  public Headline() {
    setText("Arket Price Tracker");
    getStyle()
        .set("font-size", "var(--lumo-font-size-xl)")
        .set("margin", "var(--lumo-space-s) 0");
    addClassNames("headline");
  }
}
