/*
 *  m(d)akecode is a code generator for model driven development.
 *  Copyright (C)   2011 Philipp "Hanspolo" Hirsch
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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MetaModelHandler extends DefaultHandler {
	
	String lastType = "";
	HashMap<String, LinkedList<String>> files = new HashMap<String, LinkedList<String>>();
	Boolean inFile = false;

    /**
     * 
     */
    @Override
    public void startElement(   String uri,
                                String localName,
                                String qName,
                                Attributes attributes) throws SAXException {
    	
    	if (qName.equals("Files")) {
    		lastType = attributes.getValue(0);
    		files.put(lastType, new LinkedList());
    	}
    	
    	if (qName.equals("File"))
    		inFile = true;

    }
    
    /**
     * 
     */
    @Override
    public void endElement( String uri,
                            String localName,
                            String qName) throws SAXException {
    	
    	if (qName.equals("File"))
    		inFile = false;
    }
    
    /**
     * 
     */
    @Override
    public void characters( char ch[],
                            int start,
                            int length) throws SAXException {
    	
    	String str = "";
    	
    	if (inFile) {
    		for (int i = 0; i < length; i++)
    			str += ch[i + start];
    			
    		files.get(lastType).add(str);
    	}
    }
    
    /**
     * 
     * @param type
     * @return
     */
    public List<String> getFilesByType(String type) {
    	return files.get(type);
    }
}
