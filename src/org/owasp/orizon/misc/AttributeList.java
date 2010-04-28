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
package org.owasp.orizon.misc;

import java.util.Vector;

public class AttributeList {

	private Vector<Attribute> attrs;
	
	public AttributeList () {
		attrs = new Vector<Attribute>();
	}
	
	public void add(String name, String value) {
		attrs.add(new Attribute(name, value));
	}
	
	public int size() {
		return attrs.size();
	}
	
	private Attribute __find(String name){
		for (Attribute a:attrs) {
			if (name.equalsIgnoreCase(a.getName()))
				return a;
		}
		return null;
	}
	public boolean find(String name){
		return (__find(name) == null)? false : true;
	}
	
	public String get(String name){
		Attribute a = __find(name);
		
		return (a==null) ? null:a.getValue();
	}
	public Vector<Attribute> getAll() {
		return attrs;
	}
	
}
