package ui.layout.header.mineCount;

import events.GetHintEvent;
import events.IEventPublisher;
import events.IEventSubscriber;
import events.UpdateMineCountEvent;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Renders the mine count panel in the header.
 */
@SuppressWarnings("serial")
public class MineCount extends JPanel {
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	private MineCountButton mineCountButton;

	public MineCount(
		int initialMineCount,
		ImageIcon bombHintIcon,
		IEventPublisher eventPublisher,
		IEventSubscriber eventSubscriber
	) {
		this.eventPublisher = eventPublisher;
		this.eventSubscriber = eventSubscriber;
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setOpaque(false);
		setupPanel(initialMineCount, bombHintIcon);
		setupSubscriptions();
	}

	private void setupPanel(int initialMineCount, ImageIcon bombHintIcon) {
		mineCountButton = new MineCountButton(initialMineCount, bombHintIcon, evt -> {
			eventPublisher.publish(new GetHintEvent());
		});
		add(mineCountButton, BorderLayout.LINE_START);
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(UpdateMineCountEvent.class, event -> {
			mineCountButton.setMineCount(event.mineCount);
		});
	}
}