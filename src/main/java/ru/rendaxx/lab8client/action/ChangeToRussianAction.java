package ru.rendaxx.lab8client.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.util.LocalePublisher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;


@Component
public class ChangeToRussianAction extends ChangeLanguageAction {

    @Autowired
    public ChangeToRussianAction(LocalePublisher publisher) {
        super(publisher, Locale.forLanguageTag("ru"));
    }

}
