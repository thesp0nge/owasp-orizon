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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.owasp.orizon.core.Constants;
import org.owasp.orizon.core.Orizon;
import org.owasp.orizon.mirage.Identify;
import org.owasp.orizon.mirage.java.GenericJ2eeApp;
import org.owasp.orizon.mirage.java.J2eeApp;

public class J2EEDump {
	private static Logger log = Logger.getLogger(J2EEDump.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure(Orizon.getLog4jConf());
		log.info(org.owasp.orizon.core.About.about());
		if (args.length != 1) {
			log.error("usage: org.owasp.orizon.tools.J2EEDump directory");
			System.exit(-1);
		}
		Identify i;
		try {
			i = new Identify(new File(args[0]).getCanonicalPath());
			if (! (i.tell() == Constants.O_APP_J2EE))
				log.error(i.toString() + " found. I won't handle it.");
			if (! i.isGeneric()) {
				log.info(i.toString());
				J2eeApp app = new J2eeApp(new File(args[0]).getCanonicalPath());
				app.model();
				app.dump();
			} else {
				log.info(i.toString());
				GenericJ2eeApp app = new GenericJ2eeApp(new File(args[0]).getCanonicalPath());
				app.model();
				app.dump();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
