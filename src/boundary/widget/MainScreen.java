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
import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainScreen extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JTabbedPane tab = null;

	Panel1 areaPanel = null;

	Panel2 igaPanel = null;

	Panel3 resultPanel = null;

	Panel0 setupPanel = null;

	private JScrollPane scroll = null;

	private JTextArea infoArea = null;

	private FileWriter fw;

	/**
	 * This is the default constructor
	 */
	public MainScreen() {
		super();
		initialize();
		try {
			fw = new FileWriter("info.txt");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Can't open file: info.txt");
			e.printStackTrace();
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(1024, 768);
		this.setContentPane(getJContentPane());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent arg0) {
			}

			public void windowClosed(WindowEvent arg0) {
				try {
					fw.close();
				} catch (IOException e) {
				}
			}

			public void windowClosing(WindowEvent arg0) {
			}

			public void windowDeactivated(WindowEvent arg0) {
			}

			public void windowDeiconified(WindowEvent arg0) {
			}

			public void windowIconified(WindowEvent arg0) {
			}

			public void windowOpened(WindowEvent arg0) {
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBackground(Color.white);
			jContentPane.add(getTab(), BorderLayout.CENTER);
			jContentPane.add(getScroll(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes tab
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTab() {
		if (tab == null) {
			tab = new JTabbedPane();
			tab.setBackground(Color.white);
			tab.setFont(new Font("Dialog", Font.BOLD, 12));
			tab.addTab("Setup", getSetupPanel());
			tab.addTab("Area selection", getAreaJPanel());
			tab.addTab("IGA", getIGAJPanel());
			tab.addTab("Result", getResultJPanel());
			tab.setEnabledAt(1, false);
			tab.setEnabledAt(2, false);
			tab.setEnabledAt(3, false);
		}
		return tab;
	}

	private JPanel getSetupPanel() {
		if (setupPanel == null) {
			setupPanel = new Panel0(this);
		}
		return setupPanel;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAreaJPanel() {
		if (areaPanel == null) {
			areaPanel = new Panel1(this);
		}
		return areaPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getIGAJPanel() {
		if (igaPanel == null) {
			igaPanel = new Panel2(this);
		}
		return igaPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getResultJPanel() {
		if (resultPanel == null) {
			resultPanel = new Panel3(this);
		}
		return resultPanel;
	}

	public void nextStep(int id) {
		switch (id) {
		case 0:
			tab.setEnabledAt(1, true);
			tab.setEnabledAt(2, false);
			tab.setEnabledAt(3, false);
			tab.setSelectedIndex(1);
			break;
		case 1:
			tab.setEnabledAt(1, false);
			tab.setEnabledAt(2, true);
			tab.setEnabledAt(3, false);
			tab.setSelectedIndex(2);
			break;
		case 2:
			tab.setEnabledAt(1, false);
			tab.setEnabledAt(2, true);
			tab.setEnabledAt(3, true);
			tab.setSelectedIndex(3);
			break;
		default:
		}
	}

	/**
	 * This method initializes scroll
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getScroll() {
		if (scroll == null) {
			scroll = new JScrollPane();
			scroll.setOpaque(false);
			scroll.setViewportView(getInfoArea());
		}
		return scroll;
	}

	/**
	 * This method initializes infoArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getInfoArea() {
		if (infoArea == null) {
			infoArea = new JTextArea();
			infoArea.setRows(5);
		}
		return infoArea;
	}

	public void setInfo(String info) {

		try {
			fw.write(info + System.getProperty("line.separator"));
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		infoArea.append("\t" + info + System.getProperty("line.separator"));
	}

	public void setfileName(String name) {
		resultPanel.setfileName(name);
	}

	public void setScale(float p_width, float d_width) {
		resultPanel.setScale(p_width, d_width);
	}
}
