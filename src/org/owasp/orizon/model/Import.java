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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * This class models an "import" statement
 * 
 * @author thesp0nge
 * @since 1.40
 */
public class Import {
	private String fileName;
	private int lineNo;
	private String rawValue;
	private String packageName;
	private String className;
	private String hash;
	private final Logger l = Logger.getLogger(Import.class);
	
	public Import() {
		
	}
	
	public Import(String filename, int lineno) {
		setFileName(filename); setLineNo(lineno);
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

	public String getRawValue() {
		return rawValue;
	}

	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setHash(String hash) {
		this.hash = hash;
		return;
	}
	public String getHash() {
		return hash;
	}
	
	public String sum(byte[] convertme) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.reset();
		} catch (NoSuchAlgorithmException e) {
			l.error(e.getMessage(),	e);
		} 
		return new String(md.digest(convertme));
	}
}
