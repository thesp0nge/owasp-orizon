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

import java.util.Vector;

import org.owasp.orizon.misc.Attribute;
import org.owasp.orizon.misc.AttributeList;

public class HtmlEntryPoint extends EntryPoint {
	protected static final String FORM_NAME = "form element";
	protected static final String INPUT_NAME = "input field";
	protected AttributeList attr;
	private String myName;
	
	public HtmlEntryPoint (String myName) {
		attr = new AttributeList();
		this.myName = myName;
	}
	
	public final void addAttribute(String name, String value) {
		attr.add(name, value);
		return ;
	}
	
	public final boolean hasAttribute(String name) {
		return attr.find(name);
	}
	
	public final String getAttribute(String name) {
		return attr.get(name);
	}

	public String getStoreVariableName() {
		return "";
	}
	public String toString() {
		String ret = getFileName() + " - [" + myName + "]" + "@" +getLineNo();
		Vector<Attribute> a = attr.getAll();
		for (int i=0; i<a.size(); i++){ 
			ret += a.get(i).toString();
		}
		return ret;
	}
}
