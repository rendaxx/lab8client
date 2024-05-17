package ru.rendaxx.lab8client.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.util.LocalePublisher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;


public class ChangeLanguageAction extends AbstractAction {

    private LocalePublisher publisher;
    protected Locale locale;

    public ChangeLanguageAction(LocalePublisher publisher, Locale locale) {
        super(locale.getDisplayName());
        this.publisher = publisher;
        this.locale = locale;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Locale.setDefault(locale);
        publisher.notifySubscribers();
    }
}
