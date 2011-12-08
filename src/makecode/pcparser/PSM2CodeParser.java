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

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import makecode.uml.ModelTree;

import org.xml.sax.helpers.DefaultHandler;

public class PSM2CodeParser {

	public void parse(String type) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser sax = factory.newSAXParser();

		MetaModelHandler handler = new MetaModelHandler();

		sax.parse("meta/psm2" + type + ".xml", handler);

		Template tpl;

		for (String file : handler.getFilesByType("required")) {
			tpl = new Template(ModelTree.getInstance().getModelElements().get(0));
			tpl.render(file);
			System.out.println(tpl.render(file));
		}
		
		for (String file : handler.getFilesByType("database")) {
			tpl = new Template(ModelTree.getInstance().getModelElements().get(0));
			System.out.println(tpl.render(file));
		}
		
		for (String file : handler.getFilesByType("interface")) {
			tpl = new Template(ModelTree.getInstance().getModelElements().get(0));
			System.out.println(tpl.render(file));
		}
		
		for (String file : handler.getFilesByType("model")) {
			tpl = new Template(ModelTree.getInstance().getModelElements().get(0));
			System.out.println(tpl.render(file));
		}
		
		for (String file : handler.getFilesByType("rest")) {
			tpl = new Template(ModelTree.getInstance().getModelElements().get(0));
			System.out.println(tpl.render(file));
		}		
	}
}