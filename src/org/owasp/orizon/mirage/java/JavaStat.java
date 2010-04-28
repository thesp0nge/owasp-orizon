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
package org.owasp.orizon.mirage.java;

import org.owasp.orizon.core.Stat;


public class JavaStat extends Stat {
	private int emptyLines;
	private int imports;
	private int methods;
	private int fields;
	private int minMethodLen;
	private int maxMethodLen;
	private String minMethodLenName;
	private String maxMethodLenName;
	
	public JavaStat() {
		super();
		emptyLines = 0;
		imports = 0;
		methods = 0;
		fields = 0;
		minMethodLen = 0;
		maxMethodLen = 0;
	}
	
	public int addEmptyLines(int emptyLines) {
		this.emptyLines += emptyLines;
		return this.emptyLines;
	}
	public int getEmptyLines() {
		return emptyLines;
	}

	public void setEmptyLines(int emptyLines) {
		this.emptyLines = emptyLines;
	}

	public int getImports() {
		return imports;
	}
	public void setImports(int imports) {
		this.imports = imports;
	}
	public int getMethods() {
		return methods;
	}
	public void setMethods(int methods) {
		this.methods = methods;
	}
	public int getFields() {
		return fields;
	}
	public void setFields(int fields) {
		this.fields = fields;
	}
	public int getMinMethodLen() {
		return minMethodLen;
	}
	public void setMinMethodLen(int minMethodLen) {
		this.minMethodLen = minMethodLen;
	}
	public int getMaxMethodLen() {
		return maxMethodLen;
	}
	public void setMaxMethodLen(int maxMethodLen) {
		this.maxMethodLen = maxMethodLen;
	}
	public String getMinMethodLenName() {
		return minMethodLenName;
	}
	public void setMinMethodLenName(String minMethodLenName) {
		this.minMethodLenName = minMethodLenName;
	}
	public String getMaxMethodLenName() {
		return maxMethodLenName;
	}
	public void setMaxMethodLenName(String maxMethodLenName) {
		this.maxMethodLenName = maxMethodLenName;
	}
	
	
	public String toString() {
		String ret = "Stats report\n";
		ret += hr();
		ret += "Total lines: " + getLines() +"\n";
		ret += "    of code: " + getLineOfCode() + "\n";
		ret += " of comment: " + getLineOfComment() +"\n";
		ret += hr();
		ret += getImports() + " imports found" + "\n";
		ret += getFields() + " fields found" + "\n";
		ret += getMethods() + " methods found" + "\n";
		ret += hr();
		ret += "Longest method : " + getMaxMethodLenName() + " (" + getMaxMethodLen() + ")\n";
		ret += "Shortest method: " + getMinMethodLenName() + " (" + getMinMethodLen() + ")\n";
		ret += hr();
  		return ret;
	}
}
