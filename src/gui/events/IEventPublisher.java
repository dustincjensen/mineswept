package gui.events;

public interface IEventPublisher {
    <T> void publish(T event);
}