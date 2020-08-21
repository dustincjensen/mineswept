package ui.layout.body;

import events.IEventSubscriber;
import events.ResetMinePanelEvent;
import events.UpdateMinePanelEvent;
import services.OptionsService;
import ui.utils.ColorConverter;

import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import utils.ClassFactory;

/**
 * Renders the the mine panel that allows the player to play the game.
 */
@SuppressWarnings("serial")
public class MinePanel extends JPanel {
	private OptionsService optionsService;
	private IEventSubscriber eventSubscriber;
	private JPanel minePanel;
	private GridLayout minePanelLayout;
	private Vector<MineButton> mineButtons;
	
	public MinePanel(
		int initialHeight,
		int initialWidth,
		int maximumPuzzleMineCount,
		OptionsService options,
		IEventSubscriber subscriber
	) {
		optionsService = options;
		eventSubscriber = subscriber;

		setLayout(new BorderLayout(0 , 0));
		mineButtons = new Vector<MineButton>(maximumPuzzleMineCount);
		setupInteriorMinePanel(initialHeight, initialWidth);
		setupSubscriptions();
	}

	private void setupInteriorMinePanel(int h, int w) {
		minePanel = new JPanel();

		// GridLayout goes by row, column
		minePanelLayout = new GridLayout(h, w);
		minePanel.setLayout(minePanelLayout);

		addMines(h, w);
		add(minePanel, BorderLayout.CENTER);
	}

	private void addMines(int h, int w) {
		minePanel.removeAll();
		mineButtons.clear();

		var bgColor = ColorConverter.convert(optionsService.squareColor());
		var bgAltColor = ColorConverter.convert(optionsService.squareAltColor());
		var clickedBgColor = ColorConverter.convert(optionsService.clickedColor());
		var clickedAltBgColor = ColorConverter.convert(optionsService.clickedAltColor());
		var failBgColor = ColorConverter.convert(optionsService.clickedFailColor());

		Color[] mineColors = {
			ColorConverter.convert(optionsService.mineNumOneColor()),
			ColorConverter.convert(optionsService.mineNumTwoColor()),
			ColorConverter.convert(optionsService.mineNumThreeColor()),
			ColorConverter.convert(optionsService.mineNumFourColor()),
			ColorConverter.convert(optionsService.mineNumFiveColor()),
			ColorConverter.convert(optionsService.mineNumSixColor()),
			ColorConverter.convert(optionsService.mineNumSevenColor()),
			ColorConverter.convert(optionsService.mineNumEightColor())
		};

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				// TODO this is temporary for now... Need to figure out how to create mines. Perhaps this IS the best way.
				MineButton mb = ClassFactory.create(MineButton.class);
				mb.setPosition(x, y);
				mb.setColors(bgColor, bgAltColor, clickedBgColor, clickedAltBgColor, failBgColor, mineColors);
				// TODO add options to set these...
				mb.setBorders(StylesModern.RAISED_BORDER, StylesModern.LOWERED_BORDER);
				mb.decorate();
				mineButtons.add(mb);
				minePanel.add(mb);
			}
		}
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(UpdateMinePanelEvent.class, event -> {
			for (int i = 0; i < mineButtons.size(); i++) {
				mineButtons.get(i).decorate();
			}
			revalidate();
			repaint();
		});

		eventSubscriber.subscribe(ResetMinePanelEvent.class, event -> {
			minePanelLayout.setRows(event.h);
			minePanelLayout.setColumns(event.w);
			addMines(event.h, event.w);
			
			revalidate();
			repaint();
		});
	}
}