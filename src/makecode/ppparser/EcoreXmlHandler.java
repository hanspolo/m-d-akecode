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

package makecode.ppparser;

import java.util.HashMap;

import makecode.uml.Association;
import makecode.uml.AssociationEnd;
import makecode.uml.Attribute;
import makecode.uml.Class;
import makecode.uml.Classifier;
import makecode.uml.DataType;
import makecode.uml.Interface;
import makecode.uml.ModelElement;
import makecode.uml.ModelTree;
import makecode.uml.Operation;
import makecode.uml.Parameter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class EcoreXmlHandler extends DefaultHandler {

    private ModelTree tree;
    private int typeIndex, nameIndex;
    private ModelElement actClass;
    private Operation actOperation;
    private String actPackage;
    private HashMap<AssociationEnd, String> assocEnds;
    private HashMap<String, Association> assocOpposite;
    private HashMap<ModelElement, String> superTypes;
    
    
    public EcoreXmlHandler() {
        tree = ModelTree.getInstance();
        assocEnds = new HashMap<AssociationEnd, String>();
        assocOpposite = new HashMap<String, Association>();
        superTypes = new HashMap<ModelElement, String>();
    }
    
    /**
     * 
     */
    @Override
    public void startElement(   String uri,
                                String localName,
                                String qName,
                                Attributes attributes) throws SAXException {
        
        // Describes a Package
        if (qName.equals("ecore:EPackage")) {
            nameIndex = attributes.getIndex("name");
            actPackage = attributes.getValue(nameIndex);
        }
        
         
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
                
                if (attributes.getIndex("eSuperTypes") >= 0)
                	superTypes.put(actClass, attributes.getValue(attributes.getIndex("eSuperTypes")));
            }
            
            
            // Classifier is a DataType
            if (attributes.getValue(typeIndex).equals("ecore:EDataType"))
                ;
        }
        
        
        // Describes an Operation
        if (qName.equals("eOperations")) {
            nameIndex = attributes.getIndex("name");
            typeIndex = attributes.getIndex("xsi:type");
            
            
            // Operation returns a value
            if (attributes.getIndex("eType") >= 0)
                actOperation = 
                    new Operation(  attributes.getValue(nameIndex),
                                    this.convertEType(attributes.getValue(attributes.getIndex("eType"))));
            // Operation doesn't return a value
            else
                actOperation = new Operation(attributes.getValue(nameIndex));
        }
        
        
        // Describes a Parameter of an Operation
        if (qName.equals("eParameters")) {
            nameIndex = attributes.getIndex("name");
            typeIndex = attributes.getIndex("xsi:type");
            
            
            // Add Parameter to Operation
            actOperation.addParameter(
                    new Parameter(  attributes.getValue(nameIndex),
                                    this.convertEType(attributes.getValue(attributes.getIndex("eType")))));
        }
        
        
        // Describes an Attribute, Reference, Inheritance ... of a Class
        if (qName.equals("eStructuralFeatures")) {
            typeIndex = attributes.getIndex("xsi:type");
            nameIndex = attributes.getIndex("name");
            
            
            // Feature is an Attribute
            if (attributes.getValue(typeIndex).equals("ecore:EAttribute")) {
                Attribute attr = 
                        new Attribute(  attributes.getValue(nameIndex), 
                                        this.convertEType(attributes.getValue(attributes.getIndex("eType"))));
                
                attr.setIsChangeable(    attributes.getIndex("changeable")   >= 0);
                attr.setIsId(            attributes.getIndex("id")           >= 0);
                attr.setIsOrdered(       attributes.getIndex("ordered")      >= 0);
                attr.setIsUnique(        attributes.getIndex("unique")       >= 0);
                attr.setIsTransient(     attributes.getIndex("transient")    >= 0);
                attr.setIsVolatile(      attributes.getIndex("volatile")     >= 0);
                
                ((Class)actClass).addFeature(attr);
            }
            
            
            // Feature is a Reference
            if (attributes.getValue(typeIndex).equals("ecore:EReference")) {  
            	int i;
            	int lowerBound = (i = attributes.getIndex("lowerBound")) >= 0 ? Integer.parseInt(attributes.getValue(i)) : 0;
            	int upperBound = (i = attributes.getIndex("upperBound")) >= 0 ? Integer.parseInt(attributes.getValue(i)) : 1;
            	boolean containment = attributes.getIndex("containment") >= 0;
            	
            	// Init the ends of the Association
            	AssociationEnd startAssoc = 
                        new AssociationEnd( attributes.getValue(nameIndex),
                                            0, 0, false);
            	AssociationEnd endAssoc = 
                        new AssociationEnd( attributes.getValue(nameIndex),
                                            lowerBound, upperBound, containment);
            	
            	startAssoc.setOtherEnd(endAssoc);
            	endAssoc.setOtherEnd(startAssoc);
            	
            	// Init Association
            	Association assoc = 
            			new Association(attributes.getValue(nameIndex), startAssoc, endAssoc);
            	
            	// Add to the Model
            	tree.addModelElement(assoc);
            	
            	if (actClass instanceof Class)
            		((Class)actClass).addFeature(startAssoc);
            	
                assocEnds.put(endAssoc, this.convertName(attributes.getValue(attributes.getIndex("eType"))));
                
                // If Association has an Opposite
                // And opposite exists
                // Then connect them
                if ((i = attributes.getIndex("eOpposite")) >= 0 &&
                	(assocOpposite.containsKey(attributes.getValue(nameIndex)))) {
                	
                	Association opposite = assocOpposite.get(attributes.getValue(nameIndex));
                	
                	assoc.setOpposite(opposite);
                	opposite.setOpposite(assoc);
                }
                // If Association has an Opposite but the opposite doesn't exist
                // Then save the Association in a Map
                else if((i = attributes.getIndex("eOpposite")) >= 0)
                	assocOpposite.put(this.convertName(attributes.getValue(i)), assoc);
            }
        }
    }

    /**
     * 
     */
    @Override
    public void endElement( String uri,
                            String localName,
                            String qName) throws SAXException {
    	
        if (qName.equals("ecore:EPackage")) {
            String className;
    		
            for (AssociationEnd assoc : assocEnds.keySet()) {
                className = assocEnds.get(assoc);

                for (ModelElement e : tree.getModelElements())
                    if (e.getName().equals(className))
                        ((Class)e).addFeature(assoc);
            }
            
            for (ModelElement element : superTypes.keySet()) {
            	String[] stypes = superTypes.get(element).split(" ");
            	
            	for (String stype : stypes) {
            		stype = this.convertName(stype);
            		for (ModelElement e : tree.getModelElements()) {
            			if (e instanceof Classifier &&
            				e.getName().equals(stype)) {
            				
            				if (element instanceof Class)
            					((Class)element).addSupertype((Classifier)e);
            				if (element instanceof Interface)
            					((Interface)element).addSupertype((Classifier)e);
            			}
            		}
            	}
            }
        }
    	
        if (qName.equals("eClassifiers")) {
        	actClass.setPackageName(actPackage);
            tree.addModelElement(actClass);
        }
        
        if (qName.equals("eOperations")) {
            if (actClass instanceof Class)
                ((Class)actClass).addFeature(actOperation);
            else if (actClass instanceof Interface)
                ((Interface)actClass).addFeature(actOperation);
        }
    }
    
    /**
     * 
     */
    @Override
    public void characters( char ch[],
                            int start,
                            int length) throws SAXException {
        return ;
    }
    
    /**
     * 
     * @param str
     * @return
     */
    private DataType convertEType(String str) {
        String[] parts = str.split("//");
        str = parts[parts.length - 1];
        
        DataType dt = new DataType(str.substring(1));
        
        return dt;
    }
    
    /**
     * 
     * @param str
     * @return
     */
    private String convertName(String str) {
        String[] parts = str.split("/");
        str = parts[parts.length - 1];
        
        return str;
    }
}
