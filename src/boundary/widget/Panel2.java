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

import boundary.algorithm.Aesthetic;
import boundary.algorithm.AreaEngine;
import boundary.algorithm.GeneticAlgorithm;
import boundary.algorithm.ReadExcel;
import boundary.tools.ImageTool;
import boundary.unit.AreaSet;
import boundary.unit.ColorSet;
import boundary.unit.HVC;

import java.awt.image.BufferedImage;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JRadioButton;
import java.awt.Rectangle;

public class Panel2 extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;

	private JPanel Control = null;
	private JPanel Display = null;

	private JScrollPane jS[] = new JScrollPane[6];
	private JSlider score[] = new JSlider[6];
	private JPanel back[] = new JPanel[6];
	private JPanel title[] = new JPanel[6];
	private JLabel index[] = new JLabel[6];
	private JSlider mutrate = null;
	private JTextArea mutation = null;

	private JButton jb_set = null;
	private JButton jb_output = null;

	private PicPanel panel[] = new PicPanel[6];
	private Vector<AreaSet> areaSets = null;
	private BufferedImage bfImage = null;
	private AreaEngine areaEngine = null;
	private MainScreen parent = null;
	private GeneticAlgorithm ga = null;

	private ColorSet set[];

	private int count = 0;

	private JRadioButton radioGA = null;

	private JRadioButton radioIGA = null;

	private ButtonGroup bg = null;

	/**
	 * This is the default constructor
	 */
	public Panel2(MainScreen parent) {
		super();
		this.parent = parent;
		initialize();
		ga = new GeneticAlgorithm();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(784, 600));
		this.add(getDisplay(), BorderLayout.CENTER);
		this.add(getControl(), BorderLayout.EAST);

		this.setBackground(Color.white);

	}

	public void setData(BufferedImage Image, AreaEngine area, Vector<AreaSet> set) {
		bfImage = Image;
		if (Image != null) {
			for (int i = 0; i < panel.length; i++) {
				// resize image panel
				double rate = (double) jS[i].getWidth() / (bfImage.getWidth() + 30);
				int width = (int) (bfImage.getWidth() * rate);
				int height = (int) (bfImage.getHeight() * rate);
				panel[i].setRate(rate);
				panel[i].setPIC(bfImage);
				panel[i].setSize(new Dimension(width, height));
				panel[i].setPreferredSize(new Dimension(width, height));
			}
		}
		areaEngine = area;
		areaSets = set;
		ga.setAreaNumber(areaSets.size());
		draw();
	}

	public void setGroupData(boolean[] g) {
		ga.setGroupData(g);
	}

	/**
	 * This method initializes Display
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getDisplay() {
		if (Display == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(2);
			Display = new JPanel();
			Display.setLayout(gridLayout1);
			Display.setBackground(Color.white);

			for (int i = 0; i < jS.length; i++) {
				panel[i] = new PicPanel();
				score[i] = new JSlider();
				score[i].setMinimum(0);//
				score[i].setMaximum(2);//
				score[i].setValue(2);
				score[i].addChangeListener(this);
				jS[i] = new JScrollPane();
				jS[i].setOpaque(false);
				jS[i].setAutoscrolls(true);
				jS[i].setPreferredSize(new Dimension(200, 300));
				jS[i].setViewportView(panel[i]);
				index[i] = new JLabel(String.format(" No. " + i + " Score: %1$02d", score[i].getValue()));
				index[i].setFont(new Font("Dialog", Font.BOLD, 12));
				title[i] = new JPanel();
				title[i].setLayout(new BorderLayout());
				title[i].add(score[i], BorderLayout.CENTER);
				title[i].add(index[i], BorderLayout.WEST);
				back[i] = new JPanel();
				back[i].setOpaque(false);
				back[i].setLayout(new BorderLayout());
				back[i].add(title[i], BorderLayout.NORTH);
				back[i].add(jS[i], BorderLayout.CENTER);
				Display.add(back[i], null);
			}

		}
		return Display;
	}

	/**
	 * This method initializes Control
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getControl() {
		if (Control == null) {
			Control = new JPanel();
			Control.setLayout(null);
			Control.setPreferredSize(new Dimension(90, 10));
			Control.add(getJb_set(), null);
			Control.add(getJb_output(), null);
			Control.add(getMutrate(), null);
			Control.add(getMutation(), null);
			Control.add(getRadioGA(), null);
			Control.add(getRadioIGA(), null);
			bg = new ButtonGroup();
			bg.add(getRadioGA());
			bg.add(getRadioIGA());
		}
		return Control;
	}

	/**
	 * This method initializes jb_set
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJb_set() {
		if (jb_set == null) {
			jb_set = new JButton("IGA");
			jb_set.setBounds(new Rectangle(5, 10, 80, 30));
			jb_set.setFont(new Font("Dialog", Font.BOLD, 14));
			jb_set.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					nextColors();
				}
			});
		}
		return jb_set;
	}

	/**
	 * This method initializes jb_next
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJb_output() {
		if (jb_output == null) {
			jb_output = new JButton("Output");
			jb_output.setBounds(new Rectangle(5, 215, 80, 30));
			jb_output.setFont(new Font("Dialog", Font.BOLD, 14));
			jb_output.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					output();
				}
			});
		}
		return jb_output;
	}

	/**
	 * This method initializes mutrate
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getMutrate() {
		if (mutrate == null) {
			mutrate = new JSlider();
			mutrate.setBounds(new Rectangle(5, 180, 80, 30));
			mutrate.setMinimum(1);
			mutrate.setMaximum(10);
			mutrate.setValue(1);
			mutrate.addChangeListener(this);
		}
		return mutrate;
	}

	/**
	 * This method initializes mutation
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getMutation() {
		if (mutation == null) {
			mutation = new JTextArea("Mutation\nrate\n0.01");
			mutation.setEditable(false);
			mutation.setOpaque(false);
			mutation.setBounds(new Rectangle(5, 110, 80, 70));
			mutation.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return mutation;
	}

	private void output() {
		String text = JOptionPane.showInputDialog("Please input the graph number you selected.");
		if (text != null) {
			try {
				int index = Integer.parseInt(text);
				if (index > 0 && index < 7) {
					parent.resultPanel.setData(bfImage, areaEngine, areaSets, set[index]);
					parent.nextStep(2);
				}
			} catch (NullPointerException e) {
			} catch (NumberFormatException e) {
			}
		}
	}

	private void draw() {
		set = ga.getSixColorSet();
		for (int i = 0; i < set.length; i++) {
			BufferedImage image = ImageTool.getCopyedImage(bfImage);
			panel[i].setPIC(image);
			Color c[] = set[i].getColorSet();

			for (int j = 0; j < areaSets.size(); j++) {
				panel[i].setColors(areaEngine, areaSets, j, c[j]);
			}

		}
	}

	private void nextColors() {
		if (jb_set.getText().equals("IGA")) {
			ga.choose(score[0].getValue(), score[1].getValue(), score[2].getValue(), score[3].getValue(),
					score[4].getValue(), score[5].getValue());
			draw();
		} else {
			parent.setInfo("Start GA algorithm");
			Aesthetic aesthetic = new Aesthetic();

			int end = 15, select = 0;
			float score[] = new float[6];
			// fifteen generation
			for (int i = 0; i < end; i++) {
				parent.setInfo("\nGeneration " + i);
				// calculate score
				for (int j = 0; j < set.length; j++) {

					Color[] colors = set[j].getColorSet();
					HVC[] hvc = new HVC[colors.length];
					for (int k = 0; k < colors.length; k++) {
						hvc[k] = ReadExcel.RGB2HVC(colors[k].getRed(), colors[k].getGreen(), colors[k].getBlue());
					}

					float[][] hvcs = new float[hvc.length][3];
					for (int k = 0; k < hvcs.length; k++) {
						hvcs[k][0] = (float) hvc[k].getH();
						hvcs[k][1] = (float) hvc[k].getV();
						hvcs[k][2] = (float) hvc[k].getC();
					}

					score[j] = aesthetic.getAesthetic(hvcs);

					parent.setInfo("PIC " + j + " M = order(data?) / complexity(data?) = " + score[j]);
				}

				// input to GA
				ga.choose((int) (score[0] * 10), (int) (score[1] * 10), (int) (score[2] * 10), (int) (score[3] * 10),
						(int) (score[4] * 10), (int) (score[5] * 10));
				set = ga.getSixColorSet();

				if (i == end - 1) {
					float max = 0;
					for (int j = 0; j < score.length; j++) {
						if (score[j] >= max) {
							select = j;
							max = score[j];
						}
					}
				}
			}

			parent.setInfo("Result: pic " + select);
			draw();
			parent.resultPanel.setData(bfImage, areaEngine, areaSets, set[select]);
			parent.nextStep(2);
			count = 2;
		}

		count++;
		parent.setInfo("Generation: " + count);
		if (count % 3 == 0) {
			int degree = 1;
			boolean in = true;
			while (in) {
				try {
					String text = JOptionPane.showInputDialog("Satisfaction degrees of color scheme.(1-10)");
					degree = Integer.parseInt(text);
					if (degree > 0 && degree < 11)
						in = false;
				} catch (NumberFormatException e) {
				}
			}
			parent.setInfo("Satisfaction degrees of color scheme.(1-10) = " + degree);
		}

	}

	public void stateChanged(ChangeEvent e) {
		int i = 0;
		if (e.getSource() == score[0]) {
			i = 0;
		} else if (e.getSource() == score[1]) {
			i = 1;
		} else if (e.getSource() == score[2]) {
			i = 2;
		} else if (e.getSource() == score[3]) {
			i = 3;
		} else if (e.getSource() == score[4]) {
			i = 4;
		} else if (e.getSource() == score[5]) {
			i = 5;
		} else if (e.getSource() == mutrate) { // �]�w���ܲv
			mutation.setText("Mutation\nrate\n" + (float) mutrate.getValue() / 100);
			ga.setRate((float) mutrate.getValue() / 100);
		}
		index[i].setText(String.format(" No. " + i + " Score: %1$02d", score[i].getValue()));
	}

	/**
	 * This method initializes radioGA
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRadioGA() {
		if (radioGA == null) {
			radioGA = new JRadioButton();
			radioGA.setText("GA");
			radioGA.setBounds(new Rectangle(5, 50, 70, 23));
			radioGA.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (radioGA.isSelected()) {
						jb_set.setText("GA");
					}
				}
			});
		}
		return radioGA;
	}

	/**
	 * This method initializes radioIGA
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRadioIGA() {
		if (radioIGA == null) {
			radioIGA = new JRadioButton();
			radioIGA.setText("IGA");
			radioIGA.setSelected(true);
			radioIGA.setBounds(new Rectangle(5, 75, 70, 23));
			radioIGA.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (radioIGA.isSelected()) {
						jb_set.setText("IGA");
					}
				}
			});
		}
		return radioIGA;
	}
}
