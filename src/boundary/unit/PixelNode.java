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

//Record Pixel
public class PixelNode {
	public PixelNode prev = null, next = null, link = null;

	public int col_index = 0, row_index = 0, area = -1;

	public boolean left = true; // left node or right node

	public PixelNode(int row, int col, boolean L) {
		row_index = row;
		col_index = col;
		left = L;
	}

	/**
	 * identify left or right
	 * 
	 * @return String
	 */
	public String getLR() {
		if (left)
			return "LEFT";
		else
			return "RIGHT";
	}
}
