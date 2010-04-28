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

import java.util.StringTokenizer;

public class JspExitPoint extends ExitPoint {

	public JspExitPoint(String rawString, String filename, int i) {
	
		super(rawString.trim(), filename, i);
		parse();
	}

	@Override
	public boolean parse() {
		boolean ret = false;
		if (outString == null || "".equals(outString))
			return false;
		if (outString.contains("out.println"))
			outPrintlnParse();
		return ret;
	}

	private void outPrintlnParse() {
		StringTokenizer stok = new StringTokenizer(outString, "+");
		if (stok.countTokens() > 1) {
			while (stok.hasMoreTokens()) {
				String t = stok.nextToken();
				// if t doesn.t contain a ", it means it is not a fixed string, so
				// hopefully it is a variable name.
				if (!t.contains("\"")) {
					outNames.add(t.replace(");", " ").trim());
				}
			}
		}  
		// this is the case of an out.println(methodCall());
		if (stok.countTokens() == 1 && !outString.contains("\""))
			outNames.add(outString.substring(outString.indexOf("(")+1, outString.length()-2));
		
	}
}
