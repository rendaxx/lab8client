package ru.rendaxx.lab8client.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.util.LocalePublisher;

import java.util.Locale;


@Component
public class ChangeToCzechAction extends ChangeLanguageAction {

    @Autowired
    public ChangeToCzechAction(LocalePublisher publisher) {
        super(publisher, Locale.forLanguageTag("cs"));
    }

}
