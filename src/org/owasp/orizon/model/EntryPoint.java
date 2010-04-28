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

import java.io.File;

/**
 * This class is an entry point for an attacker to place a well known pattern to
 * a dynamic page.
 * 
 * This class is empty but created to give a common ground to specific entry
 * points.
 * 
 * @author thesp0nge
 * @since 1.30
 */
public abstract class EntryPoint {
	protected String fileName;
	protected int lineNo;
	
	public EntryPoint() {
		
	}
	
	public EntryPoint(String fileName, int lineNo) {
		setFileName(fileName); setLineNo(lineNo);
	}
	public final String getFileName() {
		return (fileName != null) ? new File(fileName).getName() : "no_name";
	}
	public final int getLineNo() {
		return lineNo;
	}
	
	public final void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public final void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
	public abstract String getStoreVariableName();

}
