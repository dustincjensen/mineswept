package ui.layout;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import ui.layout.body.BodyLayout;
import ui.layout.header.HeaderLayout;

@SuppressWarnings("serial")
public class MainLayout extends JPanel {
    public MainLayout(
        HeaderLayout header,
        BodyLayout body
    ) {
        super(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
    }
}