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
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.border.TitledBorder;
import boundary.algorithm.*;
import boundary.tools.ImageTool;
import boundary.unit.*;
import java.awt.Rectangle;
import javax.swing.JLabel;

public class Panel1 extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	private JLabel jl_scroll = null;
	private JPanel jp_Controls = null;
	private JPanel jp_areaPanel = null;
	private JPanel jp_areaControl = null;

	private JScrollPane jspanel_pic = null;
	private JScrollPane jspanel_List = null;
	private PicPanel jp_pic = null;

	private JButton jb_open = null;
	private JButton jb_construct = null;
	private JButton jb_drawColor = null;
	private JButton jb_newArea = null;
	private JButton jb_deleteArea = null;
	private JButton jb_next = null;

	private JSlider slider = null;

	private JLabel jl_age = null;
	private JSlider js_age = null;
	private JLabel jl_sex = null;
	private JSlider js_sex = null;
	private JPanel jp_group = null;
	private JButton JB_Cal = null;
	private JList<AreaSet> areaList = null;

	private MainScreen parent = null;
	private BufferedImage Image = null;
	private AreaEngine areaEngine = null;
	private Vector<AreaSet> areaSets = null;

	private JScrollPane scroll = null;

	private JLabel jl_m_fm = null;

	private JLabel jl_old_young = null;

	/**
	 * This is the default constructor
	 */
	public Panel1(MainScreen parent) {
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
		jl_scroll = new JLabel();
		jl_scroll.setText("Gray Level Threshold : 255");
		jl_scroll.setFont(new Font("Dialog", Font.BOLD, 12));
		jl_scroll.setBounds(new Rectangle(20, 235, 162, 15));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		this.setBounds(new Rectangle(0, 0, 1024, 700));
		this.add(getJs_pic(), BorderLayout.CENTER);
		this.add(getScroll(), BorderLayout.EAST);
		setDisable();
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
			scroll.setPreferredSize(new Dimension(260, 668));
			scroll.setViewportView(getControls());
		}
		return scroll;
	}

	/**
	 * This method initializes js_pic
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJs_pic() {
		if (jspanel_pic == null) {
			jspanel_pic = new JScrollPane();
			jspanel_pic.setOpaque(false);
			jspanel_pic.setBorder(BorderFactory.createTitledBorder(null, "Image", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("�s�ө���", Font.BOLD, 12), new Color(1, 1, 1)));
			jspanel_pic.setAutoscrolls(true);
			jspanel_pic.setPreferredSize(new Dimension(200, 300));
			jspanel_pic.setViewportView(getJp_pic());
		}
		return jspanel_pic;
	}

	/**
	 * This method initializes jp_pic
	 * 
	 * @return javax.swing.JPanel
	 */
	private PicPanel getJp_pic() {
		if (jp_pic == null) {
			jp_pic = new PicPanel();
			jp_pic.addMouseListener(this);
		}
		return jp_pic;
	}

	/**
	 * This method initializes Controls
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getControls() {
		if (jp_Controls == null) {
			jl_sex = new JLabel();
			jl_sex.setText("Sex: 0.0");
			jl_sex.setFont(new Font("Dialog", Font.BOLD, 12));
			jl_sex.setBounds(new Rectangle(5, 70, 90, 18));
			jl_age = new JLabel();
			jl_age.setText("Age: 0.0");
			jl_age.setFont(new Font("Dialog", Font.BOLD, 12));
			jl_age.setBounds(new Rectangle(5, 15, 90, 18));
			jp_Controls = new JPanel();
			jp_Controls.setLayout(null);
			jp_Controls.setOpaque(true);
			jp_Controls.setBackground(Color.white);
			jp_Controls.add(getJb_open(), null);
			jp_Controls.add(getJb_construct(), null);
			jp_Controls.add(jl_scroll, null);
			jp_Controls.add(getSlider(), null);
			jp_Controls.add(getAreaPanel(), null);
			jp_Controls.add(getJB_drawColor(), null);
			jp_Controls.add(getNext(), null);
			jp_Controls.add(getJP_01(), null);
			jp_Controls.setPreferredSize(new Dimension(220, 590));
		}
		return jp_Controls;
	}

	/**
	 * This method initializes jb_open
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJb_open() {
		if (jb_open == null) {
			jb_open = new JButton();
			jb_open.setOpaque(false);
			jb_open.setBounds(new Rectangle(15, 190, 160, 30));
			jb_open.setFont(new Font("Dialog", Font.BOLD, 14));
			jb_open.setText("Open Image File");
			jb_open.setEnabled(false);
			jb_open.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					open();
				}
			});
		}
		return jb_open;
	}

	/**
	 * This method initializes jb_construct
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJb_construct() {
		if (jb_construct == null) {
			jb_construct = new JButton();
			jb_construct.setText("Outline");
			jb_construct.setBounds(new Rectangle(15, 270, 160, 30));
			jb_construct.setFont(new Font("Dialog", Font.BOLD, 14));
			jb_construct.setOpaque(false);
			jb_construct.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					construct();
				}
			});
		}
		return jb_construct;
	}

	/**
	 * This method initializes JS
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getSlider() {
		if (slider == null) {
			slider = new JSlider();
			slider.setMaximum(255);
			slider.setValue(10);
			slider.setBounds(new Rectangle(15, 245, 160, 28));
			slider.setFont(new Font("Dialog", Font.BOLD, 12));
			slider.setOpaque(false);
			slider.setEnabled(false);
			slider.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					setDisable();
					jb_construct.setEnabled(true);
					adjust();
				}
			});
		}
		return slider;
	}

	/**
	 * This method initializes areaPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAreaPanel() {
		if (jp_areaPanel == null) {
			jp_areaPanel = new JPanel();
			jp_areaPanel.setLayout(new BorderLayout());
			jp_areaPanel.setBounds(new Rectangle(8, 313, 174, 167));
			jp_areaPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray, 1),
					"Areas", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(1, 1, 1)));
			jp_areaPanel.setOpaque(false);
			jp_areaPanel.setFont(new Font("Dialog", Font.BOLD, 12));
			jp_areaPanel.add(getAreaControl(), BorderLayout.NORTH);
			jp_areaPanel.add(getScrollList(), BorderLayout.CENTER);
		}
		return jp_areaPanel;
	}

	/**
	 * This method initializes JB_drawColor
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJB_drawColor() {
		if (jb_drawColor == null) {
			jb_drawColor = new JButton();
			jb_drawColor.setOpaque(false);
			jb_drawColor.setPreferredSize(new Dimension(67, 23));
			jb_drawColor.setBounds(new Rectangle(15, 490, 160, 30));
			jb_drawColor.setFont(new Font("Dialog", Font.BOLD, 14));
			jb_drawColor.setText("Draw Colors");
			jb_drawColor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillColors();
				}
			});
		}
		return jb_drawColor;
	}

	/**
	 * This method initializes areaControl
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAreaControl() {
		if (jp_areaControl == null) {
			jp_areaControl = new JPanel();
			jp_areaControl.setLayout(new GridBagLayout());
			jp_areaControl.setOpaque(false);
			jp_areaControl.add(getNewArea(), new GridBagConstraints());
			jp_areaControl.add(getDeleteArea(), new GridBagConstraints());
		}
		return jp_areaControl;
	}

	/**
	 * This method initializes scrollList
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getScrollList() {
		if (jspanel_List == null) {
			jspanel_List = new JScrollPane();
			jspanel_List.setAlignmentX(0.2F);
			jspanel_List.setAlignmentY(0.2F);
			jspanel_List.setOpaque(false);
			jspanel_List.setViewportView(getAreaList());
		}
		return jspanel_List;
	}

	/**
	 * This method initializes areaList
	 * 
	 * @return javax.swing.JList
	 */
	private JList<AreaSet> getAreaList() {
		if (areaList == null) {
			areaList = new JList<AreaSet>();
			areaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			areaList.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (areaList.getSelectedIndex() != -1) {
						jp_pic.setPIC(ImageTool.getCopyedImage(Image));
						jp_pic.setColors(areaEngine, areaSets, areaList.getSelectedIndex(), Color.GRAY);
					}
				}
			});
		}
		return areaList;
	}

	/**
	 * This method initializes newArea
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getNewArea() {
		if (jb_newArea == null) {
			jb_newArea = new JButton();
			jb_newArea.setText("NEW");
			jb_newArea.setOpaque(false);
			jb_newArea.setFont(new Font("Dialog", Font.BOLD, 14));
			jb_newArea.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					newArea();
				}
			});
		}
		return jb_newArea;
	}

	/**
	 * This method initializes deleteArea
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getDeleteArea() {
		if (jb_deleteArea == null) {
			jb_deleteArea = new JButton();
			jb_deleteArea.setText("DELETE");
			jb_deleteArea.setOpaque(false);
			jb_deleteArea.setFont(new Font("Dialog", Font.BOLD, 14));
			jb_deleteArea.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteArea();
				}
			});
		}
		return jb_deleteArea;
	}

	/**
	 * This method initializes next
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getNext() {
		if (jb_next == null) {
			jb_next = new JButton();
			jb_next.setBounds(new Rectangle(15, 530, 160, 30));
			jb_next.setOpaque(false);
			jb_next.setText("Next");
			jb_next.setFont(new Font("Dialog", Font.BOLD, 14));
			jb_next.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					next();
				}
			});
		}
		return jb_next;
	}

	/**
	 * This method initializes js_age
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getJS_01() {
		if (js_age == null) {
			js_age = new JSlider();
			js_age.setBounds(new Rectangle(5, 45, 160, 28));
			js_age.setValue(0);
			js_age.setOpaque(false);
			js_age.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					jl_age.setText("Age: " + (float) js_age.getValue() / 100);
				}
			});
		}
		return js_age;
	}

	/**
	 * This method initializes JS_02
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getJS_02() {
		if (js_sex == null) {
			js_sex = new JSlider();
			js_sex.setBounds(new Rectangle(5, 100, 160, 28));
			js_sex.setValue(0);
			js_sex.setOpaque(false);
			js_sex.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					jl_sex.setText("Sex: " + (float) js_sex.getValue() / 100);
				}
			});
		}
		return js_sex;
	}

	/**
	 * This method initializes JP_01
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJP_01() {
		if (jp_group == null) {
			jl_old_young = new JLabel();
			jl_old_young.setBounds(new Rectangle(5, 30, 160, 18));
			jl_old_young.setFont(new Font("Dialog", Font.BOLD, 12));
			jl_old_young.setText("Young-------------------------Old");
			jl_m_fm = new JLabel();
			jl_m_fm.setBounds(new Rectangle(5, 85, 160, 18));
			jl_m_fm.setFont(new Font("Dialog", Font.BOLD, 12));
			jl_m_fm.setText("Male----------------------Female");
			jp_group = new JPanel();
			jp_group.setLayout(null);
			jp_group.setBounds(new Rectangle(8, 10, 174, 169));
			jp_group.setOpaque(false);
			jp_group.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray, 1),
					"Find Group", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jp_group.add(jl_age, null);
			jp_group.add(getJS_01(), null);
			jp_group.add(jl_sex, null);
			jp_group.add(getJS_02(), null);
			jp_group.add(getJB_Cal(), null);
			jp_group.add(jl_m_fm, null);
			jp_group.add(jl_old_young, null);
		}
		return jp_group;
	}

	/**
	 * This method initializes JB_Cal
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJB_Cal() {
		if (JB_Cal == null) {
			JB_Cal = new JButton();
			JB_Cal.setBounds(new Rectangle(7, 125, 160, 30));
			JB_Cal.setOpaque(false);
			JB_Cal.setFont(new Font("Dialog", Font.BOLD, 14));
			JB_Cal.setText("Find Group");
			JB_Cal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					readExcel();
				}
			});
		}
		return JB_Cal;
	}

	private void setEnable() {
		jb_construct.setEnabled(true);
		jb_drawColor.setEnabled(true);
		areaList.setEnabled(true);
		jb_newArea.setEnabled(true);
		jb_deleteArea.setEnabled(true);
		// JB_next.setEnabled(false);
	}

	private void setDisable() {
		jb_construct.setEnabled(false);
		jb_drawColor.setEnabled(false);
		areaList.setEnabled(false);
		jb_newArea.setEnabled(false);
		jb_deleteArea.setEnabled(false);
		jb_next.setEnabled(false);
	}

	private void open() {// open image file
		setDisable();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image file", "jpg", "jpeg", "png", "gif");
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				parent.setfileName(fileChooser.getSelectedFile().getAbsolutePath());
				Image = ImageIO.read(new File(fileChooser.getSelectedFile().getAbsolutePath()));

				// conver RGB to Gray Level
				for (int y = 0; y < Image.getHeight(); y++) {

					for (int x = 0; x < Image.getWidth(); x++) {
						Color pixel = new Color(Image.getRGB(x, y));
						int G = (int) (0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
						Color pixel2 = new Color(G, G, G);
						Image.setRGB(x, y, pixel2.getRGB());
					}
				}

				// resize image panel
				jp_pic.setSize(new Dimension(Image.getWidth(), Image.getHeight()));
				jp_pic.setPreferredSize(new Dimension(Image.getWidth(), Image.getHeight()));
				adjust();
				slider.setEnabled(true);
				jb_construct.setEnabled(true);
			} catch (IOException e) {
			}
		}
	}

	private void adjust() {

		jl_scroll.setText("Gray Level Threshold : " + Math.abs(255 - slider.getValue()));

		if (Image != null) {
			BufferedImage image = new BufferedImage(Image.getWidth(), Image.getHeight(), BufferedImage.TYPE_INT_RGB);
			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					Color pixel = new Color(Image.getRGB(x, y));

					if (pixel.getRed() < Math.abs(255 - slider.getValue())) {
						image.setRGB(x, y, new Color(0, 0, 0).getRGB());
					} else {
						image.setRGB(x, y, new Color(255, 255, 255).getRGB());
					}
				}
			}
			areaEngine = null;
			areaSets = null;

			jp_pic.setPIC(image); // show image

			System.gc();
		}
	}

	private void newArea() {
		String name = JOptionPane.showInputDialog(this, "Area name");
		if (name != null && name.length() > 0) {
			areaSets.add(new AreaSet(name));
			areaList.setListData(areaSets);
			areaList.setSelectedIndex(areaSets.size() - 1);
			if (areaSets.size() > 2) {
				jb_next.setEnabled(true);
			} else {
				jb_next.setEnabled(false);
			}
		}
	}

	private void deleteArea() {
		if (areaList.getSelectedIndex() != -1) {
			areaSets.remove(areaList.getSelectedIndex());
			areaList.setListData(areaSets);
			areaList.setSelectedIndex(areaSets.size() - 1);
		}
	}

	private void construct() {
		adjust();
		areaSets = new Vector<AreaSet>();
		areaList.setListData(areaSets);
		LinkedList<PixelNode> List = new Construct(jp_pic.getPIC()).Construct_Connectivity_Net();
		areaEngine = new AreaEngine(List);

		setEnable();
	}

	private void fillColors() {
		jp_pic.setPIC(ImageTool.getCopyedImage(Image));
		Color c[] = { Color.red, Color.orange, Color.yellow, Color.blue };
		for (int i = 0; i < areaSets.size(); i++) {
			jp_pic.setColors(areaEngine, areaSets, i, c[i % c.length]);
		}
	}

	private void readExcel() {
		ReadExcel.load();

		double minage = (double) js_age.getValue() / 100 - 0.05;
		if (minage < 0)
			minage = 0;
		double maxage = (double) js_age.getValue() / 100 + 0.05;

		double minsex = (double) js_sex.getValue() / 100 - 0.05;
		if (minsex < 0)
			minsex = 0;
		double maxsex = (double) js_sex.getValue() / 100 + 0.05;

		boolean[] g = ReadExcel.findGroup(minage, maxage, minsex, maxsex);
		parent.igaPanel.setGroupData(g);

		parent.setInfo("Min Age=" + minage + " Max Age=" + maxage + " Min Sex=" + minsex + " Max Sex=" + maxsex);

		for (int d = 0; d < g.length - 1; d++) {
			if (g[d] == true) {

				parent.setInfo("Group " + (d + 1) + " is selected");
			} else {

				parent.setInfo("Group " + (d + 1) + " is not selected");
			}
		}

		jb_open.setEnabled(true);
	}

	private void next() {
		parent.igaPanel.setData(Image, areaEngine, areaSets);
		parent.nextStep(1);
	}

	public void mouseClicked(MouseEvent arg0) {

		if (areaEngine != null) {
			Area a = areaEngine.getArea(arg0.getX(), arg0.getY());
			if (a != null) {

				for (int i = 0; i < areaSets.size(); i++) {
					AreaSet set = areaSets.get(i);
					if (i == areaList.getSelectedIndex()) {

						if (set.put("" + a.id, a) != null) {
							set.remove("" + a.id);
						}
						jp_pic.setPIC(ImageTool.getCopyedImage(Image));
						jp_pic.setColors(areaEngine, areaSets, i, Color.GRAY);
					} else {
						set.remove("" + a.id);
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

} // @jve:decl-index=0:visual-constraint="10,10"
