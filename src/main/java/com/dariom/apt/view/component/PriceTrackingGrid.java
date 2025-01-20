package com.dariom.apt.view.component;

import static com.vaadin.flow.component.grid.GridVariant.LUMO_ROW_STRIPES;
import static com.vaadin.flow.component.icon.VaadinIcon.TRASH;

import com.dariom.apt.core.domain.PriceTracking;
import com.dariom.apt.core.service.PriceTrackingService;
import com.dariom.apt.event.CreatePriceTrackingEvent;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import java.util.ArrayList;

public class PriceTrackingGrid extends Grid<PriceTracking> {

  private final PriceTrackingService priceTrackingService;
  private ListDataProvider<PriceTracking> dataProvider;

  public PriceTrackingGrid(PriceTrackingService priceTrackingService) {
    this.priceTrackingService = priceTrackingService;
    addThemeVariants(LUMO_ROW_STRIPES);

    // add columns
    addColumn(PriceTracking::productName)
        .setHeader("Product").setAutoWidth(true);
    addColumn(PriceTracking::desiredPrice)
        .setHeader("Desired Price").setAutoWidth(true);
    addColumn(PriceTracking::link)
        .setHeader("Link").setWidth("30rem");
    addColumn(new ComponentRenderer<>(tracking -> new Checkbox(tracking.active())))
        .setHeader("Active").setAutoWidth(true);
    addColumn(new ComponentRenderer<>(tracking ->
        new Button(TRASH.create(), e -> showDeleteConfirmation(tracking))))
        .setAutoWidth(true);

    loadData();
  }

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);

    ComponentUtil.addListener(
        attachEvent.getUI(),
        CreatePriceTrackingEvent.class,
        event -> loadData()
    );
  }

  private void loadData() {
    var trackings = new ArrayList<>(priceTrackingService.getPriceTrackings()); // mutable list
    dataProvider = new ListDataProvider<>(trackings);
    setDataProvider(dataProvider);
  }

  private void showDeleteConfirmation(PriceTracking tracking) {
    var confirmDialog = new ConfirmDialog();
    confirmDialog.setHeader("Confirm deletion");
    confirmDialog.setText("Are you sure you want to delete this tracking?");
    confirmDialog.setCancelable(true);
    confirmDialog.setConfirmText("Delete");
    confirmDialog.setCancelText("Cancel");

    confirmDialog.addConfirmListener(event -> deleteTracking(tracking));

    confirmDialog.open();
  }

  public void deleteTracking(PriceTracking tracking) {
    priceTrackingService.delete(tracking.id());
    dataProvider.getItems().remove(tracking);
    dataProvider.refreshAll();
  }

}
