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

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;

public class Panel0 extends JPanel {

	private static final long serialVersionUID = 1L;
	private MainScreen parent = null;
	private Panel_bg line = null;
	private JButton next = null;
	private JTextField text_1 = null;
	private JTextField text_2 = null;

	/**
	 * This is the default constructor
	 */
	public Panel0(MainScreen parent) {
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
		this.setSize(800, 272);
		this.setLayout(null);
		this.add(getLine(), null);
		this.setBackground(Color.white);
		this.addComponentListener(new java.awt.event.ComponentListener() {
			public void componentResized(java.awt.event.ComponentEvent e) {
				line.setLocation((getWidth() - line.getWidth()) / 2, (getHeight() - line.getHeight()) / 2);
			}

			public void componentMoved(java.awt.event.ComponentEvent e) {
			}

			public void componentShown(java.awt.event.ComponentEvent e) {
			}

			public void componentHidden(java.awt.event.ComponentEvent e) {
			}
		});
	}

	/**
	 * This method initializes line
	 * 
	 * @return javax.swing.JPanel
	 */
	private Panel_bg getLine() {
		if (line == null) {
			line = new Panel_bg();
			line.setLayout(null);
			line.setPreferredSize(line.getPreferredSize());
			line.add(getJTextField(), null);
			line.add(getJTextField1(), null);
			line.add(getNext(), null);
			line.setLocation((this.getWidth() - line.getWidth()) / 2, (this.getHeight() - line.getHeight()) / 2);
		}
		return line;
	}

	/**
	 * This method initializes next
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getNext() {
		if (next == null) {
			next = new JButton();
			next.setText("Next");
			next.setFont(new Font("Dialog", Font.BOLD, 14));
			next.setOpaque(false);
			next.setBounds(700, 630, 200, 30);
			next.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						float d_width = Float.parseFloat(text_1.getText());
						float p_width = Float.parseFloat(text_2.getText());
						parent.setScale(p_width, d_width);
						parent.nextStep(0);
					} catch (NumberFormatException ex) {
					}
				}
			});
		}
		return next;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (text_1 == null) {
			text_1 = new JTextField("40");
			text_1.setBounds(700, 480, 200, 30);
			text_1.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return text_1;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (text_2 == null) {
			text_2 = new JTextField("140");
			text_2.setBounds(700, 585, 200, 30);
			text_2.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return text_2;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
