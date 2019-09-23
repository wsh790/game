package cn.wsh.game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * 通过路径获得图片
 * @author 16572
 *
 */
public class GameUtil {
	private GameUtil() {
	}

	public static Image getImage(String path) {
		BufferedImage bi = null;
		URL url = GameUtil.class.getClassLoader().getResource(path);
		try {
			bi = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bi;
	}

}
