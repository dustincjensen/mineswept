package ui.components.button;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;

@SuppressWarnings("serial")
public abstract class DefaultButton extends JButton {
    private DefaultButtonUi buttonUi;

    public DefaultButton(
        String buttonText,
        String foreground,
        String background,
        String backgroundHover,
        int radius,
        boolean isMinimal,
        ActionListener actionListener
    ) {
        super(buttonText);

        buttonUi = new DefaultButtonUi(
            Color.decode(foreground),
            Color.decode(background),
            Color.decode(backgroundHover),
            radius,
            isMinimal
        );

        setUI(buttonUi);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        addActionListener(actionListener);
    }

    protected void setColors(
        String foregroundHexValue,
        String backgroundHexValue,
        String backgroundHoverHexValue
    ) {
        buttonUi.setColors(
            Color.decode(foregroundHexValue),
            Color.decode(backgroundHexValue),
            Color.decode(backgroundHoverHexValue)
        );

        // Since we are updating outside of normal behaviour, we need to
        // force a repaint of the component.
        repaint();
    }
}