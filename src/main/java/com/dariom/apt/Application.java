package com.dariom.apt;

import static com.vaadin.flow.theme.lumo.Lumo.DARK;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Push
@SpringBootApplication
@Theme(value = "my-theme", variant = DARK)
public class Application implements AppShellConfigurator {

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

  // TODO icon
  @Override
  public void configurePage(AppShellSettings settings) {
//        settings.addFavIcon("icon", "/icons/favicon.png", "256x256");
  }
}
