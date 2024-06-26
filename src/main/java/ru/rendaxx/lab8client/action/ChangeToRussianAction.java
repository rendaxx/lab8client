package ru.rendaxx.lab8client.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.util.LocalePublisher;

import java.util.Locale;


@Component
public class ChangeToRussianAction extends ChangeLanguageAction {

    @Autowired
    public ChangeToRussianAction(LocalePublisher publisher) {
        super(publisher, Locale.forLanguageTag("ru"));
    }

}
