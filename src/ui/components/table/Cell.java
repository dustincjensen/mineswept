package ui.components.table;

public class Cell {
    public String text;
    public boolean useExtraWidth; 

    public Cell(String text, boolean useExtraWidth) {
        this.text = text;
        this.useExtraWidth = useExtraWidth;
    }
}