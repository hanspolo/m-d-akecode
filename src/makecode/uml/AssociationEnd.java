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

package makecode.uml;

/**
 * 
 * @author Philipp "Hanspolo" Hirsch
 *
 */
public class AssociationEnd extends Feature {

	int lowerBound;
	int upperBound;
	boolean composition;
	AssociationEnd otherEnd;
	
	/**
	 * 
	 * @param name
	 * @param lower
	 * @param upper
	 * @param composition
	 */
	public AssociationEnd (String name, int lower, int upper, boolean composition) {
		super(name);
		this.lowerBound 	= lower;
		this.upperBound 	= upper;
		this.composition 	= composition;
	}
	
	/**
	 * 
	 * @param name
	 * @param lower
	 * @param upper
	 * @param composition
	 * @param other
	 */
	public AssociationEnd (String name, int lower, int upper, boolean composition, AssociationEnd other) {
		super(name);
		this.lowerBound 	= lower;
		this.upperBound 	= upper;
		this.composition 	= composition;
		this.otherEnd		= other;
	}

	/**
	 * @return the lowerBound
	 */
	public int getLowerBound() {
		return lowerBound;
	}

	/**
	 * @param lowerBound the lowerBound to set
	 */
	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	/**
	 * @return the upperBound
	 */
	public int getUpperBound() {
		return upperBound;
	}

	/**
	 * @param upperBound the upperBound to set
	 */
	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

	/**
	 * @return the composition
	 */
	public boolean isComposition() {
		return composition;
	}

	/**
	 * @param composition the composition to set
	 */
	public void setComposition(boolean composition) {
		this.composition = composition;
	}

	/**
	 * @return the otherEnd
	 */
	public AssociationEnd getOtherEnd() {
		return otherEnd;
	}

	/**
	 * @param otherEnd the otherEnd to set
	 */
	public void setOtherEnd(AssociationEnd otherEnd) {
		this.otherEnd = otherEnd;
	}
}