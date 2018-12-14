package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventSubscriber implements IEventSubscriber {
    private Map<Class, ArrayList<IEventHandleSubscription>> subscribers;

    public <T> void subscribe(Class<T> c, IEventHandleSubscription<T> methodToInvoke) {
        if (subscribers == null) {
            subscribers = new HashMap();
        }

        if (!subscribers.containsKey(c)) {
            subscribers.put(c, new ArrayList<IEventHandleSubscription>());
        }

        subscribers.get(c).add(methodToInvoke);
    }

    public <T> void notify(T event) {
        ArrayList<IEventHandleSubscription> eventSubscribers = subscribers.get(event.getClass());

        if (eventSubscribers == null) {
            System.out.println("Error: There are no subscribers for the event type: " + event.getClass());
            return;
        }

        for (var subscriber : eventSubscribers) {
            subscriber.handleSubscription(event);
        }
    }
}