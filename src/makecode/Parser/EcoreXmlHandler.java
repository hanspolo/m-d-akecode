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

package makecode.Parser;

import makecode.UML.Attribute;
import makecode.UML.Class;
import makecode.UML.Interface;
import makecode.UML.ModelElement;
import makecode.UML.Operation;
import makecode.UML.Parameter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class EcoreXmlHandler extends DefaultHandler {

	private ModelTree tree;
	private int typeIndex, nameIndex;
	private ModelElement actClass;
	private Operation actOperation;
	
	
	public EcoreXmlHandler() {
		tree = ModelTree.getInstance();
	}
	
	/**
	 * 
	 */
	@Override
	public void startElement(	String uri,
								String localName,
								String qName,
								Attributes attributes) throws SAXException {
		
		// Describes a Package
		if (qName.equals("ecore:EPackage")) ;
 		
		// Describes a Class, Interface, ...
		if (qName.equals("eClassifiers")) {
			typeIndex = attributes.getIndex("xsi:type");
			nameIndex = attributes.getIndex("name");
		
			// Classifier is a Class
			if (attributes.getValue(typeIndex).equals("ecore:EClass")) {
				// is an Interface
				if (attributes.getIndex("interface") >= 0)
					actClass = new Interface(attributes.getValue(nameIndex));	
				// Class is abstract
				else if (attributes.getIndex("abstract") >= 0) 
					actClass = new Class(attributes.getValue(nameIndex), true);
				// normal Class
				else
					actClass = new Class(attributes.getValue(nameIndex));
			}
		}
		
		// Describes a Operation
		if (qName.equals("eOperations")) {
			nameIndex = attributes.getIndex("name");
			
			actOperation = new Operation(attributes.getValue(nameIndex));
		}
		
		// Describes a Parameter of an Operation
		if (qName.equals("eParameters")) {
			nameIndex = attributes.getIndex("name");
			
			actOperation.addParameter(new Parameter(attributes.getValue(nameIndex)));
		}
		
		// Describes an Attribute, Reference, Inheritance ... of a Class
		if (qName.equals("eStructuralFeatures")) {
			typeIndex = attributes.getIndex("xsi:type");
			nameIndex = attributes.getIndex("name");
			
			// Feature is an Attribute
			if (attributes.getValue(typeIndex).equals("ecore:EAttribute")) {
				((Class)actClass).addFeature(
						new Attribute(attributes.getValue(nameIndex)));
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public void endElement(	String uri,
							String localName,
							String qName) throws SAXException {
		
		if (qName.equals("eClassifiers"))
			tree.addModelElement(actClass);
		
		if (qName.equals("eOperations")) {
			if (actClass instanceof Class)
				((Class)actClass).addFeature(actOperation);
			else if (actClass instanceof Interface)
				/*((Interface)actClass).addFeature(actOperation)*/;
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void characters(	char ch[],
							int start,
							int length) throws SAXException {
		return ;
	}
}
