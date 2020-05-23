package ui.components.button;

import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PrimaryButton extends DefaultButton {
    private static final String foreground = "#ffffff";
    private static final String background = "#007bff";
    private static final String backgroundHover = "#0069d9";
    private static final int radius = 3;

    public PrimaryButton(String buttonText, ActionListener actionListener) {
        super(buttonText, foreground, background, backgroundHover, radius, false, actionListener);
    }

    public PrimaryButton(String buttonText, boolean isMinimal, ActionListener actionListener) {
        super(buttonText, foreground, background, backgroundHover, radius, isMinimal, actionListener);
    }
}