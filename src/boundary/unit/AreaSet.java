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

import java.awt.Color;
import java.util.HashMap;

//Record area and it's name
public class AreaSet extends HashMap<String, Area> {

	private static final long serialVersionUID = 1L;
	Color color;
	String name;

	public AreaSet(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
