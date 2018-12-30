package gui.components.button;

import java.awt.event.ActionListener;

public class DangerButton extends DefaultButton {
    private static final String foreground = "#ffffff";
    private static final String background = "#dc3545";
    private static final String backgroundHover = "#c82333";

    public DangerButton(String buttonText, ActionListener actionListener) {
        super(buttonText, foreground, background, backgroundHover, actionListener);
    }
}