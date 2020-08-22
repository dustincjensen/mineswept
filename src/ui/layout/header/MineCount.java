package ui.layout.header;

import events.GetHintEvent;
import events.IEventPublisher;
import events.IEventSubscriber;
import events.UpdateMineCountEvent;
import events.StartClockTimerEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import state.GameState;
import ui.components.button.LightButton;
import ui.utils.FontChange;

/**
 * Renders the mine count panel in the header.
 */
@SuppressWarnings("serial")
public class MineCount extends JPanel {
	private GameState gameState;
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	private ImageIcon bombHintIcon;
	private LightButton mineCountButton;

	public MineCount(
		GameState gameState,
		IEventPublisher eventPublisher,
		IEventSubscriber eventSubscriber,
		ImageIcon bombHintIcon
	) {
		this.gameState = gameState;
		this.eventPublisher = eventPublisher;
		this.eventSubscriber = eventSubscriber;
		this.bombHintIcon = bombHintIcon;

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setBackground(null);
		setupPanel();
		setupSubscriptions();
	}

	private void setupPanel() {
		mineCountButton = new LightButton(getFormattedMineCount(), true, evt -> {
			// TODO this happens in mineButton as well, this should probably occur in the events
			// But we don't use eventPublisher in the events currently.
			gameState.setGameStarted(true);
			eventPublisher.publish(new StartClockTimerEvent());
			eventPublisher.publish(new GetHintEvent());
		});
		mineCountButton.setIcon(bombHintIcon);
		mineCountButton.setIconTextGap(20);
		mineCountButton.setHorizontalAlignment(SwingConstants.LEFT);
		mineCountButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		mineCountButton.setPreferredSize(new Dimension(156, mineCountButton.getHeight()));

		mineCountButton.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				mineCountButton.setText(String.format("%1$7s", "Hint"));
			}
		
			public void focusLost(FocusEvent e) {
				mineCountButton.setText(getFormattedMineCount());
			}
		});
		mineCountButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				mineCountButton.setText(String.format("%1$7s", "Hint"));
			}
		
			public void mouseExited(MouseEvent evt) {
				if (!mineCountButton.isFocusOwner()) {
					mineCountButton.setText(getFormattedMineCount());
				}
			}
		});

		FontChange.setFont(mineCountButton, 24);
		add(mineCountButton, BorderLayout.LINE_START);
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(UpdateMineCountEvent.class, event -> {
			if (!mineCountButton.isFocusOwner()) {
				mineCountButton.setText(getFormattedMineCount());
			}
		});
	}

	private String getFormattedMineCount() {
		return String.format("%1$8s", String.format("%02d", gameState.getMineCount()));
	}
}