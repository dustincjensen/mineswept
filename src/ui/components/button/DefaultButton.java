package ui.components.button;

import ui.utils.HexToRgb;
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
        ActionListener actionListener
    ) {
        super(buttonText);

        buttonUi = new DefaultButtonUi(
            HexToRgb.convert(foreground),
            HexToRgb.convert(background),
            HexToRgb.convert(backgroundHover),
            radius
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
            HexToRgb.convert(foregroundHexValue),
            HexToRgb.convert(backgroundHexValue),
            HexToRgb.convert(backgroundHoverHexValue)
        );

        // Since we are updating outside of normal behaviour, we need to
        // force a repaint of the component.
        repaint();
    }
}