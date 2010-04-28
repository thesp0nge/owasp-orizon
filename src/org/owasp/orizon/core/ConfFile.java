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
package org.owasp.orizon.core;

import org.owasp.orizon.exceptions.OrizonPanicException;
import org.owasp.orizon.exceptions.OrizonXmlException;

/**
 * This class models a config file. Its information will be used to better model
 * the web application.
 * 
 * @author thesp0nge
 * @since 1.30
 */
public abstract class ConfFile extends PlainFile {
	public ConfFile(String s) {
		setKind(Constants.O_FTYPE_CONFIG);
		setName(s);
	}
	
	public abstract boolean read() throws OrizonXmlException, OrizonPanicException;
}
