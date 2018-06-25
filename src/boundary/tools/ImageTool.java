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
package boundary.tools;

import java.awt.image.BufferedImage;

public class ImageTool {
	/**
	 * This method copy BufferedImage into new BufferedImage and return it
	 * 
	 * @return java.awt.image.BufferedImage
	 */
	public static BufferedImage getCopyedImage(BufferedImage bfImage) {
		BufferedImage image = null;
		if (bfImage != null) {
			image = new BufferedImage(bfImage.getWidth(), bfImage.getHeight(), BufferedImage.TYPE_INT_RGB);

			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					image.setRGB(x, y, bfImage.getRGB(x, y));
				}
			}

		}
		System.gc();
		return image;
	}
}
