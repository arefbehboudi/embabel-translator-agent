package com.embabel.translator.view;


import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

@Theme("mytheme")
@PWA(name = "Translator", shortName = "Translator")
public class AppShell implements AppShellConfigurator {
}