package gui.events;

public class MineClickedEvent {
    public boolean isLeftMouseButton;
    public boolean insideMineField;
    public int x;
    public int y;
    public int dragX;
    public int dragY;

    public MineClickedEvent(
        boolean isLeftMouseButton,
        boolean insideMineField,
        int x,
        int y,
        int dragX,
        int dragY
    ) {
        this.isLeftMouseButton = isLeftMouseButton;
        this.insideMineField = insideMineField;
        this.x = x;
        this.y = y;
        this.dragX = dragX;
        this.dragY = dragY;
    }
}