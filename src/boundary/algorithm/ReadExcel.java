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

import java.io.*;
import java.util.HashMap;

import boundary.unit.HVC;
import jxl.*;
import jxl.read.biff.BiffException;

/**
 * class ReadExcel ReadGroup: get a index number of group_row/color_num in Excel
 * file. There are 15 groups. getColor: get the RGB value of c_num color in
 * g_num group. getGroup: get the group numbers which the fuzzy values between
 * two groups are over threshold values.
 * 
 * 
 * 
 * 
 */

public class ReadExcel {

	private static double[][] group_color;

	private static double sheets[][][];

	private static Workbook colorbook;

	private static Workbook hvcbook;

	private static final String file = "color_1.xls";

	private static final String file2 = "hvc.xls";

	private static HashMap<String, HVC> hvcmap;

	public static void load() {
		group_color = new double[21][45];
		sheets = new double[2][15][15];
		hvcmap = new HashMap<String, HVC>();
		try {
			colorbook = Workbook.getWorkbook(new File("data" + File.separator + file));
			Sheet sheet = colorbook.getSheet(0);
			for (int i = 0; i < group_color.length; i++) {
				for (int j = 0; j < group_color[i].length; j++) {
					group_color[i][j] = ((NumberCell) sheet.getCell(i, j)).getValue();
				}
			}

			for (int k = 0; k < sheets.length; k++) {
				Sheet sheet1 = colorbook.getSheet(k + 1);
				for (int i = 0; i < sheets[0].length; i++) {
					for (int j = 0; j < sheets[0][i].length; j++) {
						sheets[k][i][j] = ((NumberCell) sheet1.getCell(i, j)).getValue();
					}
				}
			}

			hvcbook = Workbook.getWorkbook(new File("data" + File.separator + file2));
			Sheet hvcsheet = hvcbook.getSheet(1);

			for (int i = 1; i < 216; i++) {
				String key = "R=" + hvcsheet.getCell(2, i).getContents() + "G=" + hvcsheet.getCell(3, i).getContents()
						+ "B=" + hvcsheet.getCell(4, i).getContents();

				HVC data = new HVC(hvcsheet.getCell(5, i).getContents(),
						((NumberCell) hvcsheet.getCell(7, i)).getValue(),
						((NumberCell) hvcsheet.getCell(8, i)).getValue(),
						((NumberCell) hvcsheet.getCell(9, i)).getValue());
				hvcmap.put(key, data);
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static double[] findColor(int g_num, int c_num) {
		int g_row = g_num * 3;
		double[] color = { group_color[c_num][g_row], group_color[c_num][g_row + 1], group_color[c_num][g_row + 2] };
		return color;
	}

	public static boolean[] findGroup(double age_min, double age_max, double sex_min, double sex_max) {

		boolean[] g = new boolean[16];

		for (int i = 1; i < g.length; i++) {
			for (int j = 1; j < g.length; j++) {
				double[] age = getGroup(i, j, age_min, age_max, 1);
				double[] sex = getGroup(i, j, sex_min, sex_max, 2);
				if (age[0] != 0 & age[1] != 0) {

					int a_1 = (int) age[0];
					int a_2 = (int) age[1];
					g[a_1 - 1] = true;
					g[a_2 - 1] = true;

				} else if (sex[0] != 0 & sex[1] != 0) {

					int s_1 = (int) sex[0];
					int s_2 = (int) sex[1];
					g[s_1 - 1] = true;
					g[s_2 - 1] = true;

				}
			}
		}

		return g;
	}

	private static double[] getGroup(int g_c, int g_r, double threshold_min, double threshold_max, int book_num) {

		double v = 0.00;

		v = sheets[book_num - 1][g_c - 1][g_r - 1];

		double g_index[] = new double[2];

		if (v > threshold_min & v < threshold_max) {
			g_index[0] = g_c;
			g_index[1] = g_r;
			return g_index;
		} else {
			g_index[0] = 0;
			g_index[1] = 0;
			return g_index;
		}
	}

	public static HVC RGB2HVC(int r, int g, int b) {
		String key = "R=" + r + "G=" + g + "B=" + b;
		return hvcmap.get(key);
	}
}
