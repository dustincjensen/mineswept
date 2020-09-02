package events;

import events.handlers.*;
import java.util.List;
import java.lang.reflect.ParameterizedType;

public class EventPublisher implements IEventPublisher {
    private List<IEventHandler> eventHandlers;

    public EventPublisher(List<IEventHandler> handlers) {
        eventHandlers = handlers;
    }

    public <T> void publish(T event) {
        for (var handler : eventHandlers) {
            for (var i : handler.getClass().getGenericInterfaces()) {
                if (i instanceof ParameterizedType) {
                    var genericTypes = ((ParameterizedType) i).getActualTypeArguments();
                    for (var t : genericTypes) {
                        if (event.getClass().getTypeName() == t.getTypeName()) {
                            handler.execute(event);
                        }
                    }
                }
            }
        }
    }
}