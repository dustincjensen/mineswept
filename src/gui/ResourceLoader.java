package gui;

import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import models.Resource;

/**
 * Loads all of the icons that the application will need.
 */
public class ResourceLoader {
    private Map<Resource, ImageIcon> iconMap;

    public ResourceLoader() {
        try {
            iconMap = new HashMap<Resource, ImageIcon>();
            iconMap.put(Resource.BombHint, imageIcon("/icons/bomb.png"));
            iconMap.put(Resource.Clock, imageIcon("/icons/clock.png"));
            iconMap.put(Resource.Flag, imageIcon("/icons/flag-24.png"));
			iconMap.put(Resource.FlagHint, imageIcon("/icons/flagHint-24.png"));
			iconMap.put(Resource.Mine, imageIcon("/icons/mrBomb.png"));
			iconMap.put(Resource.MineHint, imageIcon("/icons/smiley-wink-32.png"));
			iconMap.put(Resource.MineWrong, imageIcon("/icons/mrBombWrong.png"));
            iconMap.put(Resource.SmileyCool, imageIcon("/icons/smiley-cool.png"));
            iconMap.put(Resource.SmileyHappy, imageIcon("/icons/smiley-happy.png"));
            iconMap.put(Resource.SmileyPaused, imageIcon("/icons/smiley-paused.png"));
            iconMap.put(Resource.SmileyRecord, imageIcon("/icons/smiley-record.png"));
            iconMap.put(Resource.SmileySad, imageIcon("/icons/smiley-sad.png"));
            iconMap.put(Resource.SmileySurprised, imageIcon("/icons/smiley-surprised.png"));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Returns an image icon for the specified resource.
     * 
     * @param resource the resource for which to return the image icon.
     * @return The image icon associated with the resource.
     */
    public ImageIcon get(Resource resource) {
        return iconMap.get(resource);
    }

    private ImageIcon imageIcon(String path) {
        return new ImageIcon(getClass().getResource(path));
    }
}