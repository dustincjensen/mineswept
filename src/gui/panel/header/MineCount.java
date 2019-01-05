package gui.panel.header;

import events.GetHintEvent;
import events.IEventPublisher;
import events.IEventSubscriber;
import events.UpdateMineCountEvent;
import gui.FontChange;
import gui.ResourceLoader;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.Resource;
import state.GameState;

/**
 * Renders the mine count panel in the header.
 */
public class MineCount extends JPanel {
	private GameState gameState;
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	private ResourceLoader resourceLoader;
	private JButton mineIcon;
	private JLabel mineCount;

	public MineCount(
		GameState state,
		IEventPublisher publisher,
		IEventSubscriber subscriber,
		ResourceLoader loader
	) {
		gameState = state;
		eventPublisher = publisher;
		eventSubscriber = subscriber;
		resourceLoader = loader;

		setLayout(new FlowLayout(FlowLayout.LEADING));
		setupPanel();
		setupSubscriptions();
	}

	private void setupPanel() {
		mineIcon = new JButton(resourceLoader.get(Resource.BombHint));
		mineIcon.setToolTipText("Get a hint");
		mineIcon.setBorderPainted(false);
		mineIcon.setContentAreaFilled(false);
		mineIcon.addActionListener(evt -> {
			eventPublisher.publish(new GetHintEvent());
		});
		add(mineIcon);

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