package gui.components.table;

import gui.HexToRgb;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomReadonlyTable extends JPanel {
    private TableRow header;
    private JPanel content;

    public CustomReadonlyTable(TableRow header) {
        this.header = header;
        setLayout(new GridBagLayout());
        setBackground(HexToRgb.convert("#333333"));
        createContentPanel();
        createHeader();
    }

    public void setData(TableRow... rows) {
        content.removeAll();
        createHeader();
        
        for (var row = 0; row < rows.length; row++) {
            var rowColor = row % 2 == 0 ? "#333333" : "#212529";
            renderRowCells(rows[row].cellHandlers, row + 1, "#ffffff", rowColor);
        }
        revalidate();
    }

    private void createContentPanel() {
        content = new JPanel();
        content.setLayout(new GridBagLayout());
        content.setBackground(HexToRgb.convert("#333333"));

        var c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;

        add(content, c);
    }

    private void createHeader() {
        var cellHandlers = header.cellHandlers;
        renderRowCells(cellHandlers, 0, "#ffffff", "#007bff");
    }

    private void renderRowCells(
        ArrayList<ICellHandler> cellHandlers, 
        int row, 
        String foregroundHexColor,
        String backgroundHexColor
    ) {
        for (var column = 0; column < cellHandlers.size(); column++) {
            var cell = cellHandlers.get(column).handleCell(row, column);
            var label = createLabel(cell.text, foregroundHexColor, backgroundHexColor);
            var c = getConstraints(column, row, cell.useExtraWidth);
            content.add(label, c);
        }
    }

    private JLabel createLabel(
        String text,
        String foregroundHexColor,
        String backgroundHexColor
    ) {
        var label = new JLabel();
        label.setText(text);
        label.setForeground(HexToRgb.convert(foregroundHexColor));
        label.setBackground(HexToRgb.convert(backgroundHexColor));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return label;
    }

    private GridBagConstraints getConstraints(int x, int y, boolean useExtraWidth) {
        var c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = useExtraWidth ? 1.0 : 0.0;
        c.weighty = 0.0;
        c.anchor = GridBagConstraints.WEST;
        c.ipadx = 2;
        c.ipady = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }
}
