package ru.rendaxx.lab8client.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocalePublisher {
    @Resource(name = "myList")
    List<LocaleChangeListener> listeners; //TODO delete not valid elements


    public void addSubscriber(LocaleChangeListener listener) {
        listeners.add(listener);
    }

    public void notifySubscribers() {
        listeners.forEach(LocaleChangeListener::localeChanged);
    }
}
