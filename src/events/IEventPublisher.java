package events;

public interface IEventPublisher {
    <T> void publish(T event);
}