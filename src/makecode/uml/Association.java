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

package makecode.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Philipp "Hanspolo" Hirsch
 *
 */
public class Association extends ModelElement {

    List<AssociationEnd> associationEnds;
    Association opposite;
    
    /**
     * 
     * @param name
     * @param a
     * @param b
     */
    public Association (String name, AssociationEnd a, AssociationEnd b) {
        super(name);
        associationEnds = new ArrayList<AssociationEnd>();
        
        associationEnds.add(a);
        associationEnds.add(b);
        
        a.setAssociation(this);
        b.setAssociation(this);
        
        opposite = null;
    }
    
    public void addAssociationEnd(AssociationEnd a) {
        associationEnds.add(a);
        a.setAssociation(this);
    }

	/**
	 * @return the type
	 */
	public AssociationType getType() {
		return AssociationType.CONTAINS;
	}

	/**
	 * @return the opposite
	 */
	public Association getOpposite() {
		return opposite;
	}

	/**
	 * @param opposite the opposite to set
	 */
	public void setOpposite(Association opposite) {
		this.opposite = opposite;
	}
	
	public String toString() {
		String str = "";
		str += getName() + System.lineSeparator();
		for (AssociationEnd e : associationEnds)
			str += e.toString();
		
		if (opposite != null)
			str += "Opposite: " + opposite.getName() + System.lineSeparator();
		
		return str;
	}
}
