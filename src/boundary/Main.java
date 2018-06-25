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
package boundary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import boundary.widget.MainScreen;

public class Main {

	public static void debug(String[] args) {
		if (args.length > 0)
			for (String arg : args) {
				if (!arg.startsWith("debug=") || arg.indexOf("on") == -1)
					setLog();
			}
		else {
			setLog();
		}
	}

	private static void setLog() {
		try {
			File log = new File("logs");
			if (!log.exists()) {
				log.mkdir();
			}
			String date = Calendar.getInstance().getTime().toString().replace(':', ' ');
			System.setOut(
					new PrintStream(new FileOutputStream("logs" + File.separator + "log_" + date + ".txt", false)));
			System.setErr(
					new PrintStream(new FileOutputStream("logs" + File.separator + "err_" + date + ".txt", false)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// debug(args);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				new MainScreen();
			}
		});
	}
}
