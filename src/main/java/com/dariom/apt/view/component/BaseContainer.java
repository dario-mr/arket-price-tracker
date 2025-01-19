package com.dariom.apt.view.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class BaseContainer extends VerticalLayout {

  private static final String MAX_WINDOW_WIDTH = "1000px";

  public BaseContainer(Component... children) {
    setHeightFull();
    setMaxWidth(MAX_WINDOW_WIDTH);
    addClassName(LumoUtility.Padding.Horizontal.SMALL);

    add(children);
  }
}
