package events;

/**
 * Interface used to control flow of events to a list of subscribers.
 */
public interface IEventSubscriber {
    /**
     * Subscribe to a event, providing a method to invoke when the event occurs.
     * @param c                 the class type of the event.
     * @param methodToInvoke    the method to invoke when the event occurs.
     */
    <T> void subscribe(Class<T> c, IEventHandleSubscription<T> methodToInvoke);

    /**
     * Notify the subscribers of an event occurrence.
     * @param event     the event that occurred.
     */
    <T> void notify(T event);
}