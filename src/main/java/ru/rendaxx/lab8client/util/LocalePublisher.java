package ru.rendaxx.lab8client.util;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Log
@Component
public class LocalePublisher {
    private List<SetTextListener> listeners;

    @Autowired
    public LocalePublisher(@Qualifier("myListenersList") List<SetTextListener> listeners) {
        this.listeners = listeners;
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                log.info("Deleted invalid subscribers");
//                deleteInvalidSubscribers();
//            }
//        }, 1000, 30000); TODO
    }

    public void addSubscriber(SetTextListener listener) {
        listeners.add(listener);
    }

    private void deleteInvalidSubscribers() {
        listeners.removeIf(o -> !o.isVisible());
    }

    public void notifySubscribers() {
        deleteInvalidSubscribers();
        listeners.forEach(SetTextListener::setText);
    }
}
