package events;

import gui.Resource;

public class SetResetButtonIconEvent {
	public Resource resource;

	public SetResetButtonIconEvent(Resource iconResource) {
		resource = iconResource;
	}
}