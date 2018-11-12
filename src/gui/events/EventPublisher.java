package gui.events;

import gui.events.handlers.*;
import gui.options.OptionWindow;
import java.util.ArrayList;
import java.lang.reflect.ParameterizedType;

public class EventPublisher implements IEventPublisher {
    private ArrayList<IEventHandler> handlers;

    // TODO this should not rely on OptionWindow, this should only ask for
    // IEventHandler's and those dependencies will be created by the injection
    // handler.
    public EventPublisher(OptionWindow window) {
        System.out.println("Creating Event Publisher");
        handlers = new ArrayList<IEventHandler>();
        handlers.add(new AboutEventHandler());
        handlers.add(new GetHintEventHandler());
        handlers.add(new PauseGameEventHandler());
        handlers.add(new QuitGameEventHandler());
        handlers.add(new ResetGameEventHandler());
        handlers.add(new ShowOptionsEventHandler(window));
        handlers.add(new ShowRecordsEventHandler());
        handlers.add(new ShowStatisticsEventHandler());
    }

    public <T> void publish(T event) {
        for (var handler : handlers) {
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