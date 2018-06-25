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

import javax.swing.*;

public class Panel_bg extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image img = new ImageIcon("bg.jpg").getImage();

	/**
	 * This is the default constructor
	 */
	public Panel_bg() {
		super();
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		this.setSize(img.getWidth(this), img.getHeight(this));
		this.setMinimumSize(new Dimension(img.getWidth(this), img.getHeight(this)));
		this.setPreferredSize(new Dimension(img.getWidth(this), img.getHeight(this)));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void paintComponent(Graphics g) {
		setBackground(Color.WHITE);

		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, 0, 70, this);

	}
} // @jve:decl-index=0:visual-constraint="10,10"
