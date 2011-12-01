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
public class Class extends Classifier {

    private List<Feature> features;
    private List<Classifier> superTypes;
    private Boolean isAbstract;

    /**
     * 
     * @param name
     */
    public Class(String name) {
        super(name);
        features = new ArrayList<Feature>();
        superTypes = new ArrayList<Classifier>();
        this.isAbstract = false;
    }
    
    /**
     * 
     * @param name
     */
    public Class(String name, Boolean isAbstract) {
        super(name);
        features = new ArrayList<Feature>(); 
        superTypes = new ArrayList<Classifier>();
        this.isAbstract = isAbstract;
    }
    
    /**
     * 
     * @param f
     */
    public void addFeature(Feature f) {
        features.add(f);
        f.setMyClass(this);
    }
    
    /**
     * 
     * @param c
     */
    public void addSupertype(Classifier c) {
        superTypes.add(c);
    }
    
    /**
     * @return the isAbstract
     */
    public Boolean getIsAbstract() {
        return isAbstract;
    }

    /**
     * @param isAbstract the isAbstract to set
     */
    public void setIsAbstract(Boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    /**
     * 
     */
    public String toString() {
        String str = "";
        
        if (isAbstract)
            str += "abstract ";
        
        str += "class " + getName();

        if (superTypes.size() > 0)
        	str += " implements ";

        for (Classifier c : superTypes)
        	str += c.getName() + " ";
        
        str += "{" + System.lineSeparator();
        
        for (Feature f : features)
        	if (!(f instanceof AssociationEnd))
                str += f.toString();

        str += "}" + System.lineSeparator();
        
        return str;
    }
}