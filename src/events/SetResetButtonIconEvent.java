package events;

import models.Resource;

public class SetResetButtonIconEvent {
	public Resource resource;

	public SetResetButtonIconEvent(Resource iconResource) {
		resource = iconResource;
	}
}