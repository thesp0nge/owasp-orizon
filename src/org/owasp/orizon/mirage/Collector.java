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
 * @author thesp0nge 
 * @since 1.30
 */
package org.owasp.orizon.mirage;

import java.io.File;

import org.owasp.orizon.core.Stat;
import org.owasp.orizon.exceptions.OrizonPanicException;
import org.owasp.orizon.exceptions.OrizonParseException;

/**
 * This interface is for all language dependent collectors
 * @author thesp0nge
 * @since 1.1
 */
public abstract class Collector {
	
	
	protected String		filename;
	protected File			f;
	protected Stat			stats;
	
	public Collector(File f) {
		this.f = f;
		this.filename = f.getAbsolutePath();
	}
	
	public final String getFileName() {
		return filename;
	}
	public final File getFile() {
		return f;
	}

	public Stat getStats() {
		return stats;
	}
	
	public String toString() {
		return filename + ": " +stats.getLines() + " lines " + stats.getLineOfCode() + " code " + stats.getLineOfComment() + " comments";
	}
	/*
	public <T> T find(Vector<T> v, String name) {
		T ret = null;
		for (T s : v ) {
			if (((Item) s).getName().equals(name)) {
				ret = s;
				break;
			}
		}
		
		return ret;
	}
	
	public abstract boolean parse();
	public abstract boolean inspect();
	public abstract boolean spider();
	public abstract boolean crawl(Vector<String> s);
	public abstract boolean show(String name);
	public abstract boolean dump(String what);
	public abstract boolean hasAuxFaults();
	
	*/
	/**
	 * This method takes the language specific parser and use its information to
	 * model the source file.
	 * @throws OrizonPanicException 
	 * @throws OrizonParseException 
	 */
	public abstract boolean model() throws OrizonPanicException, OrizonParseException;
	
	
	
}
