package ui.components.button;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class DefaultButtonUi extends BasicButtonUI {
    private Color foreground, background, backgroundHover;
	private int radius;

	public DefaultButtonUi(
		Color foreground, 
		Color background, 
		Color backgroundHover,
		int radius
	) {
		this.foreground = foreground;
		this.background = background;
		this.backgroundHover = backgroundHover;
		this.radius = radius;
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
		
		var focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		var buttonIsActioned = model.isRollover() || model.isArmed() || focusOwner == button;
		
		g.setColor(buttonIsActioned ? backgroundHover : background);
		g.fillRoundRect(0, 0, button.getSize().width - 1, button.getSize().height - 1, radius, radius);
		g.drawRoundRect(0, 0, button.getSize().width - 1, button.getSize().height - 1, radius, radius);
		button.setForeground(foreground);
		
		super.paint(g, c);
	}   

	// @Override
    // public void paint(Graphics g, JComponent c) {
	// 	var button = (AbstractButton) c;
	// 	var model = button.getModel();
		
	// 	var focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
	// 	var buttonIsActioned = model.isRollover() || model.isArmed() || focusOwner == button;
		
	// 	if (buttonIsActioned) {
	// 		g.setColor(backgroundHover);
	// 		g.fillRoundRect(0, 0, button.getSize().width - 1, button.getSize().height - 1, 3, 3);
	// 	}
	// 	g.setColor(background);
	// 	g.drawRoundRect(0, 0, button.getSize().width - 1, button.getSize().height - 1, 3, 3);
	// 	button.setForeground(foreground);
		
	// 	super.paint(g, c);
	// }   
}
