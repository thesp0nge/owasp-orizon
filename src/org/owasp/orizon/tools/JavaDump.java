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
package org.owasp.orizon.tools;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.owasp.orizon.core.Constants;
import org.owasp.orizon.core.Orizon;
import org.owasp.orizon.exceptions.OrizonIOException;
import org.owasp.orizon.exceptions.OrizonPanicException;
import org.owasp.orizon.exceptions.OrizonParseException;
import org.owasp.orizon.mirage.Identify;
import org.owasp.orizon.mirage.java.JavaSourceFile;

/**
 * JavaDump is a tool that parses a Java source file given as argument, printing out some information on screen.
 * This tool is useful for debug purpose.
 * 
 * @author thesp0nge
 * @since 1.40
 */
public class JavaDump {
	private static Logger log = Logger.getLogger(JavaDump.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure(Orizon.getLog4jConf());
		log.info(org.owasp.orizon.core.About.about());
		if (args.length != 1) {
			log.error("usage: org.owasp.orizon.tools.JavaDump filename");
			return;
		}
		try {
			Identify i = new Identify();
			if (i.ident(new File(args[0]).getCanonicalPath()) != Constants.O_JAVA) {
				log.error(args[0] + " doesn't seem to be a good java source ("+i.toString()+")");
				return;
			}
			JavaSourceFile j = new JavaSourceFile(new File(args[0]).getCanonicalPath());
			log.info(args[0] + " successfully parsed. Go ahead for statistics");
			System.out.println(j.getStats().toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (OrizonIOException e) {
			log.error(e.getMessage(), e);
		} catch (OrizonPanicException e) {
			log.error(e.getMessage(), e);
		} catch (OrizonParseException e) {
			log.error(e.getMessage(), e);
		}
	}

}
