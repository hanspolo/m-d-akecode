/*
 *  m(d)akecode is a code generator for model driven development.
 *  Copyright (C)    2011 Philipp "Hanspolo" Hirsch
 *                  2011 Dennis Priefer
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

package makecode.pcparser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import makecode.uml.ModelElement;
import makecode.uml.Class;
import makecode.uml.Interface;

public class Template {

	private ModelElement element;
	private HashMap<String, Object> variables;

	/**
	 * 
	 * @param element
	 */
	public Template(ModelElement element) {
		this.element = element;
		variables = new HashMap<String, Object>();
	}

	/**
	 * 
	 * @param file
	 * @param element
	 * @return
	 */
	public String render(String file) {
		String output = "";

		try {
			FileReader reader = new FileReader("meta/" + file);

			char[] cbuf = new char[80];

			while (reader.read(cbuf) > 0) {
				output += String.valueOf(cbuf);
				cbuf = new char[80];
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.findVariables(output);

		this.findFunctions(output);
		
		// ASDF
		Pattern vp = Pattern.compile("\\{\\{.*\\}\\}");
		Matcher vm = vp.matcher(output);

		while (vm.find()) {
			String replacement = this.transformVariable(vm.group());
			output = output.replace(vm.group(), replacement);
		}
		
		return output;
	}

	/**
	 * 
	 * @param output
	 */
	private void findVariables(String output) {
		Pattern vp = Pattern.compile("\\{\\{.*\\}\\}");
		Matcher vm = vp.matcher(output);

		while (vm.find())
			this.loadVariable(vm.group());
	}

	/**
	 * 
	 * @param match
	 */
	private void loadVariable(String match) {

		match = match.substring(2, match.length() - 2);
		String[] parts = match.split(" ");
		String[] varParts;

		for (String s : parts) {
			if (!s.startsWith("@"))
				continue;

			if (s.startsWith("@") && variables.containsKey(s))
				continue;

			s = s.substring(1);
			varParts = s.split("\\.");

			if (varParts[0].equals("package")) {
				if (varParts[1].equals("name"))
					variables.put(s, element.getPackageName());
			} else if (varParts[0].equals("class")) {
				if (varParts[1].equals("name"))
					variables.put(s, element.getName());
				else if (varParts[1].equals("isabstract"))
					variables.put(s, ((Class) element).getIsAbstract());
				else if (varParts[1].equals("supertypes"))
					variables.put(s, ((Class) element).getSuperTypes());
				else if (varParts[1].equals("features"))
					variables.put(s, ((Class) element).getFeatures());
			} else if (varParts[0].equals("interface")) {
				if (varParts[1].equals("name"))
					variables.put(s, element.getName());
				else if (varParts[1].equals("supertypes"))
					variables.put(s, ((Interface) element).getSuperTypes());
				else if (varParts[1].equals("features"))
					variables.put(s, ((Interface) element).getFeatures());
			} else {
				variables.put(s, null);
			}
		}
	}

	/**
	 * 
	 * @param match
	 */
	private String transformVariable(String match) {
		String output = "";
		match = match.substring(2, match.length()-2);
		String[] parts = match.split(" ");
		
		for (String s : parts) {
			if (!s.startsWith("@"))
				continue;

			s = s.substring(1);

			Object o = variables.get(s);
			
			if (o instanceof String)
				output = o.toString();
			else if (o instanceof Boolean)
				output = o.toString();
		}

		return output;
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	private String findFunctions(String input) {
		StringTokenizer st = new StringTokenizer(input);
		String s, output = "", substring = "";
		Integer inFunction = 0;

		try {
			while (true) {
				s = st.nextToken();
				
				if (inFunction > 0) {
					substring += s + " ";
				}
				else if (s.startsWith("<M:")) {
					substring += s + " ";
					inFunction++;
				}
				else if (s.startsWith("</M:")) {
					inFunction--;
					if (inFunction == 0)
						output += " %s ";
				}
				else {
					output += s + " ";
				}
			}
		} catch (NoSuchElementException e) {
		}
		
		if (substring != "") {
			substring = translateFunction(substring);
			substring = findFunctions(substring);

			output = String.format(output, substring);
		}

		return output;
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	private String translateFunction(String input) {
		
		if (input.startsWith("<M:if")) {
			input = input.substring(0, input.lastIndexOf("</M:if>"));
		}
		else if (input.startsWith("<M:foreach")) {
			input = input.substring(0, input.lastIndexOf("</M:foreach>"));
		}
		
		input = input.substring(input.indexOf(">"));
		
		return input;
	}
	
	
}
