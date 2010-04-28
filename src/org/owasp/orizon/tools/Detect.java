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
import org.owasp.orizon.core.Orizon;
import org.owasp.orizon.mirage.Identify;

public class Detect {
	private static Logger log = Logger.getLogger(Detect.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure(Orizon.getLog4jConf());
		log.info(org.owasp.orizon.core.About.about());
		if (args.length != 1) {
			log.error("usage: Detect directory");
			System.exit(-1);
		}
		Identify i;
		try {
			i = new Identify(new File(args[0]).getCanonicalPath());
			i.tell();
			log.info(i.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
