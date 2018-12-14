package events;

/**
 * Interface that passes events onto the event handlers.
 */
public interface IEventPublisher {
    /**
     * Inform the event handlers of an event.
     * @param event     the event that has occurred.
     */
    <T> void publish(T event);
}