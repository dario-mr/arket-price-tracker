package com.dariom.apt.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;

public class CreatePriceTrackingEvent extends ComponentEvent<Button> {

  public CreatePriceTrackingEvent() {
    super(new Button(), false);
  }
}
