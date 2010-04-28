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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * This class models a link between two dynamic pages or a dynamic page and a
 * piece of business logic or two pieces of business logic.
 * 
 * @author thesp0nge
 * @since 1.30
 */
public class Link {
	private String start;
	private String stop;
	private int line;
	private String hash;
	private static Logger l = Logger.getLogger(Link.class);
	
	/**
	 * Creates a new Link
	 * @param start
	 * @param stop
	 */
	public Link(String start, String stop, int line) {
		this.start = start;
		this.stop = stop;
		this.line = line;
		hash = sum(toString().getBytes());
	}
	
	public int getLineNo() {
		return line;
	}
	public String getDestination() {
		return stop;
	}
	
	public String toString() {
		return "["+start+"->"+stop+"@"+line+"]";
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
