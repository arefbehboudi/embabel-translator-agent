package com.embabel.translator.view;

import com.embabel.translator.domain.Domain;
import com.embabel.translator.domain.Tone;
import com.embabel.translator.domain.TranslationRequest;
import com.embabel.translator.domain.TranslationResult;
import com.embabel.translator.service.TranslatorService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

import java.util.Arrays;

@Route("")
public class TranslateView extends VerticalLayout {

    private final String[] popular = new String[]{"Persian", "English", "Spanish", "French", "German", "Arabic"};

    private final TextArea sourceArea = new TextArea();
    private final TextArea resultArea = new TextArea();
    private final Span sourceCounter = new Span("0 / 5000");

    private final Tabs sourceTabs = languageTabs(true);
    private final Tabs targetTabs = languageTabs(false);
    private final Select<String> domain = domain();
    private final Select<String> tone = tone();


    private final TranslatorService translatorService;

    public TranslateView(TranslatorService translatorService) {
        this.translatorService = translatorService;

        setSizeFull();
        getStyle().set("background", "var(--lumo-base-color)");


        var tools = new HorizontalLayout(
                tone, domain
        );
        add(tools);

        var leftWrap = new Div(sourceTabs);
        var rightWrap = new Div(targetTabs);
        leftWrap.addClassName("tabs-wrap");
        rightWrap.addClassName("tabs-wrap");

        var bars = new HorizontalLayout(leftWrap, rightWrap);
        bars.setWidthFull();
        bars.setFlexGrow(1, bars.getComponentAt(0), bars.getComponentAt(1), bars.getComponentAt(1));
        add(bars);

        var cards = new HorizontalLayout(sourceCard(), resultCard());
        cards.setWidthFull();
        cards.setFlexGrow(1, cards.getComponentAt(0), cards.getComponentAt(1));
        add(cards);


        resultArea.setReadOnly(true);
        sourceArea.setMaxLength(5000);
        sourceArea.addValueChangeListener(e -> sourceCounter.setText(e.getValue().length() + " / 5000"));

        sourceArea.addValueChangeListener(e -> translate());
    }

    private Select<String> domain() {
        Select<String> domain = new Select<>();
        domain.setItems(Arrays.stream(Domain.values()).map(Domain::getDisplayName).toList());
        domain.setLabel("Domain");
        domain.setValue("General");
        return domain;
    }

    private Select<String> tone() {
        Select<String> tone = new Select<>();
        tone.setItems(Arrays.stream(Tone.values()).map(Tone::getDisplayName).toList());
        tone.setLabel("Tone");
        tone.setValue(Tone.FORMAL.getDisplayName());
        return tone;
    }

    private Tabs languageTabs(boolean left) {
        var tabs = new Tabs();
        tabs.addClassNames("lang-tabs");
        tabs.setAutoselect(true);
        if (left) {
            tabs.add(new Tab("Detect language"));
        } else {
            for (var l : popular) tabs.add(new Tab(l));
            tabs.setSelectedIndex(1);
        }
        return tabs;
    }

    private Div sourceCard() {
        var wrapper = cardShell();

        var toolbar = new HorizontalLayout();
        var mic = new Button(new Icon(VaadinIcon.MICROPHONE));
        mic.addClassName("icon-btn");
        var services = new HorizontalLayout(new Icon(VaadinIcon.LOCATION_ARROW), new Icon(VaadinIcon.GLOBE));
        services.addClassName("services-icons");
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        toolbar.add(mic, services);

        sourceArea.setPlaceholder("Type to translate…");
        sourceArea.addClassName("text-area");
        sourceArea.setWidthFull();
        sourceArea.setHeight("260px");

        var counterRow = new HorizontalLayout(new Div(new Text("")), sourceCounter);
        counterRow.setWidthFull();
        counterRow.setJustifyContentMode(JustifyContentMode.END);
        counterRow.addClassName("counter-row");

        wrapper.add(toolbar, sourceArea, counterRow);
        return wrapper;
    }

    private Div resultCard() {
        var wrapper = cardShell();
        var header = new HorizontalLayout(new Span("Translation"));
        header.addClassNames("result-header");
        resultArea.addClassName("text-area");
        resultArea.setWidthFull();
        resultArea.setHeight("260px");
        wrapper.add(header, resultArea);
        return wrapper;
    }

    private Div cardShell() {
        var box = new Div();
        box.addClassNames("card");
        box.getStyle().set("flex", "1");
        return box;
    }

    private void translate() {
        String src = sourceTabs.getSelectedTab().getLabel();
        String tgt = targetTabs.getSelectedTab().getLabel();
        Domain domain = Domain.valueOfDisplayName(this.domain.getValue());
        Tone tone = Tone.valueOfDisplayName(this.tone.getValue());
        TranslationResult out = translatorService.translate(new TranslationRequest(sourceArea.getValue(),
                src.equals("Detect language") ? null : src, tgt, tone.getDisplayName(), domain.getDisplayName()));
        resultArea.setValue(out.getTranslation());
    }
}

