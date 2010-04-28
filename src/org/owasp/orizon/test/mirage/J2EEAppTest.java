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
package org.owasp.orizon.test.mirage;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.owasp.orizon.core.About;
import org.owasp.orizon.core.Orizon;
import org.owasp.orizon.exceptions.OrizonIOException;
import org.owasp.orizon.exceptions.OrizonPanicException;
import org.owasp.orizon.exceptions.OrizonXmlException;
import org.owasp.orizon.mirage.java.J2eeApp;

public class J2EEAppTest {
	static Logger l = Logger.getLogger(J2EEAppTest.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure(Orizon.getLog4jConf());
		if (args.length != 1) {
			System.err.println("usage: java J2EEAppTest directory");
			System.exit(-1);
		}
		try {
			l.info(About.about());
			if (Orizon.isInDebugMode())
				l.warn("Orizon is running in debug mode");
			if (Orizon.beVerbose())
				l.warn("Orizon is running in verbose mode");
			l.warn("log4j configuration stored at: " + Orizon.getLog4jConf());
			
			J2eeApp app = new J2eeApp(new File(args[0]).getCanonicalPath());
			app.model();
		} catch (OrizonPanicException e) {
			l.error(e.getMessage(), e);
		} catch (OrizonXmlException e) {
			l.error(e.getMessage(), e);
		} catch (IOException e) {
			l.error(e.getMessage(), e);
		} catch (OrizonIOException e) {
			l.error(e.getMessage(), e);
		}

	}

}
