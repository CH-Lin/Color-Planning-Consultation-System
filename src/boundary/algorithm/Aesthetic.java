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

public class Aesthetic {

	private float[] HD(float[][] HVC) {
		// different values between hue.
		float sum_HD = 0;
		float h = 0;
		float hn = 0;
		float[] hue = new float[2];

		for (int i = 0; i < HVC.length - 1; i++) {
			float color1 = HVC[i][0];
			for (int j = i + 1; j < HVC.length; j++) {
				float color2 = HVC[j][0];

				float HD = Math.abs(color1 - color2);

				if (HD <= 360 & HD > 90) {
					HD = HD - 90;
				}

				if (HD > 360) {// gray color
					h = (float) 0;
				}

				if (HD == 0) {
					h = (float) 1.5;
				} else if (HD > 0 & HD <= 7) {
					h = (float) 0;
					hn = hn + 1;
				} else if (HD > 7 & HD <= 12) {
					h = (float) 1.1;
					hn = hn + 1;
				} else if (HD > 12 & HD <= 28) {
					h = (float) 0.65;
					hn = hn + 1;
				} else if (HD > 28 & HD <= 50) {
					h = (float) 1.7;
					hn = hn + 1;
				}

				sum_HD = sum_HD + h;
			}
		}
		hue[0] = sum_HD;
		hue[1] = hn;

		return hue;
	}

	private float[] VD(float[][] HVC) {
		// different values between value.
		float sum_VD = 0;
		float v = 0;
		float vn = 0;
		float[] value = new float[2];
		for (int i = 0; i < HVC.length - 1; i++) {
			float color1 = HVC[i][1];
			for (int j = i + 1; j < HVC.length; j++) {
				float color2 = HVC[j][1];

				float VD = Math.abs(color1 - color2);
				if (VD == 0) {
					v = (float) -1.3;
				} else if (VD > 0 & VD <= 0.5) {
					v = (float) -1;
					vn = vn + 1;
				} else if (VD > 0.5 & VD <= 1.5) {
					v = (float) 0.7;
					vn = vn + 1;
				} else if (VD > 1.5 & VD <= 2.5) {
					v = (float) -0.2;
					vn = vn + 1;
				} else if (VD > 2.5 & VD <= 10) {
					v = (float) 3.7;
					vn = vn + 1;
				} else if (VD > 7) {
					v = (float) 0.4;
					vn = vn + 1;
				}
				sum_VD = sum_VD + v;
			}
		}

		value[0] = sum_VD;
		value[1] = vn;
		return value;
	}

	private float[] CD(float[][] HVC) {
		// different values between chroma.
		float sum_CD = 0;
		float c = 0;
		float cn = 0;
		float[] chroma = new float[2];
		for (int i = 0; i < HVC.length - 1; i++) {
			float color1 = HVC[i][2];
			for (int j = i + 1; j < HVC.length; j++) {
				float color2 = HVC[j][2];

				float CD = Math.abs(color1 - color2);

				if (CD == 0) {
					c = (float) 0.8;
				} else if (CD > 0 & CD <= 3) {
					c = (float) 0;
					cn = cn + 1;
				} else if (CD > 3 & CD <= 5) {
					c = (float) 0.1;
					cn = cn + 1;
				} else if (CD > 5 & CD <= 7) {
					c = (float) 0;
					cn = cn + 1;
				} else if (CD > 7) {
					c = (float) 0.4;
					cn = cn + 1;
				}
				sum_CD = sum_CD + c;

			}
		}
		chroma[0] = sum_CD;
		chroma[1] = cn;

		return chroma;
	}

	public float getAesthetic(float[][] HVC) {
		// calculating aesthetic measure=order/complexity.
		float H[] = HD(HVC);
		float V[] = VD(HVC);
		float C[] = CD(HVC);

		float complexity = HVC.length + H[1] + V[1] + C[1];
		float order = H[0] + V[0] + C[0];
		float M = order / complexity;

		return M;
	}
}