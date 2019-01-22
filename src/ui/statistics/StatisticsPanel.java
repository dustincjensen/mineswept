package ui.statistics;

//import models.statistics.ShortTermStats;
import ui.utils.HexToRgb;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import models.statistics.AllStats;
import models.statistics.LongTermStats;

public class StatisticsPanel extends JPanel {
    public StatisticsPanel() {
        setLayout(new GridLayout(3, 1));
        setBackground(HexToRgb.convert("#333333"));
    }

    public void setStatistics(AllStats allStats) {
        removeAll();
        add(difficultyPanel("Easy", allStats.easy));
        add(difficultyPanel("Medium", allStats.medium));
        add(difficultyPanel("Hard", allStats.hard));
    }

    private Box difficultyPanel(String title, LongTermStats stats) {
        var panel = new Box(BoxLayout.Y_AXIS);
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panel.add(header(title));
        panel.add(content(stats));
        return panel;
    }

    private JPanel header(String title) {
        var header = new JPanel(new FlowLayout());
        header.setBackground(HexToRgb.convert("#007bff"));
        header.add(createJLabel(title, SwingConstants.LEFT));
        return header;
    }

    private JPanel content(LongTermStats stats) {
        var content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        
        var labelList = List.of(
            contentLabel("Won:"),
            contentValue("" + stats.gamesWon),
            contentLabel("Lost:"),
            contentValue("" + stats.gamesLost),
            contentLabel("Played:"),
            contentValue("" + stats.gamesPlayed)
        );

        var count = 0;
        for (var y = 0; y < labelList.size() / 2; y++) {
            for (var x = 0; x < 2; x++) {
                var c = createContentConstraints(x, y);
                content.add(labelList.get(count), c);
                count++;
            }
        }

        return content;
    }

    private GridBagConstraints createContentConstraints(int x, int y) {
        var c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.weightx = x == 1 ? 1.0 : 0.0;
        c.weighty = 1.0;
        c.insets = new Insets(5,5,0,5);
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

    private JLabel contentLabel(String text) {
        return createJLabel(text, SwingConstants.RIGHT);
    }

    private JLabel contentValue(String text) {
        return createJLabel(text, SwingConstants.LEFT);
    }

    private JLabel createJLabel(String text, int alignment) {
        var label = new JLabel(text);
        label.setHorizontalAlignment(alignment);
        label.setForeground(HexToRgb.convert("#ffffff"));
        return label;
    }
}