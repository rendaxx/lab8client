package ru.rendaxx.lab8client.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.util.LocalePublisher;

import java.util.Locale;


@Component
public class ChangeToSpanishAction extends ChangeLanguageAction {

    @Autowired
    public ChangeToSpanishAction(LocalePublisher publisher) {
        super(publisher, Locale.of("es", "HN"));
    }

}
