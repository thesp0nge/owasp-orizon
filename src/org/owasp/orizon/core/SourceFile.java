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

import org.owasp.orizon.mirage.Collector;

/**
 * This class models a source file that needs a review
 * @author thesp0nge
 * @since 1.30
 */
public class SourceFile extends PlainFile {
	protected Collector collector;
	protected Stat		stats = null;
	
	public SourceFile(String name) {
		setKind(Constants.O_FTYPE_SRC);
		setName(name);
	}
	
	public final Stat getStats() {
		return (stats == null )? new Stat(): stats;
	}

}
