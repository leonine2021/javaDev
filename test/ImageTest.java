import com.aoli.tank.ImageUtil;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageTest {

    @Test
    public void testLoadImage() {
        try {
            BufferedImage image = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            assertNotNull(image);
        }catch  (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRotateImage() {
        try {
            BufferedImage tankL = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            tankL = ImageUtil.rotateImage(tankL, 90);
            assertNotNull(tankL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}