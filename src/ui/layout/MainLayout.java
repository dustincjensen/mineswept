package ui.layout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import ui.layout.body.BodyLayout;
import ui.layout.header.HeaderLayout;

@SuppressWarnings("serial")
public class MainLayout extends Box {
    public MainLayout(
        HeaderLayout header,
        BodyLayout body
    ) {
        super(BoxLayout.Y_AXIS);
        add(header);
        add(body);
    }
}