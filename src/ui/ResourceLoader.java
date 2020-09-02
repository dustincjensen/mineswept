package ui;

import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import models.Resource;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Loads all of the icons that the application will need.
 */
public class ResourceLoader {
    private Map<Resource, ImageIcon> iconMap;

    public ResourceLoader() {
        try {
            iconMap = new HashMap<Resource, ImageIcon>();
            iconMap.put(Resource.RadioButtonDefault, recolorBlackIcon("/icons/components/radioButtonDefault.png", "#444444"));
            iconMap.put(Resource.RadioButtonSelected, recolorBlackIcon("/icons/components/radioButtonSelected.png", "#007bff"));
            iconMap.put(Resource.BombHint, imageIcon("/icons/bomb.png"));
            iconMap.put(Resource.Clock, imageIcon("/icons/clock.png"));
            iconMap.put(Resource.Flag, imageIcon("/icons/flag-24.png"));
			iconMap.put(Resource.Mine, imageIcon("/icons/mrBomb.png"));
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

    private ImageIcon recolorBlackIcon(String path, String hexCode) {
        try {
            var img = colorImage(
                ImageIO.read(getClass().getResource(path)),
                hexCode);
            return new ImageIcon(img);
        } catch (IOException ex) {
            return null;
        }
    }

    // https://stackoverflow.com/a/16054956/718285
    private BufferedImage colorImage(BufferedImage image, String hexCode) {
        var width = image.getWidth();
        var height = image.getHeight();
        var raster = image.getRaster();
        var colorToUse = Color.decode(hexCode);

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                var pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = colorToUse.getRed();
                pixels[1] = colorToUse.getGreen();
                pixels[2] = colorToUse.getBlue();
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }
}