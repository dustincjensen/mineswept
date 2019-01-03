package gui.components.button;

import java.awt.event.ActionListener;

public class LightButton extends DefaultButton {
    private static final String foreground = "#212529";
    private static final String background = "#f8f9fa";
    private static final String backgroundHover = "#e2e6ea";

    public LightButton(String buttonText, ActionListener actionListener) {
        super(buttonText, foreground, background, backgroundHover, actionListener);
    }
}