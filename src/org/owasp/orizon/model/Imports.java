/**
 * This file is part of Owasp Orizon.
 * Owasp Orizon is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Owasp Orizon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package org.owasp.orizon.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * This class models a set of Import objects
 * 
 * @author thesp0nge
 * @since 1.40
 */
public class Imports {
	private HashMap<String, Import> imports;

	public Imports() {
		imports = new HashMap<String, Import>();
	}
	
	public boolean add(Import i) {
		// XXX: Import hash is computed here since here all the fields will 
		// contain their final value
		i.setHash(i.sum(i.toString().getBytes()));
		
		if (imports.containsKey(i.getHash()))
			return false;
		imports.put(i.getHash(), i);
		return true;
	}
	public Vector<Import> get() {
		Vector<Import> ret = new Vector<Import>();
		Collection<Import> a = imports.values();
		Iterator<Import> b = a.iterator();
		while (b.hasNext()) {
			ret.add(b.next());
		}
		return ret;
	}
}
