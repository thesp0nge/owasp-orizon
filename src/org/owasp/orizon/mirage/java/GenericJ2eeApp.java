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
import java.util.Vector;

import org.apache.log4j.Logger;
import org.owasp.orizon.core.Constants;
import org.owasp.orizon.core.GenericApp;
import org.owasp.orizon.core.Orizon;
import org.owasp.orizon.core.ParseFailure;
import org.owasp.orizon.core.SourceFile;
import org.owasp.orizon.exceptions.OrizonIOException;
import org.owasp.orizon.exceptions.OrizonPanicException;
import org.owasp.orizon.exceptions.OrizonParseException;
import org.owasp.orizon.mirage.Identify;
import org.owasp.orizon.mirage.jsp.JspSourceFile;
import org.owasp.orizon.model.EntryPoint;

public class GenericJ2eeApp extends GenericApp {
	private static Logger log = Logger.getLogger(GenericJ2eeApp.class);

	/**
	 * Creates a generic J2ee Application. By generic we means that has a bunch
	 * of java source files containing business logic.
	 * 
	 * The presence of dynamic web pages written in jsp is not mandatory in this
	 * kind of application.
	 * 
	 * The {@link Identify} class must return an identification code that
	 * matches {@link Constants.O_APP_GENERIC} flag.
	 * 
	 * @param webroot
	 * @throws OrizonPanicException 
	 * @throws OrizonIOException 
	 */
	public GenericJ2eeApp(String webroot) throws OrizonPanicException, OrizonIOException {
		super(webroot);
		Identify i = new Identify(getWebroot());
		if (i.tell() != Constants.O_APP_J2EE)
			throw new OrizonPanicException("non j2ee app, passed to J2eeApp class (" + i.tell() + "). Giving up!");
		if (!i.isGeneric())
			throw new OrizonPanicException("a bug here. A pure J2EE application is handled as generic one. Giving up!");
		// now the code is pretty the same as J2eeApp constructor
		Vector<String> nameList = i.getFileListing(new File(getWebroot()));
		
		for (String s:nameList) {
			switch (i.ident(s)) {
			case Constants.O_JAVA:
				JavaSourceFile j;
				try {
					j = new JavaSourceFile(s);
					stat.add(j.getStats());
					bobjs.add(j);
				} catch (OrizonParseException e1) {
					if (Orizon.beVerbose())
						log.warn(s + " has failed parsing");
					bobjErrors.add(new ParseFailure(s, e1.getMessage()));
				}
				break;
			case Constants.O_JSP:
				try {
					JspSourceFile jsp = new JspSourceFile(s);
					stat.add(jsp.getStats());
					pages.add(jsp);
				} catch (OrizonParseException e) {
					if (Orizon.beVerbose())
						log.warn(s + " has failed parsing");
					pageErrors.add(new ParseFailure(s, e.getMessage()));
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void dump() {
		if (Orizon.isInDebugMode()) {
			log.debug("Application name is: " + getName());
			log.debug("Web root is: " +getWebroot());
			log.debug(pages.size() + " pages found");
			log.debug(bobjs.size() + " classes found");
			log.debug(pageErrors.size() + " pages were not successfully parsed");
			log.debug(bobjErrors.size() + " classes were not successfully parsed");
			log.debug("---------------------------");
			if (Orizon.beVerbose()) {
				for (int z=0; z<pageErrors.size();z++) 
					log.debug(pageErrors.get(z).toString());
				log.debug("---------------------------");
				for (int z=0; z<bobjErrors.size();z++) 
					log.debug(bobjErrors.get(z).toString());
			}
			log.debug("---------------------------");
			log.debug(stat.toString());
			log.debug("---------------------------");
			for (SourceFile p:pages) {
				JspSourceFile jsp = (JspSourceFile) p;
				log.debug(p.toString());
			}

			
			
		}
	}

	@Override
	public boolean model() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector<EntryPoint> getEntryPoints() {
		Vector<EntryPoint> ret = new Vector<EntryPoint>();
		for (SourceFile p:pages) {
			JspSourceFile jsp = (JspSourceFile) p;
			ret.addAll(jsp.getEntryPoints());
		}
		return ret;
	}

}
