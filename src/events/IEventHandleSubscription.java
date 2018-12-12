package events;

public interface IEventHandleSubscription<T> {
    void handleSubscription(T event);
}