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

/**
 * This class is a model for a dynamic page or a business logic exit point.
 * 
 * With the term exit point, we mean an output on screen, on a network
 * connection, on disk or on magnetic storage.
 * 
 * The class is abstract since it's going to be extended by specific exit point
 * classes that they will implement parse() method.
 * 
 * @author thesp0nge
 * @since 1.30
 */
public abstract class ExitPoint {
	// this is the variable names that is used in output
	protected Vector<String> outNames;
	
	// this is the raw string used as output
	protected String outString;

	private Vector<String> matches;

	protected String fileName;
	protected int lineNo;
	
	public ExitPoint(String rawString, String fileName, int lineNo) {
		outString = rawString.replace("\n", " ").trim();
		outNames = new Vector<String>();
		matches = new Vector<String>();
		this.fileName = fileName;
		this.lineNo = lineNo;
	}
	
	public abstract boolean parse();

	/**
	 * After a raw output string has been parsed, you can use this method to
	 * look for a match between a variable name passed as parameter and the
	 * variable names retrieved from the output string.
	 * 
	 * @param s
	 *            the variable name to be found
	 * @return <i>true</i> if the variable name is found in the output variables
	 *         or <i>false</i> otherwise.
	 */
	public final boolean match(String s) {
		if (s==null || "".equals(s))
			return false;
		for (String t:outNames) {
			if (t.equals(s)) {
				matches.add(s);
				return true;
			}
		}
		return false;
	}
	
	public final boolean match(Vector<String> s){
		boolean found=false;
		for (String t:s) {
			if (match(t))
				found = true;
		}
		return found;
	}
	
	public final int getOutVarsCount() {
		return outNames.size();
	}
	
	public final Vector<String> getMatches() {
		return matches;
	}
	public final int getMatchesCount() {
		return matches.size();
	}
	
	public final String toString() {
		if (outNames.size() == 0)
			return "[ ]";
		
		String ret = "[ " + outNames.firstElement();
		
		for (int i=1; i<outNames.size(); i++) {
			ret += ", " + outNames.elementAt(i);
		}
		ret += " used as output in " + fileName +"@"+lineNo+" ]";
		return ret;
	}
	// this is likely to be removed after the debug stage of this code
	public Vector<String> getOutVars() {
		return outNames;
	}
	
}
