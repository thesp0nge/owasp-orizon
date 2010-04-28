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
 * This class is a collection of links for a given page.
 * 
 * We want to manage the vector of outgoing links in a particular way to 
 * avoid duplicates.
 * 
 * @author thesp0nge
 * @since 1.30
 */
public class Links {
	private HashMap<String, Link> links;

	public Links() {
		links = new HashMap<String, Link>();
	}
	
	public boolean add(Link l) {
		if (links.containsKey(l.getHash()))
			return false;
		links.put(l.getHash(), l);
		return true;
	}
	
	public Vector<Link> get() {
		Vector<Link> ret = new Vector<Link>();
		Collection<Link> a = links.values();
		Iterator<Link> b = a.iterator();
		while (b.hasNext()) {
			ret.add(b.next());
		}
		return ret;
	}
}
