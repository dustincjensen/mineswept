package gui.components.button;

import gui.HexToRgb;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public abstract class DefaultButton extends JButton {
    public DefaultButton(
        String buttonText,
        String foreground,
        String background,
        String backgroundHover,
        ActionListener actionListener
    ) {
        super(buttonText);
        setUI(new DefaultButtonUi(
            HexToRgb.convert(foreground),
            HexToRgb.convert(background),
            HexToRgb.convert(backgroundHover)
        ));
        setBorderPainted(false);
        setFocusPainted(false);
        addActionListener(actionListener);
    }
}