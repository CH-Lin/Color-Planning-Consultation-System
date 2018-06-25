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
package boundary.unit;

import java.util.LinkedList;

public class Row {
	LinkedList<PixelNode> left, right; // use to save left node and right node

	public Row() {
		left = new LinkedList<PixelNode>();
		right = new LinkedList<PixelNode>();
	}

	/**
	 * Add left node and right node
	 * 
	 * @param PixelNode
	 *            L left node
	 * @param PixelNode
	 *            R right node
	 */
	public void add(PixelNode L, PixelNode R) {
		left.add(L);
		right.add(R);
	}

	/**
	 * get front left node
	 * 
	 * @return Node front left node
	 */
	public PixelNode getFront_Left() {
		PixelNode n = null;
		for (int i = 0; i < left.size(); i++) {
			if (left.get(i).link == null) {
				n = left.get(i);
				break;
			}
		}
		return n;
	}

	/**
	 * get rear left node
	 * 
	 * @return Node rear left node
	 * 
	 */
	public PixelNode getRear_Left() {
		PixelNode n = null;
		for (int i = left.size() - 1; i >= 0; i--) {
			if (left.get(i).link != null) {
				n = left.get(i);
				break;
			}
		}
		return n;
	}

	/**
	 * get front right node
	 * 
	 * @return Node front right node
	 */
	public PixelNode getFront_Right() {
		PixelNode n = null;
		for (int i = right.size() - 1; i >= 0; i--) {
			if (right.get(i).link == null) {
				n = right.get(i);
				break;
			}
		}
		return n;
	}

	/**
	 * get rear right node
	 * 
	 * @return Node rear right node
	 */
	public PixelNode getRear_Right() {
		PixelNode n = null;
		for (int i = 0; i < right.size(); i++) {
			if (right.get(i).link != null) {
				n = right.get(i);
				break;
			}
		}
		return n;
	}
}
