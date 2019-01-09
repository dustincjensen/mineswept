package ui.components.button;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class DefaultButtonUi extends BasicButtonUI {
    private Color foreground, background, backgroundHover;

	public DefaultButtonUi(Color foreground, Color background, Color backgroundHover) {
		this.foreground = foreground;
		this.background = background;
		this.backgroundHover = backgroundHover;
	}

	public void setColors(
		Color foreground,
		Color background,
		Color backgroundHover
	) {
		this.foreground = foreground;
		this.background = background;
		this.backgroundHover = backgroundHover;
	}

    @Override
    public void paint(Graphics g, JComponent c) {
        var button = (AbstractButton) c;
		var model = button.getModel();
		
		var buttonIsActioned = model.isRollover() || model.isArmed();
		button.setBackground(buttonIsActioned ? backgroundHover : background);
		button.setForeground(foreground);
		
		super.paint(g, c);
	}   
}
