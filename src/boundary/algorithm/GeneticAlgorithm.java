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

import java.util.HashSet;

import boundary.unit.ColorSet;

public class GeneticAlgorithm {

	private int[][][] chrom2 = null;
	private int[] g_index;
	private int[] g_lenght = { 13, 9, 15, 16, 18, 18, 21, 20, 16, 18, 11, 15, 10, 5, 10 };
	private float mutrate = 0.01F;

	public void setAreaNumber(int n) {
		chrom2 = new int[6][n][10];

		for (int i = 0; i < chrom2.length; i++) {
			for (int j = 0; j < chrom2[i].length; j++) {
				for (int k = 0; k < chrom2[i][j].length; k++) {
					if ((float) Math.random() >= 0.5) {
						chrom2[i][j][k] = 1;
					} else {
						chrom2[i][j][k] = 0;
					}
				}
			}
		}

		print("Original");
	}

	public void setGroupData(boolean[] g) {
		int count = 0;
		for (int i = 0; i < g.length; i++) {
			if (g[i] == true) {
				count++;
			}
		}
		g_index = new int[count];

		int index = 0;
		for (int i = 0; i < g.length; i++) {
			if (g[i] == true) {
				g_index[index] = i;
				index++;
			}
		}
	}

	public ColorSet[] getSixColorSet() {

		ColorSet set[] = new ColorSet[6];
		for (int i = 0; i < set.length; i++) {
			set[i] = decode(chrom2[i]);
		}
		return set;
	}

	public void setRate(float r) {
		mutrate = r;
	}

	private ColorSet decode(int[][] de) {

		int[] g_n = new int[de.length];
		int[] c_n = new int[de.length];

		for (int i = 0; i < de.length; i++) {
			float de_g = (float) (de[i][0] * 16 + de[i][1] * 8 + de[i][2] * 4 + de[i][3] * 2 + de[i][4]) / 31;
			if (de_g == 1) {
				de_g = (float) (de_g - 0.001);
			}
			float de_c = (float) (de[i][5] * 16 + de[i][6] * 8 + de[i][7] * 4 + de[i][8] * 2 + de[i][9]) / 31;
			if (de_c == 1) {
				de_c = (float) (de_c - 0.001);
			}

			g_n[i] = g_index[(int) Math.floor(de_g * g_index.length)];

			c_n[i] = (int) Math.floor(de_c * g_lenght[g_n[i]]);

		}

		// for (int i = 0; i < g_n.length; i++) {
		// System.out.println(g_n[i] + " " + c_n[i]);
		// }

		ColorSet coloset = new ColorSet();
		for (int i = 0; i < de.length; i++) {
			double[] color = ReadExcel.findColor(g_n[i], c_n[i]);
			coloset.addColor(g_n[i] + 1, (int) color[0], (int) color[1], (int) color[2]);
		}

		return coloset;
	}

