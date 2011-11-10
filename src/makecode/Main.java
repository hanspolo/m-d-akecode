/*
 *  m(d)akecode is a code generator for model driven development.
 *  Copyright (C)	2011 Philipp "Hanspolo" Hirsch
 *  				2011 Dennis Priefer
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package makecode;

import makecode.Parser.PIM2PSMParser;
import makecode.UI.Arguments;

public class Main {

	private static String progname = "mdakecode";
	
	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.out.printf("usage: %s [--help] [-f file] [-i inputType] [-t transform]", progname);
			System.exit(0);
		}
		
		try {
			Arguments.check(args);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.printf("usage: %s [--help] [-f file] [-i inputType] [-t transform]", progname);
			System.exit(0);
		}
		
		
		if (Arguments.getHelp()) {
			System.out.printf("usage: %s [--help] [-f file] [-i inputType] [-t transform] %s", progname, System.lineSeparator());
			System.out.println("--help: Get help, you actually use this");
			System.out.println("-f file: Location of the file, that will be used as model");
			System.out.println("-i inputType: Type of the model, i.e. ecore, uml");
			System.out.println("-t transform: Which transformation should be used, to generate code");
			System.exit(0);
		}
		
		try {
			PIM2PSMParser parser = new PIM2PSMParser();
			parser.parse(Arguments.getFile(), Arguments.getFileType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
