package gui.components.tabbedPane;

import gui.components.button.DefaultButton;
import java.awt.event.ActionListener;

public class CustomTabButton extends DefaultButton {
    private static final String selectedForeground = "#ffffff";
    private static final String selectedBackground = "#007bff";
    private static final String selectedBackgroundHover = "#0069d9";
    // private static final String foreground = "#212529";
    // private static final String background = "#f8f9fa";
    // private static final String backgroundHover = "#e2e6ea";
    private static final String foreground = "#ffffff";
    private static final String background = "#333333";
    private static final String backgroundHover = "#222222";

    public CustomTabButton(String buttonText, ActionListener actionListener, boolean isSelected) {
        super(
            buttonText,
            isSelected ? selectedForeground : foreground,
            isSelected ? selectedBackground : background,
            isSelected ? selectedBackgroundHover : backgroundHover,
            actionListener
        );
    }

    public void setSelected(boolean value) {
        if (value) {
            setColors(selectedForeground, selectedBackground, selectedBackgroundHover);
        } else {
            setColors(foreground, background, backgroundHover);
        }
    }
}