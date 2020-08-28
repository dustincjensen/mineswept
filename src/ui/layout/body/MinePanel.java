package ui.layout.body;

import events.IEventSubscriber;
import events.ResetMinePanelEvent;
import events.UpdateMinePanelEvent;
import services.OptionsService;
import state.GameState;
import ui.utils.BorderConverter;
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
	private GameState gameState;
	private OptionsService optionsService;
	private IEventSubscriber eventSubscriber;
	private JPanel minePanel;
	private GridLayout minePanelLayout;
	private Vector<MineButton> mineButtons;
	
	public MinePanel(
		GameState gameState,
		OptionsService options,
		IEventSubscriber subscriber
	) {
		this.gameState = gameState;
		optionsService = options;
		eventSubscriber = subscriber;

		setLayout(new BorderLayout());
		
		mineButtons = new Vector<MineButton>(gameState.getCurrentPuzzleMineCount());
		setupInteriorMinePanel(gameState.getCurrentPuzzleHeight(), gameState.getCurrentPuzzleWidth());
		setupSubscriptions();
	}

	@Override
	public Dimension getPreferredSize() {
		var parentSize = getParent().getSize();
		var h = (int)parentSize.getHeight();
		var w = (int)parentSize.getWidth();

		if (h <= 0 || w <=0) {
			return super.getPreferredSize();
		}
		return setNewSize(h, w);
	}

	// TODO put this in a shared location?
	// TODO check todo notes for resizing fonts...
	private Dimension setNewSize(int h, int w) {
		var ratio = (double)gameState.getCurrentPuzzleWidth() / (double)gameState.getCurrentPuzzleHeight();

		if (w > h) {
            var newWidth = (int)(h*ratio);
            var newHeight = h;
            if (newWidth > w) {
				// The width can't be larger than the container width,
				// so cap it, and make the height match the width ratio.
                newWidth = w;
                newHeight = (int)(w / ratio);
            }
            return new Dimension(newWidth, newHeight);
        } else if (w < h) {
            return new Dimension(w,(int)(w/ratio));
        } else {
            return new Dimension(w, h);
        }
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

		var raisedBorder = BorderConverter.convert(optionsService.raisedBorder());
		var loweredBorder = BorderConverter.convert(optionsService.loweredBorder());

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

		// Set the mine panel background, since this might change on refresh.
		minePanel.setBackground(clickedBgColor);

		// Add mines for each position
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				// TODO this is temporary for now... Need to figure out how to create mines. Perhaps this IS the best way.
				MineButton mb = ClassFactory.create(MineButton.class);
				mb.setPosition(x, y);
				mb.setColors(bgColor, bgAltColor, clickedBgColor, clickedAltBgColor, failBgColor, mineColors);
				mb.setBorders(raisedBorder, loweredBorder);
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