package ui.layout.header;

import events.GetHintEvent;
import events.IEventPublisher;
import events.IEventSubscriber;
import events.UpdateMineCountEvent;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import state.GameState;
import ui.utils.FontChange;

/**
 * Renders the mine count panel in the header.
 */
public class MineCount extends JPanel {
	private GameState gameState;
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	private ImageIcon bombHintIcon;
	private JLabel mineCount;

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

		setLayout(new FlowLayout(FlowLayout.LEADING));
		setupPanel();
		setupSubscriptions();
	}

	private void setupPanel() {
		var button = new JButton(bombHintIcon);
		button.setToolTipText("Get a hint");
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.addActionListener(evt -> {
			eventPublisher.publish(new GetHintEvent());
		});
		add(button);

		mineCount = new JLabel("");
		mineCount.setText("" + gameState.getMineCount());
		FontChange.setFont(mineCount, 24);
		add(mineCount);
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(UpdateMineCountEvent.class, (event) -> {
			mineCount.setText("" + gameState.getMineCount());
		});
	}
}