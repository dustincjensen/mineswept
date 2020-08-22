package ui.layout.header;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.PauseGameEvent;
import events.SetTimeCountEvent;
import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import state.GameState;
import ui.components.button.LightButton;
import ui.utils.FontChange;

/**
 * Renders the time count in the header.
 */
@SuppressWarnings("serial")
public class TimeCount extends JPanel {
	private GameState gameState;
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	private ImageIcon clockIcon;
	private LightButton clockCountButton;
	private int lastTime = 0;
	private boolean isHovering = false;

	public TimeCount(
		GameState gameState,
		IEventPublisher eventPublisher,
		IEventSubscriber eventSubscriber,
		ImageIcon clockIcon
	) {
		this.gameState = gameState;
		this.eventPublisher = eventPublisher;
		this.eventSubscriber = eventSubscriber;
		this.clockIcon = clockIcon;
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setBackground(null);
		setupPanel();
		setupSubscriptions();
	}

	private void setupPanel() {
		clockCountButton = new LightButton(getFormattedTime(lastTime), true, evt -> {
			eventPublisher.publish(new PauseGameEvent(!gameState.isGamePaused()));
		});
		clockCountButton.setIcon(clockIcon);
		clockCountButton.setIconTextGap(20);
		clockCountButton.setHorizontalAlignment(SwingConstants.RIGHT);
		clockCountButton.setHorizontalTextPosition(SwingConstants.LEFT);
		clockCountButton.setPreferredSize(new Dimension(156, clockCountButton.getHeight()));

		clockCountButton.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				clockCountButton.setText("Pause");
			}
		
			public void focusLost(FocusEvent e) {
				clockCountButton.setText(getFormattedTime(lastTime));
			}
		});
		clockCountButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				isHovering = true;
				clockCountButton.setText("Pause");
			}
		
			public void mouseExited(MouseEvent evt) {
				isHovering = false;
				if (!clockCountButton.isFocusOwner()) {
					clockCountButton.setText(getFormattedTime(lastTime));
				}
			}
		});

		FontChange.setFont(clockCountButton, 24);
		add(clockCountButton, BorderLayout.LINE_END);
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(SetTimeCountEvent.class, event -> {
			lastTime = event.time;
			if (!clockCountButton.isFocusOwner() && !isHovering) {
				clockCountButton.setText(getFormattedTime(lastTime));
			}
		});
	}

	private String getFormattedTime(int time) {
		return String.format("%-7s", String.format("%03d", time));
	}
}