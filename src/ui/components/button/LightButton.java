package ui.components.button;

import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class LightButton extends DefaultButton {
    private static final String foreground = "#212529";
    private static final String background = "#f8f9fa";
    private static final String backgroundHover = "#e2e6ea";
    private static final int radius = 3;

    public LightButton(String buttonText, ActionListener actionListener) {
        super(buttonText, foreground, background, backgroundHover, radius, actionListener);
    }
}