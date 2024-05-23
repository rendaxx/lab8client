package ru.rendaxx.lab8client.action;

import ru.rendaxx.lab8client.util.LocalePublisher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;


public class ChangeLanguageAction extends AbstractAction {

    private final LocalePublisher publisher;
    protected Locale locale;

    public ChangeLanguageAction(LocalePublisher publisher, Locale locale) {
        super(locale.getDisplayName(locale));
        this.publisher = publisher;
        this.locale = locale;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Locale.setDefault(locale);
        publisher.notifySubscribers();
    }
}
