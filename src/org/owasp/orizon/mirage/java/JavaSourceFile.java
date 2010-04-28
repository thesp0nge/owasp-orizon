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

import java.io.File;

import org.owasp.orizon.core.SourceFile;
import org.owasp.orizon.exceptions.OrizonIOException;
import org.owasp.orizon.exceptions.OrizonPanicException;
import org.owasp.orizon.exceptions.OrizonParseException;

/**
 * This class models a Java source file.
 * @author thesp0nge
 * @since 1.30
 * @see SourceFile
 */
public class JavaSourceFile extends SourceFile {
	private JavaCollector collector;
	private boolean interf;
	
	public JavaSourceFile(String name) throws OrizonIOException, OrizonPanicException, OrizonParseException {
		super(name);
		collector = new JavaCollector(new File(name));
		collector.model();
		stats = collector.getStats();
		
	}
	

	
	public boolean amIAnInterface() {
		return interf;
	}

}
