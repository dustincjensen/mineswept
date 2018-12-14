package events;

/**
 * Interface describing a lambda function that receives an event.
 * @param <T>   the type of the event to handle.
 */
public interface IEventHandleSubscription<T> {
    /**
     * A method that is invoked when an event of type T is received.
     * @param event     the event that occurred.
     */
    void handleSubscription(T event);
}