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
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import boundary.algorithm.AreaEngine;
import boundary.algorithm.ReadExcel;
import boundary.tools.ImageTool;
import boundary.unit.AreaSet;
import boundary.unit.ColorSet;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.Font;

public class Panel3 extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage bfImage = null;

	private JScrollPane js_display = null;
	private PicPanel panel_result = null;
	private MainScreen parent = null;

	private String filename;
	private float p_pixl_width;
	private float p_width;
	private float d_width;
	private JButton JB_GL = null;

	/**
	 * This is the default constructor
	 */
	public Panel3(MainScreen parent) {
		super();
		this.parent = parent;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(784, 500));
		this.setBackground(Color.white);
		this.add(getJs_display(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes js_display
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJs_display() {
		if (js_display == null) {
			js_display = new JScrollPane();
			js_display.setOpaque(false);
			js_display.setAutoscrolls(true);
			js_display.setViewportView(getPanel_result());
			js_display.setBackground(Color.white);
		}
		return js_display;
	}

	/**
	 * This method initializes panel_result
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanel_result() {
		if (panel_result == null) {
			panel_result = new PicPanel();
			panel_result.add(getJB_GL(), null);
		}
		return panel_result;
	}

	public void setData(BufferedImage Image, AreaEngine area, Vector<AreaSet> set, ColorSet color) {
		bfImage = Image;
		if (bfImage != null) {
			BufferedImage image = ImageTool.getCopyedImage(bfImage);

			parent.setInfo("�p�⤽��  :");
			parent.setInfo("���~��ڤؤocm / �e���W��Ϊ��|cm  =  �վ�᪺�Ϥ�Pixl / �e���W���Pixl");
			parent.setInfo("���~��ڤؤocm = " + p_width);
			parent.setInfo("�e���W��Ϊ��|cm = " + d_width);
			parent.setInfo("�e���W���Pixl = 213");
			parent.setInfo("�D�o�վ�᪺�Ϥ�Pixl = �i" + p_pixl_width + "�j");
			parent.setInfo("�ثe��J�Ϥ��ؤoPixl = " + Image.getWidth());

			parent.setInfo("�N�ثe�Ϥ��ؤo�վ㦨�ؼФؤo��� = �i" + p_pixl_width / Image.getWidth() + "�j");

			// �Y�O���ݭn���ܵ��G�Ϥ��j�p�A�N�U���{���X����
			panel_result.setRate(p_pixl_width / Image.getWidth());

			// �Y�O�ݭn���ܵ��G�Ϥ��j�p�A�N�U���{���X����
			// panel_result.setRate(1);

			panel_result.setPIC(image);

			panel_result.setSize(new Dimension(image.getWidth(), image.getHeight()));
			panel_result.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));

			Color c[] = color.getColorSet();
			parent.setInfo("Pantone:");
			for (int j = 0; j < set.size(); j++) {
				parent.setInfo(
						"R=" + c[j].getRed() + "\tG=" + c[j].getGreen() + "\tB=" + c[j].getBlue() + "\t=>\tPantone No. "
								+ ReadExcel.RGB2HVC(c[j].getRed(), c[j].getGreen(), c[j].getBlue()).getPantone());
				panel_result.setColors(area, set, j, c[j]);
			}
		}
	}

	public void setfileName(String name) {
		this.filename = name;
	}

	public void setScale(float p_width, float d_width) {
		this.p_pixl_width = (p_width / d_width) * 213;
		this.p_width = p_width;
		this.d_width = d_width;
	}

	/**
	 * This method initializes JB_GL
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJB_GL() {
		if (JB_GL == null) {
			JB_GL = new JButton();
			JB_GL.setText("Add Gray level");
			JB_GL.setFont(new Font("Dialog", Font.BOLD, 14));
			JB_GL.setBounds(new Rectangle(10, 10, 150, 30));
			JB_GL.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BufferedImage gl = null;
					try {
						StringBuffer sb = new StringBuffer(filename);
						sb.insert(sb.lastIndexOf("."), "_GL");
						parent.setInfo(sb.toString());
						gl = ImageIO.read(new File(sb.toString()));
						panel_result.setGLImage(gl);
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(null, "Can not read Glay level file", "WARNING MESSAGE",
								JOptionPane.WARNING_MESSAGE);
						// e.printStackTrace();
					}
				}
			});
		}
		return JB_GL;
	}
}
