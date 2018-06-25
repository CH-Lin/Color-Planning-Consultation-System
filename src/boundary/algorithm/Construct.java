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
package boundary.algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import boundary.unit.PixelNode;
import boundary.unit.Row;

public class Construct {

	private static final byte CONNECTED = 0, BEFORE = 1, AFTER = 2;

	LinkedList<Row> Rows;

	LinkedList<PixelNode> List;

	public Construct(BufferedImage bfImage) {
		List = new LinkedList<PixelNode>();
		Rows = new LinkedList<Row>();

		if (bfImage != null) {

			Row r;
			PixelNode left = null, right = null;

			for (int y = 0; y < bfImage.getHeight(); y++) {
				r = new Row();
				Rows.add(r);
				left = null;
				right = null;
				for (int x = 0; x < bfImage.getWidth(); x++) {

					if (bfImage.getRGB(x, y) != -1) {
						Color pixel = new Color(bfImage.getRGB(x, y));

						if (pixel.getRed() == 0 && pixel.getRed() == 0 && pixel.getBlue() == 0) {
							left = new PixelNode(y, x, true);

							if (right != null) {
								right.next = left;
								left.prev = right;
							}

							while (true) {
								x++;

								try {
									pixel = new Color(bfImage.getRGB(x, y));
									if (pixel.getRed() != 0 || pixel.getRed() != 0 || pixel.getBlue() != 0)
										break;
								} catch (ArrayIndexOutOfBoundsException e) {
									break;
								}

							}
							right = new PixelNode(y, x - 1, false);
							left.next = right;
							right.prev = left;

							r.add(left, right);

							// System.out.println("(" + left.row_index + ","
							// + left.col_index + ")" + "("
							// + right.row_index + "," + right.col_index
							// + ")");
						}
					}
				}
			}
		} else {
			runEX();
		}
	}

	private void runEX() {
		System.out.println("EX");
		Row r1 = new Row();
		PixelNode n1 = new PixelNode(0, 6, true);
		PixelNode n2 = new PixelNode(0, 7, false);
		n1.next = n2;
		n2.prev = n1;
		r1.add(n1, n2);
		Rows.add(r1);

		Row r2 = new Row();
		PixelNode n3 = new PixelNode(1, 1, true);
		PixelNode n4 = new PixelNode(1, 1, false);
		PixelNode n5 = new PixelNode(1, 4, true);
		PixelNode n6 = new PixelNode(1, 4, false);
		PixelNode n7 = new PixelNode(1, 6, true);
		PixelNode n8 = new PixelNode(1, 7, false);
		n3.next = n4;
		n4.prev = n3;
		n4.next = n5;
		n5.prev = n4;
		n5.next = n6;
		n6.prev = n5;
		n6.next = n7;
		n7.prev = n6;
		n7.next = n8;
		n8.prev = n7;
		r2.add(n3, n4);
		r2.add(n5, n6);
		r2.add(n7, n8);
		Rows.add(r2);

		Row r3 = new Row();
		PixelNode n9 = new PixelNode(2, 1, true);
		PixelNode n10 = new PixelNode(2, 2, false);
		PixelNode n11 = new PixelNode(2, 4, true);
		PixelNode n12 = new PixelNode(2, 7, false);
		n9.next = n10;
		n10.prev = n9;
		n10.next = n11;
		n11.prev = n10;
		n11.next = n12;
		n12.prev = n11;
		r3.add(n9, n10);
		r3.add(n11, n12);
		Rows.add(r3);

		Row r4 = new Row();
		PixelNode n13 = new PixelNode(3, 1, true);
		PixelNode n14 = new PixelNode(3, 2, false);
		PixelNode n15 = new PixelNode(3, 4, true);
		PixelNode n16 = new PixelNode(3, 4, false);
		PixelNode n17 = new PixelNode(3, 7, true);
		PixelNode n18 = new PixelNode(3, 7, false);
		n13.next = n14;
		n14.prev = n13;
		n14.next = n15;
		n15.prev = n14;
		n15.next = n16;
		n16.prev = n15;
		n16.next = n17;
		n17.prev = n16;
		n17.next = n18;
		n18.prev = n17;
		r4.add(n13, n14);
		r4.add(n15, n16);
		r4.add(n17, n18);
		Rows.add(r4);

		Row r5 = new Row();
		PixelNode n19 = new PixelNode(4, 4, true);
		PixelNode n20 = new PixelNode(4, 7, false);
		n19.next = n20;
		n20.prev = n19;
		r5.add(n19, n20);
		Rows.add(r5);
	}

