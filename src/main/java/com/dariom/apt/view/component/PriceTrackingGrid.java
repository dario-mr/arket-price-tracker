package com.dariom.apt.view.component;

import com.dariom.apt.core.domain.PriceTracking;
import com.dariom.apt.core.service.PriceTrackingService;
import com.dariom.apt.event.CreatePriceTrackingEvent;
import com.dariom.apt.view.component.notifcation.Toast;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.vaadin.flow.component.grid.GridVariant.LUMO_ROW_STRIPES;
import static com.vaadin.flow.component.html.AnchorTarget.BLANK;
import static com.vaadin.flow.component.icon.VaadinIcon.TRASH;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.END;

@Slf4j
public class PriceTrackingGrid extends Grid<PriceTracking> {

    private final PriceTrackingService priceTrackingService;

    private final Set<Long> visibleDetailIds = new HashSet<>();
    private ListDataProvider<PriceTracking> dataProvider;

    public PriceTrackingGrid(PriceTrackingService priceTrackingService) {
        this.priceTrackingService = priceTrackingService;
        addThemeVariants(LUMO_ROW_STRIPES);

        // add columns
        addColumn(PriceTracking::getProductName)
                .setHeader("Product").setAutoWidth(true);
        addColumn(new ComponentRenderer<>(tracking -> new Checkbox(tracking.getActive(), event -> toggleActive(tracking, event.getValue()))))
                .setHeader("Active").setAutoWidth(true);
        addColumn(new ComponentRenderer<>(tracking ->
                new Button(TRASH.create(), e -> showDeleteConfirmation(tracking))))
                .setAutoWidth(true);

        // Details renderer
        setItemDetailsRenderer(new ComponentRenderer<>(this::createDetailsComponent));

        // click listener to toggle details
        addItemClickListener(event -> {
            var tracking = event.getItem();
            if (visibleDetailIds.contains(tracking.getId())) {
                visibleDetailIds.remove(tracking.getId());
                setDetailsVisible(tracking, false);
            } else {
                visibleDetailIds.clear();
                visibleDetailIds.add(tracking.getId());
                setDetailsVisible(tracking, true);
            }
        });

        loadData();
    }

    private Component createDetailsComponent(PriceTracking tracking) {
        var linkAnchor = new Anchor(tracking.getLink(), "Go to product page", BLANK);
        linkAnchor.setWidthFull();
        linkAnchor.getStyle().set("padding-top", "var(--lumo-space-m)");

        var priceField = new NumberField("Desired Price");
        priceField.setValue(Double.valueOf(tracking.getDesiredPrice().toString()));
        priceField.setWidthFull();

        var saveButton = new Button("Save");
        saveButton.addClickListener(event -> updateTracking(tracking, priceField.getValue()));

        var priceLayout = new HorizontalLayout(priceField, saveButton);
        priceLayout.setWidthFull();
        priceLayout.setVerticalComponentAlignment(END, saveButton);

        var detailsLayout = new VerticalLayout(linkAnchor, priceLayout);
        detailsLayout.setPadding(false);
        detailsLayout.setSpacing(true);
        detailsLayout.setWidthFull();

        return detailsLayout;
    }

    private void updateTracking(PriceTracking tracking, Double newPrice) {
        // if price was unchanged, return
        if (tracking.getDesiredPrice().compareTo(new BigDecimal(newPrice)) == 0) {
            return;
        }

        tracking.setDesiredPrice(new BigDecimal(newPrice));
        try {
            priceTrackingService.updatePriceTracking(tracking);
        } catch (Exception e) {
            log.error("Error updating tracking {}", tracking.getId(), e);
            Toast.error("Error updating tracking: " + e.getMessage());
            return;
        }

        dataProvider.refreshItem(tracking);
        Toast.success("Tracking updated");
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        ComponentUtil.addListener(attachEvent.getUI(),
                CreatePriceTrackingEvent.class,
                event -> loadData());
    }

    private void loadData() {
        var trackings = new ArrayList<PriceTracking>();
        try {
            trackings = new ArrayList<>(priceTrackingService.getAllPriceTrackings()); // mutable list
        } catch (Exception ex) {
            log.error("Error fetching trackings", ex);
            Toast.error("Error fetching trackings: " + ex.getMessage());
        }

        dataProvider = new ListDataProvider<>(trackings);
        setDataProvider(dataProvider);
    }

    private void toggleActive(PriceTracking tracking, boolean active) {
        tracking.setActive(active);
        try {
            priceTrackingService.updatePriceTracking(tracking);
        } catch (Exception ex) {
            log.error("Error updating tracking {}", tracking.getId(), ex);
            Toast.error("Error updating tracking: " + ex.getMessage());
        }

        dataProvider.refreshItem(tracking);
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
        try {
            priceTrackingService.delete(tracking.getId());
        } catch (Exception ex) {
            log.error("Error deleting tracking {}", tracking.getId(), ex);
            Toast.error("Error deleting tracking: " + ex.getMessage());
            return;
        }

        dataProvider.getItems().remove(tracking);
        dataProvider.refreshAll();
    }

}
