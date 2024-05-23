package ru.rendaxx.lab8client.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.util.LocalePublisher;

import java.util.Locale;


@Component
public class ChangeToCatalanAction extends ChangeLanguageAction {

    @Autowired
    public ChangeToCatalanAction(LocalePublisher publisher) {
        super(publisher, Locale.forLanguageTag("ca"));
    }

}
