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
package org.owasp.orizon.exceptions;

import java.io.PrintStream;

public class OrizonXmlException extends Exception {
/**
	 * 
	 */
	private static final long serialVersionUID = 4951696839028942394L;
	private String messString;
	
	public OrizonXmlException(String message) {
		this.messString = message;
	}
	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}
	@Override
	public void printStackTrace() {
		System.err.println("print stack trace has been disabled.");
		// super.printStackTrace();
	}
	@Override
	public void printStackTrace(PrintStream s) {
		System.err.println("print stack trace has been disabled.");
		// super.printStackTrace(s);
	}
	@Override
	public String getMessage() {
		return "[ORIZON] XML Exception: " + messString;
	}
}
