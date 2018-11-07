package gui.events.handlers;

public interface IEventHandler<T> {
    public void execute(T event);
}