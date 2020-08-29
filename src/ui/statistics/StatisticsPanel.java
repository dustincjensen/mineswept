package ui.statistics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import models.statistics.LongTermStats;

@SuppressWarnings("serial")
public class StatisticsPanel extends JPanel {
    private JLabel gamesWon, gamesLost, gamesPlayed, gamesWonPercent, gamesLostPercent;
    private DecimalFormat formatter;

    public StatisticsPanel() {
        formatter = new DecimalFormat("#00.0");
        
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5,15,5,15));
        setupPanel();
        setPreferredSize(new Dimension(200, getHeight()));
    }

    public void setStatistics(LongTermStats stats) {
        var gw = stats.gamesWon;
        var gl = stats.gamesLost;
        var gp = stats.gamesPlayed;
        var winPercent = getValueOrDefault((double)gw / gp * 100, 0.0);
        var losePercent = getValueOrDefault((double)gl / gp * 100, 0.0);

        gamesWon.setText("" + gw);
        gamesLost.setText("" + gl);
        gamesPlayed.setText("" + gp);
        gamesWonPercent.setText(String.format(" (%s%%)", formatter.format(winPercent)));
        gamesLostPercent.setText(String.format(" (%s%%)", formatter.format(losePercent)));
    }

    private double getValueOrDefault(double value, double defaultValue) {
        return Double.isNaN(value) ? defaultValue : value; 
    }

    private void setupPanel() {
        var content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        
        var playedLabel = createJLabel("Played:");
        gamesPlayed = createJLabel("");
        var playedPercent = createJLabel("");
        var wonLabel = createJLabel("Won:");
        gamesWon = createJLabel("");
        gamesWonPercent = createJLabel("");
        var lostLabel = createJLabel("Lost:");
        gamesLost = createJLabel("");
        gamesLostPercent = createJLabel("");

        var labelList = List.of(
            playedLabel, 
            gamesPlayed,
            playedPercent,
            wonLabel, 
            gamesWon, 
            gamesWonPercent, 
            lostLabel, 
            gamesLost, 
            gamesLostPercent
        );

        var count = 0;
        for (var y = 0; y < labelList.size() / 3; y++) {
            for (var x = 0; x < 3; x++) {
                var c = createContentConstraints(x, y);
                content.add(labelList.get(count), c);
                count++;
            }
        }

        add(content, BorderLayout.NORTH);
    }

    private GridBagConstraints createContentConstraints(int x, int y) {
        var c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.weightx = x == 1 ? 1.0 : 0.0;
        c.weighty = 0.0;
        c.insets = new Insets(5,5,0,5);
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

    private JLabel createJLabel(String text) {
        var label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setForeground(Color.decode("#ffffff"));
        return label;
    }
}