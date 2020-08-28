package ui.layout.body;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.PauseGameEvent;
import events.ResetMinePanelEvent;
import services.OptionsService;
import state.GameState;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Vector;
import javax.swing.JPanel;
import ui.components.button.PrimaryButton;
import ui.utils.BorderConverter;
import ui.utils.ColorConverter;
import ui.utils.FontChange;

/**
 * Renders a pause screen and allows the user to restart the game.
 */
@SuppressWarnings("serial")
public class PausePanel extends JPanel {
	private GameState gameState;
	private OptionsService optionsService;
	private IEventPublisher eventPublisher;

	private JPanel minePanel;
	private Vector<ReadonlyMineButton> mineButtons;

	public PausePanel(
		GameState gameState,
		OptionsService optionsService,
		IEventPublisher eventPublisher,
		IEventSubscriber eventSubscriber
	) {
		this.gameState = gameState;
		this.optionsService = optionsService;
		this.eventPublisher = eventPublisher;
		mineButtons = new Vector<ReadonlyMineButton>(gameState.getCurrentPuzzleMineCount());

		// Allows us to layer components in the same grid cell.
		setLayout(new GridBagLayout());

		var c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
		c.fill = GridBagConstraints.NONE;
		add(continueButton(), c);
		
        c.fill = GridBagConstraints.BOTH;
		add(containerPanel(gameState.getCurrentPuzzleHeight(), gameState.getCurrentPuzzleWidth()), c);

		setupSubscriptions(eventSubscriber);
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

	private PrimaryButton continueButton() {
		var button = new PrimaryButton("Continue Playing", evt -> eventPublisher.publish(new PauseGameEvent()));
		FontChange.setFont(button, 24);
		return button;
	}

	private JPanel containerPanel(int h, int w) {
		var container = new JPanel();
		container.setLayout(new BorderLayout(0, 0));
		minePanel = new JPanel();

		// GridLayout goes by row, column
		minePanel.setLayout(new GridLayout(h, w));

		addMines(h, w);
		container.add(minePanel, BorderLayout.CENTER);
		
		return container;
	}

	private void addMines(int h, int w) {
		minePanel.removeAll();
		mineButtons.clear();

		var clickedBgColor = ColorConverter.convert(optionsService.clickedColor());
		var clickedAltBgColor = ColorConverter.convert(optionsService.clickedAltColor());
		var loweredBorder = BorderConverter.convert(optionsService.loweredBorder());

		// Set the mine panel background, since this might change on refresh.
		minePanel.setBackground(clickedBgColor);

		// Add mines for each position
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				var mb = new ReadonlyMineButton(x, y, loweredBorder, clickedBgColor, clickedAltBgColor);
				mineButtons.add(mb);
				minePanel.add(mb);
			}
		}
	}

	private void setupSubscriptions(IEventSubscriber eventSubscriber) {
		eventSubscriber.subscribe(ResetMinePanelEvent.class, event -> {
			var layout = (GridLayout)minePanel.getLayout();

			layout.setRows(event.h);
			layout.setColumns(event.w);
			addMines(event.h, event.w);
			
			revalidate();
			repaint();
		});
	}
}