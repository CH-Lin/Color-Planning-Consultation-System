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

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import boundary.unit.Area;
import boundary.unit.PixelNode;

public class AreaEngine {

	private LinkedList<Area> areas;

	public AreaEngine(LinkedList<PixelNode> List) {
		areas = new LinkedList<Area>();
		HashMap<String, Area> hasharea = new HashMap<String, Area>();

		for (int i = 0; i < List.size(); i++) {
			PixelNode start = List.get(i);
			PixelNode next = start;
			PixelNode left = next;

			int L = next.col_index, R = next.col_index;
			int T = next.row_index, D = next.row_index;

			do {
				next.area = i;
				if (next.col_index < left.col_index) {
					left = next;
				}

				if (next.col_index < L) {
					L = next.col_index;
				}
				if (next.col_index > R) {
					R = next.col_index;
				}
				if (next.row_index < T) {
					T = next.row_index;
				}
				if (next.row_index > D) {
					D = next.row_index;
				}

				next = next.link;
			} while (start != next);
			if (left.left == false) {
				String key = "L" + L + "R" + R + "W" + (R - L) + "H" + (D - T);
				if (!hasharea.containsKey(key)) {
					Area a = new Area(i);
					a.start = start;
					a.setBounds(L, T, R - L, D - T);
					hasharea.put(key, a);
					areas.add(a);
				}
			}
		}
	}

	public LinkedList<Area> getAreaList() {
		return areas;
	}

	public Area getArea(int x, int y) {
		LinkedList<Area> area = new LinkedList<Area>();

		for (int i = 0; i < areas.size(); i++) {
			LinkedList<PixelNode> nodes = new LinkedList<PixelNode>();
			Area a = areas.get(i);
			if (a.contains(x, y)) {

				PixelNode next = a.start;

				do {
					if (next.row_index == y) {
						nodes.add(next);
					}
					next = next.link;
				} while (next != a.start);

				PixelNode[] n = nodes.toArray(new PixelNode[0]);
				Arrays.sort(n, new NodeComparator());

				if (n.length % 2 != 0) {
					return null;
				}

				for (int j = 0; j < n.length; j = j + 2) {
					if (n[j].col_index < x && n[j + 1].col_index > x) {
						area.add(a);
					}
				}
			}
		}

		int w = Integer.MAX_VALUE, index = 0;
		for (int i = 0; i < area.size(); i++) {
			Area a = area.get(i);
			if (a.width < w) {
				w = a.width;
				index = i;
			}
		}

		if (area.size() > 0)
			return area.get(index);
		else
			return null;
	}

	private class NodeComparator implements Comparator<Object> {

		public int compare(Object arg0, Object arg1) {
			if (((PixelNode) arg0).col_index > ((PixelNode) arg1).col_index)
				return 1;
			else if (((PixelNode) arg0).col_index < ((PixelNode) arg1).col_index)
				return -1;
			return 0;
		}

	}
}