	/**
	 * Construct Connectivity Net
	 * 
	 */
	public LinkedList<PixelNode> Construct_Connectivity_Net() {
		// System.out.printf("Construct Connectivity Net......%n");

		PixelNode n = getStart_Front_left();

		while (n != null) {
			Recur_Connect_Left(n);
			n = getStart_Front_left();
		}

		n = getStart_Front_right();

		while (n != null) {
			Recur_Connect_Right(n);
			n = getStart_Front_right();
		}

		// System.out.printf("Construct finish......%n");
		return List;
	}

	/**
	 * a recursive function to extract links of left-nodes from a given start-node
	 * 
	 * @param n
	 *            start-node
	 */
	private void Recur_Connect_Left(PixelNode n) {

		List.add(n);

		PixelNode C = n, F = null, R = null;
		boolean after = false;

		int k = n.row_index;
		try {
			F = Rows.get(k + 1).getFront_Left();
		} catch (IndexOutOfBoundsException e) {
			F = null;
		}

		while (F != null && !after) {
			switch (Connectivity_relation(F, C)) {
			case BEFORE:
				Recur_Connect_Left(F);
				break;
			case CONNECTED:

				C.link = F;
				C = F;
				break;
			case AFTER:
				after = true;
				break;
			default:
			}

			k = C.row_index;
			try {
				F = Rows.get(k + 1).getFront_Left();
			} catch (IndexOutOfBoundsException e) {
				F = null;
			}
		}

		try {
			R = Rows.get(k + 1).getRear_Left();
		} catch (IndexOutOfBoundsException e) {
			R = null;
		}

		byte rel = Connectivity_relation(R, C);

		if (rel == BEFORE) {
			C.link = C.next;
		} else if (R == null) {
			C.link = C.next;
		} else if (rel == CONNECTED) {
			C.link = C.prev;
		}
	}

	/**
	 * recursive function extracting connectivity information (links) of the
	 * left-nodes can be applied to the case of right-nodes
	 * 
	 * @param n
	 *            start-node
	 */
	private void Recur_Connect_Right(PixelNode n) {
		PixelNode C = n, F = null, R = null;
		boolean before = false;

		int k = C.row_index;
		try {
			F = Rows.get(k - 1).getFront_Right();
		} catch (IndexOutOfBoundsException e) {
			F = null;
		}

		while (F != null && !before) {
			switch (Connectivity_relation(F, C)) {
			case AFTER:
				Recur_Connect_Right(F);
				break;
			case CONNECTED:
				C.link = F;
				C = F;
				break;
			case BEFORE:
				before = true;
				break;
			default:
			}

			k = C.row_index;
			try {
				F = Rows.get(k - 1).getFront_Right();
			} catch (IndexOutOfBoundsException e) {
				F = null;
			}
		}

		try {
			R = Rows.get(k - 1).getRear_Right();
		} catch (IndexOutOfBoundsException e) {
			R = null;
		}

		byte rel = Connectivity_relation(R, C);

		if (rel == AFTER) {
			C.link = C.prev;
		} else if (R == null) {
			C.link = C.prev;
		} else if (rel == CONNECTED) {
			C.link = C.next;
		}
	}

	/**
	 * Get a node having the lowest row index among the front left-nodes in the
	 * connectivity-net
	 * 
	 * @return Node
	 */
	private PixelNode getStart_Front_left() {
		PixelNode n = null;
		for (int i = 0; i < Rows.size(); i++) {
			n = Rows.get(i).getFront_Left();
			if (n != null) {
				break;
			}
		}
		return n;
	}

	/**
	 * Get a node having the highest row index among the front right-nodes in the
	 * connectivity-net
	 * 
	 * @return Node
	 */
	private PixelNode getStart_Front_right() {
		PixelNode n = null;
		for (int i = Rows.size() - 1; i >= 0; i--) {
			n = Rows.get(i).getFront_Right();
			if (n != null) {
				break;
			}
		}
		return n;
	}

	/**
	 * Identify the connectivity relation between C-node & F-node
	 * 
	 * @param F
	 * @param C
	 * @return CONNECTED=0, BEFORE=1, AFTER=2, F | C == null then return -1
	 */
	private byte Connectivity_relation(PixelNode F, PixelNode C) {
		if (F != null && C != null)
			if (F.left) {
				if (F.next.col_index < C.col_index) {
					return BEFORE;
				} else if (F.col_index > C.next.col_index) {
					return AFTER;
				} else {
					return CONNECTED;
				}
			} else {
				if (F.prev.col_index > C.col_index) {
					return AFTER;
				} else if (F.col_index < C.prev.col_index) {
					return BEFORE;
				} else {
					return CONNECTED;
				}
			}
		return -1;
	}
}
