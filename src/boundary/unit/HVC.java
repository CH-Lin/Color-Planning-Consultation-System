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

public class HVC {
	private String Pantone;
	double angle, v, c;

	public HVC(String p, double a, double v, double c) {
		this.Pantone = p;
		this.angle = a;
		this.v = v;
		this.c = c;
	}

	public String getPantone() {
		return Pantone;
	}

	public double getH() {
		return angle;
	}

	public double getV() {
		return v;
	}

	public double getC() {
		return c;
	}

	public String toString() {
		return "Pantone=" + Pantone + "Hue=" + angle + "V=" + v + "C=" + c;
	}
}