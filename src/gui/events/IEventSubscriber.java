package gui.events;

public interface IEventSubscriber {
    <T> void subscribe(Class<T> c, IEventHandleSubscription<T> methodToInvoke);
    <T> void notify(T event);
}