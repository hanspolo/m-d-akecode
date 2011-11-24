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

/**
 * 
 * @author Philipp "Hanspolo" Hirsch
 *
 */
public class Attribute extends Feature {

    DataType type;
    Boolean isChangeable;
    Boolean isId;
    Boolean isOrdered;
    Boolean isUnique;
    Boolean isTransient;                
    Boolean isVolatile;
    
    /**
     * 
     * @param name
     * @param type
     */
    public Attribute(String name, DataType type) {
        super(name);
        this.type = type;
    }
    
    /**
     * @return the type
     */
    public DataType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(DataType type) {
        this.type = type;
    }

    /**
     * @return the isChangeable
     */
    public Boolean getIsChangeable() {
        return isChangeable;
    }

    /**
     * @param isChangeable the isChangeable to set
     */
    public void setIsChangeable(Boolean isChangeable) {
        this.isChangeable = isChangeable;
    }

    /**
     * @return the isId
     */
    public Boolean getIsId() {
        return isId;
    }

    /**
     * @param isId the isId to set
     */
    public void setIsId(Boolean isId) {
        this.isId = isId;
    }

    /**
     * @return the isOrdered
     */
    public Boolean getIsOrdered() {
        return isOrdered;
    }

    /**
     * @param isOrdered the isOrdered to set
     */
    public void setIsOrdered(Boolean isOrdered) {
        this.isOrdered = isOrdered;
    }

    /**
     * @return the isUnique
     */
    public Boolean getIsUnique() {
        return isUnique;
    }

    /**
     * @param isUnique the isUnique to set
     */
    public void setIsUnique(Boolean isUnique) {
        this.isUnique = isUnique;
    }

    /**
     * @return the isTransient
     */
    public Boolean getIsTransient() {
        return isTransient;
    }

    /**
     * @param isTransient the isTransient to set
     */
    public void setIsTransient(Boolean isTransient) {
        this.isTransient = isTransient;
    }

    /**
     * @return the isVolatile
     */
    public Boolean getIsVolatile() {
        return isVolatile;
    }

    /**
     * @param isVolatile the isVolatile to set
     */
    public void setIsVolatile(Boolean isVolatile) {
        this.isVolatile = isVolatile;
    }


    /**
     * @return String
     */
    public String toString() {
        String str = "";
        
        str += "\tpublic ";
        if (type != null)
            str += type.toString() + " ";
        str += "$" + getName() + System.lineSeparator();
        
        return str;        
    }
    
}
