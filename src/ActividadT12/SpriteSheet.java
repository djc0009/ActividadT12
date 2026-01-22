package ActividadT12;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet {
    private BufferedImage sheet;
    private int frameWidth, frameHeight;

    public SpriteSheet(String ruta, int cols, int rows) throws IOException {
        sheet = ImageIO.read(new File(ruta));
        frameWidth = sheet.getWidth() / cols;
        frameHeight = sheet.getHeight() / rows;
    }

    public BufferedImage getFrame(int col, int row) {
        return sheet.getSubimage(col * frameWidth, row * frameHeight, frameWidth, frameHeight);
    }
}