	public void choose(int v1, int v2, int v3, int v4, int v5, int v6) {
		int[][][] chose = new int[6][chrom2[0].length][10];

		int[] score = { v1, v2, v3, v4, v5, v6 };

		for (int i = 0; i < 6; i++) {
			if (score[i] == 0) {

				for (int j = 0; j < chose[i].length; j++) {
					for (int k = 0; k < chose[i][j].length; k++) {
						if ((float) Math.random() >= 0.5) {
							chose[i][j][k] = 1;
						} else {
							chose[i][j][k] = 0;
						}
					}
				}

			} else {
				float random = (float) Math.random() * (v1 + v2 + v3 + v4 + v5 + v6);
				System.out.print("Random = " + random + "\tSelect chrom = ");
				if (0 <= random && random < v1) {
					for (int j = 0; j < chrom2[0].length; j++) {
						System.arraycopy(chrom2[0][j], 0, chose[i][j], 0, chrom2[0][j].length);
					}
				} else if (v1 <= random && random < v1 + v2) {
					for (int j = 0; j < chrom2[1].length; j++) {
						System.arraycopy(chrom2[1][j], 0, chose[i][j], 0, chrom2[1][j].length);
					}
				} else if (v1 + v2 <= random && random < v1 + v2 + v3) {
					for (int j = 0; j < chrom2[2].length; j++) {
						System.arraycopy(chrom2[2][j], 0, chose[i][j], 0, chrom2[2][j].length);
					}
				} else if (v1 + v2 + v3 <= random && random < v1 + v2 + v3 + v4) {
					for (int j = 0; j < chrom2[3].length; j++) {
						System.arraycopy(chrom2[3][j], 0, chose[i][j], 0, chrom2[3][j].length);
					}
				} else if (v1 + v2 + v3 + v4 <= random && random < v1 + v2 + v3 + v4 + v5) {
					for (int j = 0; j < chrom2[4].length; j++) {
						System.arraycopy(chrom2[4][j], 0, chose[i][j], 0, chrom2[4][j].length);
					}
				} else if (v1 + v2 + v3 + v4 + v5 <= random && random <= v1 + v2 + v3 + v4 + v5 + v6) {
					for (int j = 0; j < chrom2[5].length; j++) {
						System.arraycopy(chrom2[5][j], 0, chose[i][j], 0, chrom2[5][j].length);
					}
				}
			}
		}

		for (int i = 0; i < chose.length; i++) {
			for (int j = 0; j < chose[i].length; j++) {
				System.arraycopy(chose[i][j], 0, chrom2[i][j], 0, chose[i][j].length);
			}
		}
		print("Choose result");

		for (int i = 0; i < 6; i++) {
			int index[] = select(i);
			for (int j = 0; j < index.length; j += 2)
				crossover(i, index[j], index[j + 1]);
		}
		print("After Crossover");

		mut();
		print("After mut");
	}

	private int[] select(int i) {
		int index[];
		if ((chrom2[i].length % 2) == 0) {
			index = new int[chrom2[i].length];
		} else {
			index = new int[chrom2[i].length - 1];
		}

		HashSet<Integer> hash = new HashSet<Integer>(index.length);
		for (int j = 0; j < index.length; j++) {
			int rnd = (int) ((Math.random()) * chrom2[i].length);
			if (rnd == chrom2[i].length)
				rnd--;

			while (!hash.add(rnd)) {
				rnd = (int) ((Math.random()) * chrom2[i].length);
				if (rnd == chrom2[i].length)
					rnd--;
			}
			index[j] = rnd;
		}
		return index;
	}

	private void crossover(int pic, int c1, int c2) {

		int i = (int) ((Math.random()) * (5)) + 5;
		if (i == 10)
			i--;

		int[] temp2 = new int[10 - i];
		System.arraycopy(chrom2[pic][c1], i, temp2, 0, 10 - i);
		System.arraycopy(chrom2[pic][c2], i, chrom2[pic][c1], i, 10 - i);
		System.arraycopy(temp2, 0, chrom2[pic][c2], i, 10 - i);

	}

	private void mut() {
		for (int i = 0; i < chrom2.length; i++) {
			for (int j = 0; j < chrom2[i].length; j++) {
				for (int k = 0; k < chrom2[i][j].length; k++) {
					float random = (float) Math.random();
					if (random <= mutrate) {
						System.out.println("MUT at Chrom" + i + " index " + j);
						if (chrom2[i][j][k] == 1) {
							chrom2[i][j][k] = 0;
						}
						if (chrom2[i][j][k] == 0) {
							chrom2[i][j][k] = 1;
						}
					}
				}
			}
		}
	}

	private void print(String text) {
		System.out.println(text);
		for (int i = 0; i < chrom2.length; i++) {
			System.out.println("PIC " + i);
			for (int j = 0; j < chrom2[i].length; j++) {
				System.out.print("chrom " + j + ":\t");
				for (int k = 0; k < chrom2[i][j].length; k++) {
					System.out.print(chrom2[i][j][k] + " ");
				}
				System.out.println();
			}
			System.out.println("\n");
		}
		System.out.println("\n");
	}
}