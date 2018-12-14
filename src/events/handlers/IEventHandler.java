package events.handlers;

/**
 * Interface describing a class that handles events of a type.
 * @param <T>   the type of event to handle.
 */
public interface IEventHandler<T> {
    /**
     * Executes the required behaviour when an event is received.
     * @param event     the event to handle.
     */
    public void execute(T event);
}