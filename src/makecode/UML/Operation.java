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

package makecode.UML;

import java.util.ArrayList;
import java.util.List;

public class Operation extends Feature {

	List<Parameter> parameters;

	
	public Operation(String name) {
		super(name);
		parameters = new ArrayList<Parameter>();
	}
}