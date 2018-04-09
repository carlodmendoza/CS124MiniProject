package maze;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

public class DrawingCanvas extends Canvas {
	
	public Image roomImg;

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.drawImage(roomImg, 175, 0, this);
	}
	
	public void changeImg(String name) {
		roomImg = Toolkit.getDefaultToolkit().getImage("./assets/rooms/" + name);
		repaint();
	}
}
