/*
Copyright (c) 2009 Che-Hung Lin

This file is part of Color Planning Consultation System.

Color Planning Consultation System is free software: you can redistribute it and/or
modify it under the terms of the GNU General Public License as published by the
Free Software Foundation, either version 3 of the License, or (at your option)
any later version.

Color Planning Consultation System is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
details.

You should have received a copy of the GNU General Public License along with
Color Planning Consultation System. If not, see <https://www.gnu.org/licenses/>.
*/
package boundary.widget;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

import boundary.algorithm.AreaEngine;
import boundary.unit.Area;
import boundary.unit.AreaSet;
import boundary.unit.PixelNode;

public class PicPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage bfImage = null;

	private double scaleRate;

	/**
	 * This is the default constructor
	 */
	public PicPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setDoubleBuffered(true);
		this.setLayout(null);
		scaleRate = 1;
	}

	public void setPIC(BufferedImage bfImage) {
		this.bfImage = bfImage;
		repaint();
	}

	public void setGLImage(BufferedImage gl) {

		if (gl == null)
			return;

		for (int y = 0; y < gl.getHeight(); y++) {
			for (int x = 0; x < gl.getWidth(); x++) {

				int rgb1 = bfImage.getRGB(x, y);
				int rgb2 = gl.getRGB(x, y);

				int r1 = (rgb1 & 16711680) >> 16;
				int g1 = (rgb1 & 65280) >> 8;
				int b1 = (rgb1 & 255);

				int r2 = (rgb2 & 16711680) >> 16;
				int g2 = (rgb2 & 65280) >> 8;
				int b2 = (rgb2 & 255);

				float[] hsb1 = Color.RGBtoHSB(r1, g1, b1, null);
				float[] hsb2 = Color.RGBtoHSB(r2, g2, b2, null);

				float nwe_H = hsb1[0];
				float nwe_S = hsb1[1];
				float nwe_B = hsb2[2];

				int new_rgb = Color.HSBtoRGB(nwe_H, nwe_S, nwe_B);
				bfImage.setRGB(x, y, new_rgb);

			}
		}

		repaint();
	}

	public BufferedImage getPIC() {
		return bfImage;
	}

	public void setColors(AreaEngine areaengine, Vector<AreaSet> areasets, int areaindex, Color c) {

		AreaSet set = areasets.get(areaindex);
		// System.out.print("area set:" + areaindex + " contain areas: ");
		for (Area ar : set.values().toArray(new Area[0])) {
			// System.out.print(ar.id + " ");

			PixelNode next = ar.start;
			do {
				if (next.left == false) {
					for (int x = next.col_index; x < next.next.col_index; x++) {
						bfImage.setRGB(x, next.row_index, c.getRGB());
					}

					PixelNode left = next.next;
					while (left.next != null) {
						left = left.next;
						Area a = areaengine.getArea(left.col_index + 1, left.row_index);
						if (a != null && a.id == ar.id && left.next != null) {
							for (int x = left.col_index; x < left.next.col_index; x++) {
								bfImage.setRGB(x, left.row_index, c.getRGB());
							}
						}
						if (left.next != null) {
							left = left.next;
						}
					}

				}

				next = next.link;
			} while (next != ar.start);
		}
		// System.out.println();
		repaint();
	}

	public void setRate(double rate) {
		scaleRate = rate;
	}

	// paint this panel
	public void paint(Graphics g) {

		if (bfImage != null) {
			Graphics2D g2 = (Graphics2D) g;
			g2.clearRect(0, 0, this.getWidth(), this.getHeight());
			g2.scale(scaleRate, scaleRate);
			g2.drawImage(bfImage, 0, 0, bfImage.getWidth(), bfImage.getHeight(), null);
		}
	}
}
