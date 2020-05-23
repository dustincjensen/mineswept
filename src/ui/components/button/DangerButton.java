package ui.components.button;

import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class DangerButton extends DefaultButton {
    private static final String foreground = "#ffffff";
    private static final String background = "#dc3545";
    private static final String backgroundHover = "#c82333";
    private static final int radius = 3;

    public DangerButton(String buttonText, ActionListener actionListener) {
        super(buttonText, foreground, background, backgroundHover, radius, actionListener);
    }
}